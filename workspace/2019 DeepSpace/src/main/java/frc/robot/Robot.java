/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * Main robot code for 2019.
 */
public class Robot extends TimedRobot {
  private static final int kPWMFrontLeft = 0;
  private static final int kPWMRearLeft = 2;
  private static final int kPWMFrontRight = 1;
  private static final int kPWMRearRight = 3;
  
  private static final int kButtonX = 1;
  private static final int kButtonA = 2;
  private static final int kButtonB = 3;
  private static final int kButtonY = 4;
  private static final int kButtonLB = 5;
  private static final int kButtonRB = 6;
  private static final int kButtonLT = 7;
  private static final int kButtonRT = 8;

  private static final double kMotorPowerLevel = 0.4;

  private static final int kJoystickChannel = 0;

  private MecanumDrive m_robotDrive;
  private Joystick m_stick;
  
  CameraServer m_cameraServer;

  private Walker m_walker;

  private Lifter m_lifter;

  @Override
  public void robotInit() {
    PWMVictorSPX frontLeft = new PWMVictorSPX(kPWMFrontLeft);
    PWMVictorSPX rearLeft = new PWMVictorSPX(kPWMRearLeft);
    PWMVictorSPX frontRight = new PWMVictorSPX(kPWMFrontRight);
    PWMVictorSPX rearRight = new PWMVictorSPX(kPWMRearRight);

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
    m_lifter.LiftInit();

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
    
    m_lifter.Lift();
    
    
  }
}
