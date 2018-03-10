
package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	int driveCtr0 = 0;
	int driveCtr1 = 1;
	CameraServer camera = CameraServer.getInstance();
	int pwr = 3;
	
	private Joystick joy1;
	private Joystick joy2;
	private Victor left;
	private Victor right;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	joy1 = new Joystick(0);
    	joy2 = new Joystick(1);
    	left = new Victor(driveCtr1);
		right = new Victor(driveCtr0);
		
		camera.setQuality(50);
    	camera.startAutomaticCapture("cam0");
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    
    }
    
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
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	double turning = joy2.getX();
    	/*if(Math.abs(turning) >= .15){
    		turn(turning);
    	} else {
    	*/	drive();
    	
    	if(joy1.getRawButton(6)) pwr++;
    	if(joy1.getRawButton(7)) pwr--;
    }
    
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    private void drive(){
    	double l = joy2.getY();
    	double r = joy1.getY();
    	if(joy2.getBumper()){
    		left.set(adjust(-l));
    		right.set(adjust(l));
    	}
    	left.set(adjust(-l));
    	right.set(adjust(r));
    }
    
    private void turn(double rate){
    	if(rate < 0){
    		left.set(Math.pow(rate, pwr));
    		right.set(Math.pow(rate, pwr));
    	}
    	if(rate > 0){
    		left.set(Math.pow(rate, pwr));
    		right.set(Math.pow(rate, pwr));
    	}
    }
    
}
