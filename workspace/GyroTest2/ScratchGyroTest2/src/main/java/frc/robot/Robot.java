/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class Robot extends TimedRobot {
  Joystick controller;
  Victor motor_fL;
  Victor motor_fR;
  Victor motor_rL;
  Victor motor_rR;
  ADXRS450_Gyro gyro;
  MecanumDrive drive;
  // X=1 A=2 B=3 Y=4 LB=5 RB=6 LT=7 RT=8 Back=9 Start=10

  @Override
  public void robotInit() {
    gyro = new ADXRS450_Gyro();
    gyro.reset();
    motor_fL = new Victor(0); //PWM Channels
    motor_fR = new Victor(1);
    motor_rL = new Victor(2);
    motor_rR = new Victor(3);
    controller = new Joystick(0);
    drive = new MecanumDrive(motor_fL, motor_rL, motor_fR, motor_rR);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}
  
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopPeriodic() {
    //driveCartesian(xAxis, yAxis, zRotation)
    // joystick y-axes are inverted for some reason
    drive.driveCartesian(controller.getRawAxis(0), -controller.getRawAxis(1), controller.getRawAxis(2));
    double currentAngle = gyro.getAngle();
    System.out.println(currentAngle);
    System.out.println(gyro.isConnected()); //returns true
    System.out.println(gyro.getAngle()); //still zero
    System.out.println(gyro.getRate()); // zero
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
