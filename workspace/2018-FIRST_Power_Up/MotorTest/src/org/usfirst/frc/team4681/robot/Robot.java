/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
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
		kSpeed = 0.15;
	}


	@Override
	public void autonomousInit() {}

	@Override
	public void autonomousPeriodic() {}

	@Override
	public void teleopPeriodic() {
		if (controller.getRawButton(2))
			kSpeed = controller.getRawButton(8) ? .70 : 0.35;
		if (controller.getRawButtonPressed(3))
			kSpeed = controller.getRawButton(8) ? 0.50 : 0.20;
		
		motor0.set(kSpeed*-controller.getRawAxis(3)*controller.getRawAxis(3));
		motor1.set(kSpeed*controller.getRawAxis(1)*controller.getRawAxis(1));
	}

	@Override
	public void testPeriodic() {}
}
