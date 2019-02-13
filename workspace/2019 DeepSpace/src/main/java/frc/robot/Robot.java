/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * Main robot code for 2019.
 */
public class Robot extends TimedRobot {
    // Joystick declarations
    private static final int kButtonX = 1;
    private static final int kButtonA = 2;
    private static final int kButtonB = 3;
    private static final int kButtonY = 4;
    private static final int kButtonLB = 5;
    private static final int kButtonRB = 6;
    private static final int kButtonLT = 7;
    private static final int kButtonRT = 8;
    private static final int kButtonBottom = 9;
    private static final int kButtonStart = 10;
    private static final int kButtonJoyStickLeft = 11;
    private static final int kButtonJoyStickRight = 12;
    private static final int kJoystickChannel = 0;
    
    // For testing
    private static final double kMotorPowerLevel = 0.4;
    
    // To manage the controls for the Lifter
    private boolean currentlyPressed = false;
    private boolean ballMode= true;
    private int lifterLevel = 0;
    private int modeAdder = 1;
    private boolean autoLift = true;
    
    private MecanumDrive m_robotDrive;

    CameraServer m_cameraServer;
    
    Compressor m_compressor; // It needs to be called to start in robotInit() (when the robot turns on)
    
    private Joystick m_stick;
    
    private Walker m_walker;

    private Lifter m_lifter;

    private Grabber m_grabber;

    private Sucker m_sucker;

    private LineFollower m_lineFollower;

    @Override
    public void robotInit() {
        PWMVictorSPX frontLeft = new PWMVictorSPX(Constants.kPWMFrontLeft);
        PWMVictorSPX rearLeft = new PWMVictorSPX(Constants.kPWMRearLeft);
        PWMVictorSPX frontRight = new PWMVictorSPX(Constants.kPWMFrontRight);
        PWMVictorSPX rearRight = new PWMVictorSPX(Constants.kPWMRearRight);
        
        // Invert motors as needed.
        frontLeft.setInverted(true);
        rearLeft.setInverted(true);
        frontRight.setInverted(true);
        rearRight.setInverted(true);

        m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
        
        m_compressor = new Compressor();
        m_compressor.start()

        m_stick = new Joystick(kJoystickChannel);

        m_walker = new Walker();
        m_walker.WalkerInit();

        m_lifter = new Lifter();
        m_lifter.lifterInit();
        m_lifter.GoToBottom();

        m_grabber = new Grabber();
        m_grabber.GrabberInit();

        m_sucker = new Sucker();
        m_sucker.SuckerInit();

        m_lineFollower = new LineFollower();
        m_lineFollower.LineFollowerInit();

        m_cameraServer.getInstance().startAutomaticCapture("FrontCam", 0);
        m_cameraServer.getInstance().startAutomaticCapture("BackCam", 1);     
    }
    
    /* EXPLAINING THE USE OF 'modeAdder':
    * lifterLevel will either be offset by +1 (ball mode) or 0 (hatch mode). It starts at +1.
    * The lifter levels are as follows: -1=bottom, 0=bottom, 1=hatch1, 2=ball1, 3=hatch2, 4=ball2, 5=hatch3, 6=ball3
    * The -1 is the bottom in the hatch mode, and 0 is bottom for the ball mode.
    * There are no specific levels for the loading station because we pick balls up from the ground,
    * and the hatch receptacle is at the same level as hatch1. There isn't a level for BallCargoShip because
    * our primary focus is the rocket.
    */
    
