/**
 * 
 * CODE FOR 2016
 * Only to be used as reference
 * 
 */
package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.*;
/*
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
*/
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
	//values to be set later
	double distanceTo = 0;
	boolean altCtrl;
	boolean reverse = false;
	
	//values for controllers
	int driveCtr0 = 0;
	int driveCtr1 = 1;
	int loadCtr = 2;
	int shootCtr0 = 3;
	int shootCtr1 = 4;
	
	//inputs
	String camForward = "cam0";
	String camReverse = "cam2";
	
	int rangeSensorIn = 0;
	
	//range sensor
	AnalogInput rangeIn = new AnalogInput(rangeSensorIn);
	
	//inputs and output classes
	private Drive move;
	private Shooter shoot;
	private Loader load;
	private Joystick joy1;
	private Joystick joy2;
	//CameraServer camera = CameraServer.getInstance(); 
	
	/**stuff for camera (most of it was copy and pasted last minute, need to take time to understand later)
	Image frame;
	int forwardSession;
	int reverseSession;
	int viewSession;
	NIVision.Rect rect = new NIVision.Rect(120, 270, 100, 100);**/
	
    public void robotInit() {
    	//defining inputs and outputs upon startup
    	move = new Drive(driveCtr0, driveCtr1);
    	joy1 = new Joystick(0);
    	joy2 = new Joystick(1);
    	shoot = new Shooter(shootCtr0, shootCtr1);
    	load = new Loader(loadCtr);
    	/*
    	//for camera
    	camera.setQuality(50);
    	camera.startAutomaticCapture("cam0");
		
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	forwardSession = NIVision.IMAQdxOpenCamera(camForward, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
    	reverseSession = NIVision.IMAQdxOpenCamera(camReverse, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
    	viewSession = forwardSession;
    	
    	NIVision.IMAQdxConfigureGrab(viewSession);
		*/
    }

    /**
     * This function is called periodically during autonomous
     * NOTE:THIS NEEDS TWEAKING	
     * LOTS OF IT
     */
    public void autonomousInit() {
    	move.autoDrive(1);
    	Timer.delay(3.5);
    	move.autoDrive(0);
    }

    
    public void telopInit(){
    	altCtrl=false;
    }
    
    /**
     * This function is called periodically during operator control
     */
   public void teleopPeriodic() {
	   /*
   		distanceTo = getRange();
   		if(distanceTo < 0) distanceTo = 0;
   		if(distanceTo > 5) distanceTo = 5;
   		SmartDashboard.putNumber("DB/Slider 0", distanceTo);
   		//debug code
   		SmartDashboard.putNumber("DB/Slider 1", rangeIn.getVoltage());
   		SmartDashboard.putNumber("DB/Slider 2", rangeIn.getAverageVoltage());
   		//button to switch direction of the robot (camera and drive)
   		 */
    	if(joy2.getRawButton(4) && !reverse){
    		reverse = true;
    		/*camera = null;
    		camera = CameraServer.getInstance();
    		camera.setQuality(50);
    	    camera.startAutomaticCapture(camReverse);
    		
    		NIVision.IMAQdxStopAcquisition(viewSession);
    		viewSession = reverseSession;
  	        NIVision.IMAQdxConfigureGrab(viewSession); 
    		*/
    	}
    	if(joy2.getRawButton(5) && reverse){
    		reverse = false;
    		/*camera = null;
    		camera = CameraServer.getInstance();
    		camera.setQuality(50);
    	    camera.startAutomaticCapture(camForward);
    		
    		NIVision.IMAQdxStopAcquisition(viewSession);
    		viewSession = forwardSession;
  	        NIVision.IMAQdxConfigureGrab(viewSession);
    		*/
    	}
    	
    	//NIVision.IMAQdxGrab(viewSession, frame, 1);
    	
    	/*draw reticle-ish thingy-ma-bobber
    	if(reverse){
    		NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
    	}
    	CameraServer.getInstance().setImage(frame);
    	*/
    	//to move, if reverse, controls are switched
    	/*if(reverse)
    		move.drive(-joy1.getRawAxis(5), -joy1.getRawAxis(5), joy2.getTrigger());    		
    	} 
    	else {*/
    		move.drive(joy1.getRawAxis(5),joy1.getRawAxis(1), joy2.getTrigger());

    	//}
    	
    	//plays with shooting speed value
    	shoot.changeSpeed(joy2.getRawButton(11), joy2.getRawButton(10));
        //if trigg is pressed, shoot
        shoot.projectileLaunch(joy1.getTrigger());
        //moves ball backwards if necessary
        shoot.sendBack(joy2.getRawButton(2));
        //if but3, load in, if but 2, push out
        load.loadBall(joy1.getRawButton(3),joy1.getRawButton(2));
        
   }
    
    //called when disabled
    public void disabledInit(){
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    //returns the range sensor input as meters
    public double getRange(){
    	return (rangeIn.getAverageVoltage()/.997);
    }
    
}
