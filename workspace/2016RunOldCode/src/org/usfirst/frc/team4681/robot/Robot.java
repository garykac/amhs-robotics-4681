/**
 * 
 * CODE FOR 2015 
 * 
 * 
 */

package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public Victor frontLeftMotor = new Victor(0);
	public Victor frontRightMotor = new Victor(1);
	public Victor backLeftMotor = new Victor(2);
	public Victor backRightMotor = new Victor(3);
	public Victor raiseLowerMotor = new Victor(4);
	
	public Encoder frontLeftEncoder = new Encoder(0,1);
	public Encoder frontRightEncoder = new Encoder(2,3);
	public Encoder backLeftEncoder = new Encoder(4,5);
	public Encoder backRightEncoder = new Encoder(6,7);
	public Encoder raiseLowerEncoder = new Encoder(8,9);
	
	
	public Joystick Joy1 = new Joystick(0);
	public Joystick Joy2 = new Joystick(1);
	
	boolean isBroken = false;
	double dHeight = 0;
	
	public double encoderMax = 0;
    
	Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		//SmartDashboard.putNumber("Height(TEST)", raiseLowerEncoder.getDistance());
		//SmartDashboard.putNumber("desiredHeight", dHeight);
        // instantiate the command used for the autonomous period
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		/* This makes sure that the autonomous stops running when
         teleop starts running. If you want the autonomous to 
         continue until interrupted by another command, remove
         this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        raiseLowerEncoder.reset();
        */
    }
    
   /*
     This function is called when the disabled button is hit.
     You can use it to reset subsystems before shutting down.
    */
    public void disabledInit(){
    	System.out.println("Final maximum is " + encoderMax);
    }

   //This function is called periodically during operator control
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        drive();
    }
    
    // This function is called periodically during test mode
     public void testPeriodic() {
        LiveWindow.run();
    }
    
    //This is a testing method to raise and lower the carriage
   
    //Drive code is isolated here
    public void drive(){
    	frontLeftMotor.set(specialSquare(-Joy1.getY() + Joy1.getX() - Joy2.getX()));
        backLeftMotor.set(specialSquare(Joy1.getY() - Joy1.getX() - Joy2.getX()));
        frontRightMotor.set(specialSquare(-(-Joy1.getY() - Joy1.getX() + Joy2.getX())));
        backRightMotor.set(specialSquare(-(Joy1.getY() + Joy1.getX() + Joy2.getX())));
    }
    
    //Squares values but preserves negatives
    public double specialSquare(double number) {
    	if(number>0)
    	{
    		return Math.pow(number, 2);
    	}
    	else {
    	return (-Math.pow(number, 2));
    	}
    	
    }
 }

