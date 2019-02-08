/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
    
    private static final int kButtonX = 1;
    private static final int kButtonA = 2;
    private static final int kButtonB = 3;
    private static final int kButtonY = 4;
    private static final int kButtonLB = 5;
    private static final int kButtonRB = 6;
    private static final int kButtonLT = 7;
    private static final int kButtonRT = 8;
    private static final int kButtonBottom = 9;
    private static final int kButtonstart = 10;
    private static final int kButtonJoyStickLeft = 11;
    private static final int kButtonJoyStickRight = 12;

    private static final double kMotorPowerLevel = 0.4;

    private static final int kJoystickChannel = 0;

    private boolean currentlyPressed = false;
    private boolean ballMode= true;
    private int lifterLevelHatch = 0;
    private int lifterLevelBall = 0;

    private MecanumDrive m_robotDrive;
    private Joystick m_stick;

    CameraServer m_cameraServer;

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

    @Override
    public void teleopPeriodic() {
        // Use the joystick X axis for lateral movement, Y axis for forward
        // movement, and Z axis for rotation.
        // Why not use m_stick.getRawAxis()? And then use 0, 1, and 2 for LX, LR, RX?

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
        
        if (m_stick.getRawButtonPressed(kButtonstart)) {
            m_walker.Climb();
        }
        if(m_stick.getRawButtonPressed(kButtonLB)){
            ballMode=false;
        }
        if(m_stick.getRawButtonPressed(kButtonRB)){
            ballMode=true;
        }


        if (m_stick.getPOV() == 0) {
            if (lifterLevelBall < 7 && !currentlyPressed && ballMode == true){
                if(lifterLevelBall != 3){
                    lifterLevelBall++;
                    currentlyPressed = true;}
        } }
        else if (m_stick.getPOV() == 180) {
            if (lifterLevelBall > 0 && !currentlyPressed && ballMode == true){
                if(lifterLevelBall != 0){
                    lifterLevelBall--;
                    currentlyPressed = true;}
        } }
        else {
           currentlyPressed = false;
        }
        if(ballMode){
            switch(lifterLevelBall) {
                case 0:
                    m_lifter.GoToBottom();
                    System.out.println("Bottom");
                    break;
                case 1:
                    m_lifter.GoToFirstBallLevel();
                    System.out.println("Ball First Level");
                    break;
                case 2:
                    m_lifter.GoToSecondBallLevel();
                    System.out.println("Ball Second Level");
                    break;
                case 3:
                    m_lifter.GoToThirdBallLevel();
                    System.out.println("Ball Third Level");
                    break;
            }
        }
        if (m_stick.getPOV() == 0) {
            if (lifterLevelHatch < 7 && !currentlyPressed && ballMode != true){
                if(lifterLevelHatch !=3 ){
                    lifterLevelHatch++;
                    currentlyPressed = true;}
        } }
        else if (m_stick.getPOV() == 180) {
            if (lifterLevelHatch > 0 && !currentlyPressed && ballMode != true){
                if(lifterLevelHatch != 0){
                    lifterLevelHatch--;
                    currentlyPressed = true;}
        } }
        else {
           currentlyPressed = false;
        }
        if(ballMode != true){
            switch(lifterLevelHatch) {
                case 0:
                    m_lifter.GoToBottom();
                    System.out.println("Bottom");
                    break;
                case 1:
                    m_lifter.GoToFirstHatchLevel();
                    System.out.println("Hatch First Level");
                    break;
                case 2:
                    m_lifter.GoToSecondHatchLevel();
                    System.out.println("Hatch Second Level");
                    break;
                case 3:
                    m_lifter.GoToThirdHatchLevel();
                    System.out.println("Hatch Third Level");
                    break;
        }
        //m_lineFollower.OnLine();
        }
    }
}

