/* Xbox controller notes:
 * 4 axes:
 * 0 = left stick X
 * 1 = left stick Y
 * 2 = right stick X
 * 3 = right sitck Y
 * 
 * Buttons:
 * 1 = X, 2 = A, 3 = B, 4 = Y
 * 5 = Left Bumper (LB)
 * 6 = Right Bumper (RB)
 * 7 = Left Trigger (LT)
 * 8 = Right Trigger (RT)
 * 9 = Back Button
 * 10 = Start Button
 * 11 = Left Stick Press (Click In)
 * 12 = Right Stick Press (Click In)
 */

package org.usfirst.frc.team4681.robot;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//This is a program designed to test joystick capabilities.
//When the robot is enabled, it will test any of the un-commented motors.
//DO NOT USE WITH A DRIVING ROBOT
//SOLE USE IS TO TEST BUTTONS AND MOTOR SPINNING
//PLEASE DO NOT USE WITH INSTALLED MOTORS

//BUTTON TESTING ONLY IN DISABLED
//MOTOR TESTING ONLY IS TELEOPERATED ENABLED

public class ControlTest extends IterativeRobot {
	Joystick xbox; //Creates a joystick thingy
	Victor m0, m1, m2, m3, m4, m5, m6, m7, m8, m9;
	//a victor for each of the possible channels
	//they are declared in robot initiation section
	CameraServer vid;
	
	int yoda = 0; //Declares Yoda. //These are for disabled testing.
	String Force = "Do or do not, there is no try.";
	String lazyOut = "You haven't pressed a special button yet.";
	
	@Override
	public void robotInit() {
		//m0 = new Victor(0);
		//m1 = new Victor(1);
		//m2 = new Victor(2);
		//m3 = new Victor(3);
		//m4 = new Victor(4);
		//m5 = new Victor(5);
		//m6 = new Victor(6);
		//m7 = new Victor(7);
		//m8 = new Victor(8);
		m9 = new Victor(9);
		//vid = CameraServer.getInstance();
		//vid.startAutomaticCapture("Camera",0);
	}
	
	@Override
	public void disabledInit() {
		//This function runs when the robot initiates.
		//The controller must be plugged into port 0 of the computer (the right side).
		xbox = new Joystick(0);
		SmartDashboard.putString("People say ", Force);
		SmartDashboard.putNumber("Midichlorian count: ", yoda);
		SmartDashboard.putString("Lazy Output: ", lazyOut);
	}
	
	@Override
	public void disabledPeriodic() {
		//Code here runs only when robot is disabled.
		
		if (xbox.getRawButton(1)) {
			SmartDashboard.putString("People say ",
					"Lock S-Foils in attack position!");
		} else if (xbox.getRawButton(4)) {
			SmartDashboard.putString("People say ", "Why didn't "
					+ "you tell me? You told me Vader betrayed and murdered my father.");
		} else if (xbox.getRawButton(3)) {
			SmartDashboard.putString("People say ",
					"May the Force be with you!");
		} else if (xbox.getRawButton(2)) {
			SmartDashboard.putString("People say ",
					"Attachments are forbidden.");
		} else {
			SmartDashboard.putString("People say ", Force);
		}
		
		if (xbox.getRawButton(5)) {
			SmartDashboard.putNumber("Midichlorian count: ", Math.floor(yoda));
			yoda -= .05;
		} else if (xbox.getRawButton(6)) {
			SmartDashboard.putNumber("Midichlorian count: ", Math.floor(yoda));
			yoda += .05;
		} else {
			SmartDashboard.putNumber("Midichlorian count: ", yoda);
		}
		
		if (xbox.getRawButton(7) || xbox.getRawButton(8)
				|| xbox.getRawButton(11) || xbox.getRawButton(12)) {
			SmartDashboard.putString("Lazy Output: ", "Fire at will, commander!");
		} else if (xbox.getRawButton(9)) {
			SmartDashboard.putString("Lazy Output: ", "Why does everyone want to go back to Jakku?!");
		} else if (xbox.getRawButton(10)) {
			SmartDashboard.putString("Lazy Output: ", "A long time ago, in a galaxy far, far away...");
		} else {
			SmartDashboard.putString("Lazy Output: ", lazyOut);
		}
	}
	
	@Override
	public void teleopInit() {
		int a = 9;
		int b = 13;
	}
	
	@Override
	public void teleopPeriodic() {
		if (xbox.getRawButton(6)) {
			m9.set(xbox.getRawAxis(3)/2);
		} else {
				m9.set(0);
			
		} /**Above is specialized testing for single motor responding to Right Y Axis.
		You MUST be pressing the right bumper to be operating the motor.*/
		//m0.set(.1);
		//m1.set(-.1);
		//m2.set(.1);
		//m3.set(-.1);
		//m4.set(.1);
		//m5.set(-.1);
		//m6.set(.1);
		//m7.set(-.1);
		//m8.set(.1);
		//m9.set(-.1);
	}
}



//Courtesy of Christopher :-)