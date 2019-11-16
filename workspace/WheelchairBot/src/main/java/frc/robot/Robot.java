/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends TimedRobot {
    Victor motor0;
	Victor motor1;
	Joystick controller;
	double kSpeed;


	@Override
	public void robotInit() {
		this.motor0 = new Victor(0);
		this.motor1 = new Victor(1);
		this.controller = new Joystick(0);
		kSpeed = 0.45;
	}

	@Override
	public void autonomousInit() {}

	@Override
	public void autonomousPeriodic() {}

	@Override
	public void teleopPeriodic() {
	}

	@Override
	public void testPeriodic() {}
}
