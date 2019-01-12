/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot2019 extends TimedRobot {
	private static final int frontLeftWheel = 2;
	private static final int RearLeftWheel = 3;
	private static final int FrontRightWheel = 1;
	private static final int RearRightWheel = 0;

	private MecanumDrive robotDrive;
	private Joystick controller;

	@Override
	public void robotInit() {
		Victor frontLeft = new Victor(kFrontLeftChannel);
		Victor rearLeft = new Victor(kRearLeftChannel);
		Victor frontRight = new Victor(kFrontRightChannel);
		Victor rearRight = new Victor(kRearRightChannel);

		// Invert the left side motors.
		// You may need to change or remove this to match your robot.
		frontLeft.setInverted(true);
		rearLeft.setInverted(true);

		m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

		m_stick = new Joystick(kJoystickChannel);
	}

	@Override
	public void teleopPeriodic() {
		// Use the joystick X axis for lateral movement, Y axis for forward
		// movement, and Z axis for rotation.
		m_robotDrive.driveCartesian(m_stick.getX(), m_stick.getY(),
				m_stick.getZ(), 0.0);
	}
}
