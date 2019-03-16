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
    private static final int kButtonJoystickLeft = 11;
    private static final int kButtonJoystickRight = 12;
    private static final int kJoystickChannel = 0;
    private static final int kJoystickPlayerChannel = 1;
    // For testing CHANGE IT FOR COMPETITION
    private static final double kMotorPowerLevel = 1.0;
    // To manage the controls for the Lifter
    private boolean currentlyPressed = false;
    private boolean currentlyPressedPlayer = false;
    private boolean ballMode= true;
    private int lifterLevel = 0;
    private int modeAdder = 1; //For automated lifter
    private boolean autoLift = false;
    private int counter = 0;
    private int runningTotal = 0;
    private boolean twoPlayer = true; //Boolean used for if using one or two remotes
    private MecanumDrive m_robotDrive;

    CameraServer m_cameraServer;
    
    Compressor m_compressor;
    
    private Joystick m_stick, m_stickPlayer;
    
    private boolean grabberRunning = false;
    private boolean constantIntake = false;
    private int macroIndex = 0;

    // These are used to keep a running average of the last 10 LIDAR values.
    // LIDAR value min/max tracker.
    private boolean m_recordLidarValues = false;
    private double m_totalDistance = 0.0;
    private int m_numSamples = 0;
    private double m_minLidarValue = Double.MAX_VALUE;
    private double m_maxLidarValue = Double.MIN_VALUE;
    
    private Walker m_walker;

    private Lifter m_lifter;

    private Grabber m_grabber;

    private Sucker m_sucker;

    private LineFollower m_lineFollower;
    
    @Override
    public void robotInit() {
        PWMVictorSPX frontLeft = new PWMVictorSPX(Constants.kPWMFrontLeft); //Each motor is a seperate wheel
        PWMVictorSPX rearLeft = new PWMVictorSPX(Constants.kPWMRearLeft);
        PWMVictorSPX frontRight = new PWMVictorSPX(Constants.kPWMFrontRight);
        PWMVictorSPX rearRight = new PWMVictorSPX(Constants.kPWMRearRight);
        
        // Invert motors as needed. Some of these will become false.
        frontLeft.setInverted(true);
        rearLeft.setInverted(true);
        frontRight.setInverted(true);
        rearRight.setInverted(true);

        m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
        
        m_compressor = new Compressor(0);

        m_stick = new Joystick(kJoystickChannel);
        m_stickPlayer = new Joystick(kJoystickPlayerChannel); // Second controller

        m_walker = new Walker();
        m_walker.WalkerInit();

        m_lifter = new Lifter();
        m_lifter.lifterInit();

        m_grabber = new Grabber();
        m_grabber.GrabberInit();

        m_sucker = new Sucker();
        m_sucker.SuckerInit();

        m_lineFollower = new LineFollower();
        m_lineFollower.LineFollowerInit();

        m_cameraServer.getInstance().startAutomaticCapture("FrontCam", 0); // Initilaizing cameras; if cameras arent plugged in, will error
        m_cameraServer.getInstance().startAutomaticCapture("BackCam", 1);     
    }
    
    /* EXPLAINING THE USE OF 'modeAdder':
     * lifterLevel will either be offset by +1 (ball mode) or 0 (hatch mode). It starts at +1.
     * The lifter levels are as follows: -1=bottom, 0=bottom, 1=hatch1, 2=ball1, 3=hatch2, 4=ball2, 5=hatch3, 6=ball3
     * The -1 is the bottom in the hatch mode, and 0 is bottom for the ball mode.
     * There are no specific levels for the loading station because we pick balls up from the ground,
     * and the hatch receptacle is at the same level as hatch1. There isn't a level for BallCargoShip because
     * our primary focus is the rocket. AUTOMATED LIFTER IS CURRENTLY UNUSED
    */

    public void lifterOperatorCode() {
        if (autoLift == !autoLift) {
            switch (lifterLevel) {
                case -1:
                case 0:
                    m_lifter.GoToBottom();
                    break;
                case 1:
                    m_lifter.GoToFirstHatchLevel();
                    break;
                case 2:
                    m_lifter.GoToFirstBallLevel();
                    break;
                case 3:
                    m_lifter.GoToSecondHatchLevel();
                    break;
                case 4:
                    m_lifter.GoToBallLoadingStation();
                    break;
                case 5:
                    m_lifter.GoToThirdHatchLevel();
                    break;
                case 6:
                    m_lifter.GoToSecondBallLevel();
                    break;
            }
        }
        if (twoPlayer) { // Works with the controllers
            /*if (m_stickPlayer.getRawButtonPressed(kButtonA)) {
                autoLift = !autoLift;  // true to false; v.v.
                System.out.println("Automated Lifter: " + autoLift);
            }*/
            if (autoLift == !autoLift) { //code works, Laser Sensor not finished, removed until laser works
                if (m_stickPlayer.getRawButtonPressed(kButtonB)) {
                    modeAdder *= -1; // Switches between -1 and 1, used to switch between two types of preset heights
                    if (modeAdder == 1) {
                        System.out.println("Ball Mode");
                    } else {
                        System.out.println("Hatch Mode");
                    }
                    lifterLevel += modeAdder;
                }
                if (m_stickPlayer.getPOV() == 0) {//D-Pad Up Arrow
                    if (lifterLevel <= 4 && !currentlyPressedPlayer)
                        lifterLevel += 2;
                    currentlyPressedPlayer = true;
                } else if (m_stickPlayer.getPOV() == 180) {//D-Pad Down Arrow
                    if (lifterLevel >= 1 && !currentlyPressedPlayer)
                        lifterLevel -= 2;
                    currentlyPressedPlayer = true;
                } else if (m_stickPlayer.getPOV() == 270) {//D-Pad Right Arrow
                    lifterLevel = (int).5*(modeAdder - 1); // This will return it to the correct bottom, no matter the mode.
                } else {
                    currentlyPressedPlayer = false;      
                }
            } else { // The only other choice is autoLift = false;
                if (m_stickPlayer.getPOV() == 0) {//D-Pad Up Arrow
                    m_lifter.Lift();
                } else if (m_stickPlayer.getPOV() == 180) {//D-Pad Down Arrow
                    m_lifter.Lower();
                } else {
                    m_lifter.Stop();
                }
            }
        // END OF TWO PLAYER CODE
        } else { // ELSE IF USING ONLY ONE JOYSTICK
            /*if (m_stick.getRawButtonPressed(kButtonA)){
                autoLift = !autoLift;  // true to false; v.v.
                System.out.println("Automated Lifter: " + autoLift);
            }*/
            if (autoLift == !autoLift) { // Autolift is jerky
                if (m_stick.getRawButtonPressed(kButtonB)) {
                    modeAdder *= -1; // Switches between -1 and 1, used to switch between two types of preset heights
                    if (modeAdder == 1) {
                        System.out.println("Ball Mode");
                    } else {
                        System.out.println("Hatch Mode");
                    }
                    lifterLevel += modeAdder;
                }
                if (m_stick.getPOV() == 0) {
                    if (lifterLevel <= 4 && !currentlyPressedPlayer)
                        lifterLevel += 2;
                    currentlyPressedPlayer = true;
                } else if (m_stick.getPOV() == 180) {
                    if (lifterLevel >= 1 && !currentlyPressedPlayer)
                        lifterLevel -= 2;
                    currentlyPressedPlayer = true;
                } else if (m_stick.getPOV() == 270) {
                    lifterLevel = (int).5*(modeAdder - 1); // This will return it to the correct bottom, no matter the mode.
                } else {
                    currentlyPressedPlayer = false;      
                }
            } else { // The only other choice is autoLift = false;
                if (m_stick.getPOV() == 0) {
                    m_lifter.Lift();
                } else if (m_stick.getPOV() == 180) {
                    m_lifter.Lower();
                } else {
                    m_lifter.Stop();
                }
            }
        }
    }

    public void mainPeriodic() {
        m_robotDrive.driveCartesian(-kMotorPowerLevel * m_stick.getX(),
                                    kMotorPowerLevel * m_stick.getY(),
                                    -kMotorPowerLevel * m_stick.getZ(), 0.0);
        if (grabberRunning) {//Two Constants used, grabberRunning is whether or not ball motor is on
            if (constantIntake) {//constnatIntake is whether or not the ball is being pulled in or 
                m_grabber.Grab();
            } else {
                m_grabber.Eject();
            }
        } else {
            m_grabber.Stop();
        }
        // You have to press both sticks in to switch in and out of two player mode
        if (m_stick.getRawButton(kButtonJoystickRight) && m_stick.getRawButton(kButtonJoystickLeft)) {
            if (!currentlyPressed) {
                twoPlayer = !twoPlayer;
                System.out.println("\nTwo Player Mode: " + twoPlayer);
            }
            currentlyPressed = true;
        } else {
            currentlyPressed = false;
        }

        if (twoPlayer) {
            if (m_stickPlayer.getRawButtonPressed(kButtonRT)) { //Switches between motor for balls being off or on
                grabberRunning = true;
                constantIntake = !constantIntake;
                System.out.println("Grabber Intake: " + constantIntake);
            }
            if (m_stickPlayer.getRawButtonPressed(kButtonLT)) { //Stops motor from spinning
                grabberRunning = false;
                constantIntake = false;
            }
            if (m_stickPlayer.getRawButtonPressed(kButtonX)) //Toggles hatch vacuum
                m_sucker.Suck();
            if (m_stickPlayer.getRawButtonPressed(kButtonY)) //Toggles between extended/retracted hatch piston
                m_sucker.Extend();
        } else {
            if (m_stick.getRawButtonPressed(kButtonRT)) { //Toggles between between grabbing or ejecting
                grabberRunning = true;
                constantIntake = !constantIntake;
                System.out.println("Grabber Intake: " + constantIntake);
            }
            if (m_stick.getRawButtonPressed(kButtonLT)) { //Kills the motor
                grabberRunning = false;
                constantIntake = false;
            }
            if (m_stick.getRawButtonPressed(kButtonX))
                m_sucker.Suck();
            if (m_stick.getRawButtonPressed(kButtonY))
                m_sucker.Extend();
        }
    }
    
    @Override
    public void autonomousInit() {//Teleop is being used, just in case FRC checks autonomous mode
        teleopInit(); // All relevant auto and teleop init functions are the same
    }

    @Override
    public void autonomousPeriodic() {
        mainPeriodic();
        lifterOperatorCode(); // The functionality of this line of code is referenced in issue #25 [it works]

    }
    
    @Override
    public void teleopInit() {
        m_compressor.start();
        macroIndex = 0;
        grabberRunning = false;
        constantIntake = false;
    }
                
    @Override
    public void teleopPeriodic() {
        mainPeriodic();
        lifterOperatorCode();

        // CLIMBING CODE, SPECIFIC TO TELEOP
        // Only valid for main joystick. It's easier that way.
        if (m_stick.getRawButtonPressed(kButtonStart)) {
            // Proceed to next climbing step.
            macroIndex++;
            System.out.println(macroIndex);
            m_walker.Climb(macroIndex);
            if (macroIndex == 4) {
                macroIndex = -1;
            }
        }
        if (m_stick.getRawButtonPressed(kButtonBottom)) {
            // If we need to restart the climbing process.
            macroIndex = -1;
            System.out.println(macroIndex);
            m_walker.Climb(macroIndex);
        }
        
        //Prints out values for lidar scanner 
        counter++;
        runningTotal += m_lifter.getDistance();
        if (counter == 50) {
            //System.out.println(runningTotal / 50);
            counter = 0;
            runningTotal = 0;
        }
    }

    public void trackLidarValues() {
        if (m_stick.getRawButtonPressed(kButtonLB)) {
            m_recordLidarValues = !m_recordLidarValues;

            // Print summary when we stop sampling.
            if (!m_recordLidarValues) {
                System.out.println("LIDAR Summary:");
                System.out.println("  Min: " + m_minLidarValue + " Max: " + m_maxLidarValue);
                System.out.println("  Samples: " + m_numSamples);
                System.out.println("  Average: " + (m_totalDistance / m_numSamples));
            } else {
                System.out.println("Sampling LIDAR values...");
            }

            m_numSamples = 0;
            m_totalDistance = 0;
            m_minLidarValue = Double.MAX_VALUE;
            m_maxLidarValue = Double.MIN_VALUE;
        }

        if (m_recordLidarValues) {
            double sample = m_lifter.getDistance();
            m_totalDistance += sample;
            m_numSamples++;
            if (sample < m_minLidarValue)
                m_minLidarValue = sample;
            if (sample > m_maxLidarValue)
                m_maxLidarValue = sample;
        }
    }
}