    public void lifterOperatorCode() {
        if (autoLift) {
            if (m_stick.getRawButtonPressed(kButtonLB)) {
                modeAdder *= -1; // Switches between -1 and 1
                if (modeAdder == 1) {
                    System.out.println("Ball Mode");
                } else {
                    System.out.println("Hatch Mode");
                }
                lifterLevel += modeAdder;
            }
            if (m_stick.getPOV() == 0 {
                if (lifterLevel <= 4 && !currentlyPressed)
                    lifterLevel += 2;
                currentlyPressed = true;
            } else if (m_stick.getPOV() == 180) {
                if (lifterLevel >= 1 && !currentlyPressed)
                    lifterLevel -= 2;
                currentlyPressed = true;
            } else if (m_stick.getPOV() == 270) {
                lifterLevel = .5*(modeAdder - 1); // This will return it to the correct bottom, no matter the mode.
            } else {
                currentlyPressed = false;      
            }
            switch (lifterLevel) {
                case -1:
                case 0:
                    m_lifter.goToBottom();
                    break;
                case 1:
                    m_lifter.gotoFirstBallLevel();
                    break;
                case 2:
                    m_lifter.gotoFirstHatchLevel();
                    break;
                case 3:
                    m_lifter.gotoSecondBallLevel();
                    break;
                case 4:
                    m_lifter.gotoSecondHatchLevel();
                    break;
                case 5:
                    m_lifter.gotoThirdBallLevel();
                    break;
                case 6:
                    m_lifter.gotoThirdHatchLevel();
                    break;
            }
        } else { // The only other choice is autoLift = false;
            if(m_stick.getPOV() == 0){
                m_lifter.Lift();
            } else if (m_stick.getPOV() == 180) {
                m_lifter.Lower();
            } else {
                m_lifter.Stop();
            }
        }        
    }
                
                
    @Override
    public void AutonomousPeriodic() {
        m_robotDrive.driveCartesian(kMotorPowerLevel * m_stick.getX(),
                                    kMotorPowerLevel * m_stick.getY(),
                                    kMotorPowerLevel * m_stick.getZ(), 0.0);
        
        if (m_stick.getRawButtonPressed(kButtonA)) {
            m_grabber.Grab();
        }
        if (m_stick.getRawButtonPressed(kButtonB)) {
            m_grabber.Eject();
        }
        if (m_stick.getRawButtonPressed(kButtonX)) {
            m_sucker.Suck(true);
        }
        if (m_stick.getRawButtonPressed(kButtonY)) {
            m_sucker.Suck(false);
        } 
        lifterOperatorCode(); // The functionality of this line of code is referenced in issue #25

    }
    
    @Override
    public void teleopInit() {
        int macroIndex = 0;
        m_compressor.start() // If it failed in robotInit(), just in case.
    }
                
    @Override
    public void teleopPeriodic() {
        m_robotDrive.driveCartesian(kMotorPowerLevel * m_stick.getX(),
                                    kMotorPowerLevel * m_stick.getY(),
                                    kMotorPowerLevel * m_stick.getZ(), 0.0);
        if (m_stick.getRawButtonPressed(kButtonA)) {
            m_grabber.Grab();
        }
        if (m_stick.getRawButtonPressed(kButtonB)) {
            m_grabber.Eject();
        }
        if (m_stick.getRawButtonPressed(kButtonX)) {
            m_sucker.Suck(true);
        }
        if (m_stick.getRawButtonPressed(kButtonY)) {
            m_sucker.Suck(false);
        }
        if (m_stick.getRawButtonPressed(kButtonStart)) { // This code doesn't exist in the auto stuff, for good reason
            macroIndex++;  // I've had to edit this code, because the Climb() function is idealized
            if (macroIndex == 1)
                m_walker.RaiseRobot();
            if (macroIndex == 2)
                m_walker.Walk();
            if (macroIndex == 3) //By idealized, I mean, the robot has to drive during this step.
                m_walker.RetractBackLegs(); // Climb() doesn't give the operator time to do so.
            if (macroIndex == 4) {
                m_walker.RetractFrontLegs();
                macroIndex = 0;
            }
        }
        if (m_stick.getRawButtonPressed(kButtonBottom)) { // If we need to restart the climbing process.
            m_walker.RetractBackLegs();
            m_walker.RetractFrontLegs();
            macroIndex = 0;
        }
        lifterOperatorCode();
    }
}

