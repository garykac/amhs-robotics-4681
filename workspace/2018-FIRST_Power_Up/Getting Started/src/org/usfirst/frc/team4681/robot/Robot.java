/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4681.robot;

/**
* import edu.wpi.first.wpilibj.AnalogInput;
* import edu.wpi.first.wpilibj.Spark;
* import edu.wpi.first.wpilibj.drive.DifferentialDrive;
* import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
*/
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */


public class Robot extends IterativeRobot {
	private Timer m_timer = new Timer();
	int small = 0;
	
	Joystick controller;
	Encoder encoder1;
	DigitalInput cutoffSwitch;
	BuiltInAccelerometer accel;
	Relay really;
	Victor motor0;
	Victor motor1;
	Victor motor2;
	CameraServer cameraServer;
	
	double velocityY, velocityX = 0.0f;
	double previousX, previousY = 0.0f;
	double accelerationX, accelerationY = 0.0f;
	double accelYAdj = 0.0f;
	double sps = 0.0f;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		controller = new Joystick(0);
		//cameraServer = CameraServer.getInstance();
		//cameraServer.startAutomaticCapture("Camera",0);
		//Make sure you add the camera on the thing.
		//i.e. in the SmartDashboard, view, add, camera server
		//If all else fails, reboot, recode, and then pray feverishly
	}
	
	@Override
	public void disabledInit() {
		//controller = new Joystick(0);
		//encoder1.free();
		//encoder1 = new Encoder(0,1, false, EncodingType.k4X);
		//encoder1.reset();
		//encoder1.setDistancePerPulse(1);
		//cutoffSwitch = new DigitalInput(7);
		
		motor0 = new Victor(0);
		motor1 = new Victor(1);
		motor2 = new Victor(2);
		
		//7.5 mA max for relays
		really = new Relay(0);
	
		
		
		/** Sadly, the accelerometer is not working as well as we'd hoped.
		 * There's too much noise for it to be of use (until we can filter it). -Chris
		
		accel = new BuiltInAccelerometer();
		accel.setRange(Range.k8G);
		SmartDashboard.putNumber("Accel Y adjustment", 0.013);
		accelYAdj = SmartDashboard.getNumber("Accel Y adjustment", 0.013);
		*/
	}
	
	// Disabled Periodic contains flawed code for accelerometer testing. -Chris
	// Disabled Periodic cannot test motors (because the robot's disabled, right?).
	@Override
	public void disabledPeriodic() {
	
		/*if(controller.getRawButton(5)) {
			small++;
			SmartDashboard.putString("Message from us", "Hello");
			SmartDashboard.putNumber("Small numbers: ", small);
		}*/
		
		/** 
		//accelYAdj = SmartDashboard.getNumber("Accel Y adjustment",0.013);
		//accelerationY = accel.getY() - accelYAdj;
		if(Math.abs(accel.getX())>.005) {
			velocityX += (accel.getX());
		}
		if(Math.abs(accel.getY())>.005) {
			velocityY += (accel.getY());
		}
		small++;
		velocityY += accel.getY();// - .008;
		double avgAccel = velocityY/small;
		SmartDashboard.putNumber("Average acceleration Y", avgAccel);
		SmartDashboard.putNumber("Small: ", small);
				
		//previousX = 0.1*accel.getX() + 0.9*previousX;
		//previousY = 0.1*accel.getY() + 0.9*previousY;
		SmartDashboard.putNumber("Accelerometer X", accel.getX());
		//SmartDashboard.putNumber("Accelerometer Y", accelerationY);
		SmartDashboard.putNumber("The Returned AccelY Value", accel.getY());
		SmartDashboard.putNumber("Velocity X", velocityX);
		SmartDashboard.putNumber("Velocity Y", velocityY);
 		
		if(controller.getRawButton(5)) {
			velocityY = 0;
		}
		*/
		
		SmartDashboard.putNumber("Number of revolutions: ", encoder1.getDistance()/150.0);
		//SmartDashboard.putBoolean("Cutoff Switch", cutoffSwitch.get()); 
		if(controller.getRawButton(6)) { //This is right bumper
			encoder1.reset();
		}
		/* Green (a) is on, red (b) is off, blue (x) is forward, yellow (y) is reverse.*/
		if(controller.getRawButtonPressed(2)) {
			really.set(Value.kOn);
			//This is 'A', 5 FWD, 5 REV
		}
		else if(controller.getRawButtonPressed(3)) {
			really.set(Value.kOff);
			//This is 'B', 0 FWD, - REV
		}
		else if(controller.getRawButtonPressed(1)) {
			really.set(Value.kForward);
			//This is 'X', 5 FWD, 0 REV
		}
		else if(controller.getRawButtonPressed(4)) {
			really.set(Value.kReverse);
			//This is 'Y', 0 FWD, 5 REV
		}
		SmartDashboard.putString("Stuff", really.get().toString());
		
		
		
		
	}
		

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	
	@Override
	public void autonomousInit() {
		m_timer.reset();
		m_timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	
	@Override
	public void autonomousPeriodic() {
	
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	
	@Override
	public void teleopInit() {
		cutoffSwitch = new DigitalInput(7);
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	
	@Override
	public void teleopPeriodic() {
		//motor0.set(controller.getRawAxis(2)); //Right stick x-axis to PWM 0 !DOESN'T WORK!
		//motor1.set(controller.getRawAxis(3)); //Right stick y-axis to PWM 1
		
		SmartDashboard.putNumber("Rstick.Y pos: ", controller.getRawAxis(3));
		SmartDashboard.putNumber("Encoder 1 position: ", encoder1.getDistance());
		SmartDashboard.putNumber("Encoder 1 Speed: ", encoder1.getRate());
		//I'm like 109% sure that our encoder does 250 pulses per revolution - Andrew
		
		SmartDashboard.putNumber("Number of revolutions: ", encoder1.getDistance()/250.0);
		SmartDashboard.putBoolean("Cutoff Switch", cutoffSwitch.get()); 
		
		/*if(cutoffSwitch.get()) {
			motor2.set(.15);
		}
		else {
			motor2.set(0);
		}*/
		
		
		if(controller.getRawButton(6)) { //This is right trigger
			encoder1.reset();
		}
		if(controller.getRawButton(5)) { //left bumper
			motor1.set(1);
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
