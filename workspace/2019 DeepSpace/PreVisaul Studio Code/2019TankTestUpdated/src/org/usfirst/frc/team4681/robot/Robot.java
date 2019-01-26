/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;



public class Robot extends TimedRobot {
	Victor motorR; //y-axes are inverted between controller and console
	Victor motorL;
	Victor motorTest;
	Joystick controller;
	double rDist; double lDist;
	String Rrange,Lrange;
	double ru,lu;
	Ultrasonic Rultra =new Ultrasonic(3,4); //Digital Output 1, Digital Input 1
	Ultrasonic Lultra =new Ultrasonic(1,2);

	@Override
	public void robotInit() {
		this.motorR = new Victor(0);
		this.motorL = new Victor(1);
		this.motorTest = new Victor(2);
		this.controller = new Joystick(0);
		Rultra.setAutomaticMode(true);
		Lultra.setAutomaticMode(true);
	}

	public void ultraSens(){
		rDist = Rultra.getRangeInches();
		lDist = Lultra.getRangeInches();
		if(rDist <= 4 || lDist <= 4) {
			motorR.set(0);
			motorL.set(0);
		} else {
			motorR.set(-0.35);
			motorL.set(0.35);
		}
		if (ru>lu){
			//motorR.set(-.15); //for motorR pos = backwards, neg = forwards
			motorL.set(-0.3); //for motorL, pos=ford, neg=backw
		}
		else if(lu>ru){
			//motorL.set(.15);
			motorR.set(0.3);
		}
		Rrange = Double.toString(rDist);
		Lrange = Double.toString(lDist);
		ru=Double.parseDouble(Rrange.substring(0,2));
		lu=Double.parseDouble(Lrange.substring(0,2));
		System.out.println(ru+"\n"+lu+"\n"+"\n");
		//System.out.println( "Left: "+valU1+"\nRight: "+valU2);
		
	}
	@Override
	public void autonomousInit() {}

	@Override
	public void autonomousPeriodic() {
		ultraSens();
	}

	@Override
	public void teleopPeriodic() {
		//motor0.set(controller.getRawAxis(3)); //3 is the right side
		//motor1.set(-controller.getRawAxis(1));
		//System.out.println(controller.getRawAxis(3));
		//System.out.println(controller.getRawAxis(1));
		motorR.set(controller.getRawAxis(3));
		motorL.set(-controller.getRawAxis(1));	
		motorTest.set(controller.getRawAxis(0));
		//ultraSens();
	}
	
	@Override
	public void testPeriodic() {}
}