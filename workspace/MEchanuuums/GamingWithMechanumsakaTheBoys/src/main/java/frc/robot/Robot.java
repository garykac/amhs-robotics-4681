// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PWMVictorSPX;

import org.ejml.equation.Variable;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveWheelSpeeds;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  PWMVictorSPX forwardLeft;
  PWMVictorSPX backLeft;
  PWMVictorSPX forwardRight;
  PWMVictorSPX backRight; 
  
  MecanumDrive drive;
  Joystick controller;

  double ySpeed;
  double xSpeed;
  double rotation;
  double speed = 0.5;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    forwardLeft = new PWMVictorSPX(3);
    forwardRight = new PWMVictorSPX(2);
    backLeft = new PWMVictorSPX(1);
    backRight = new PWMVictorSPX(0);

    controller = new Joystick(0);
    drive = new MecanumDrive(forwardLeft, backLeft, forwardRight, backRight);
  }

/* CHOEESE */
  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override                   
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    ySpeed = -controller.getRawAxis(1) * speed;
    xSpeed = controller.getRawAxis(0) * speed;
    rotation = controller.getRawAxis(2) * speed;

    drive.driveCartesian(xSpeed * 1.2, ySpeed, rotation);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}