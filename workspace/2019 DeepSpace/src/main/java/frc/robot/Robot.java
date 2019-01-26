/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
  private static final int kPWMGrabberRight = 4;
  private static final int kPWMGrabberLeft = 5;
  private static final int kPWMLifter = 6;

  private static final double kMotorPowerLevel = 0.4;

  private static final int kJoystickChannel = 0;

  private PWMVictorSPX m_grabberRight;
  private PWMVictorSPX m_grabberLeft;
  private PWMVictorSPX m_lifter;

  private MecanumDrive m_robotDrive;
  private Joystick m_stick;

  private Walker m_walker;

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

    m_grabberRight = new PWMVictorSPX(kPWMGrabberRight);
    m_grabberLeft = new PWMVictorSPX(kPWMGrabberLeft);
    m_lifter = new PWMVictorSPX(kPWMLifter);

    m_stick = new Joystick(kJoystickChannel);

    m_walker = new Walker();
    m_walker.WalkerInit();
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick X axis for lateral movement, Y axis for forward
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(kMotorPowerLevel * m_stick.getX(),
                                kMotorPowerLevel * m_stick.getY(),
                                kMotorPowerLevel * m_stick.getZ(), 0.0);
    
  }
}
