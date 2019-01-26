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
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj.drive.MecanumDrive;
// Will we need to import gyros and analog sensors?
// What will we use them for, rather, before importing?

public class Robot extends TimedRobot {
  /* Buttons:
	 * 1 = X, 2 = A, 3 = B, 4 = Y
	 * 5 = LB, 6 = RB, 7 = LT, 8 = RT
	 * 9 = Back, 10 = Start
	 * 11 = Left Stick Press (Click In)
	 * 12 = Right Stick Press (Click In)
	 */
  Victor motor_frontL;
  Victor motor_frontR;
  Victor motor_backL;
  Victor motor_backR;
  Victor motor_intakeL;
  Victor motor_intakeR;
  Solenoid piston_frontL;
  Solenoid piston_frontR;
  Solenoid piston_backL;
  Solenoid piston_backR;
  Joystick controller;
  Compressor compressor;
  MecanumDrive robotBase;
  //CameraServer cameraServer;

  @Override
  public void robotInit() {
    motor_frontL = new Victor();
    motor_frontR = new Victor();
    motor_backL = new Victor();
    motor_backR = new Victor();
    motor_intakeL = new Victor();
    motor_intakeR = new Victor();
    piston_frontL = new Solenoid();
    piston_frontR = new Solenoid();
    piston_backL = new Solenoid();
    piston_backR = new Solenoid();
    controller = new Solenoid();
    compressor = new Compressor();
    compressor.start(); //Here, or in teleopInit?
    robotBase = new MecanumDrive(motor_frontL, motor_backL, motor_frontR, motor_backR);
    cameraServer = new CameraServer.getInstance();
    cameraServer.startAutomaticCapture("FrontCam", 0);
    cameraServer.startAutomaticCapture("BackCam", 1);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    //compressor.start() Here, or robotInit, or teleopInit?
  }

  @Override
  public void autonomousPeriodic() {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
