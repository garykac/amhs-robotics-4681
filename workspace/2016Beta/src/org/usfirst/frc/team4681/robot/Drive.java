//2016 Robot Code
package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.*;

public class Drive {
	//define motor controllers
	private Victor left;
	private Victor right;
	
	//variable to contain inputs (cuz screw calling joy.getX() every time)
	private double xl;	//x of the left joy
	private double xr;	//x of the right
	
	//constructor and init things in appropiate controller
	public Drive(int ctrler0, int ctrler1){
		left = new Victor(ctrler0); //was 0
		right = new Victor(ctrler1); //was 1
	}
	
	/*special function to allow better control of robot
	basically squares the input (making it a smaller number b/c it is a decimal) while keeping sign
	also creates a dead zone so it is not constantly jerking (magic square method)*/
	private double adjust(double in){
		if(in > .05){
			if(in > 1) return 1;
			return in*in;
		} else if(in < -.05){
			if(in < -1) return -1;
			return -(in*in);
		} else {
			return 0;
		}
	}
	
	//let's go places!
	//drives the robot
	public void drive(double Left, double Right, boolean straight){
		xl = Left;
		xr = -Right;
		if(straight){
			xl = Right;
		}
		
		//set motors to go at adjusted speed
		left.set(adjust(xl));
		right.set(adjust(xr));
	}
	
	//single joy-stick drive
	public void altDrive(double y, double rotate){
		double leftMotorSpeed;
		double rightMotorSpeed;
		
		if ( y > 0.0) {
		      if (rotate > 0.0) {
		        leftMotorSpeed = y - rotate;
		        rightMotorSpeed = Math.max(y, rotate);
		      } else {
		        leftMotorSpeed = Math.max(y, -rotate);
		        rightMotorSpeed = y + rotate;
		      }
		    } else {
		      if (rotate > 0.0) {
		        leftMotorSpeed = -Math.max(-y, rotate);
		        rightMotorSpeed = y + rotate;
		      } else {
		        leftMotorSpeed = y - rotate;
		        rightMotorSpeed = -Math.max(-y, -rotate);
		      }
		    }
		
		// I screwed up but easier fix than unscrewing up
		left.set(rightMotorSpeed);
		right.set(leftMotorSpeed);
	}
	
	//automanous drive 
	public void autoDrive(double speed){
		xl = -speed;
		xr = speed;
		
		left.set(xl);
		right.set(.82 * xr);
	}
	
	//turns the robot x degrees, positive to left
	public void turn(int angle){
		double percent = angle/180;
		//time to turn 180;
		double time = 1;
		double turnTime = percent*time;
		
		left.set(-.75);
		right.set(-.75);
		Timer.delay(turnTime);
		left.set(0);
		right.set(0);
	}
	
}
