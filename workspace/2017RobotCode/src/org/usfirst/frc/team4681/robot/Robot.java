/*code for 2017
 * */
package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
//import edu.wpi.first.wpilibj.Ultrasonic;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//Boolean used for autonomous periodic
	String choice;
	int counter = 0;
	
	/*Values for Motor Controllers*/
	int driveFrontLeft=0;
	int driveFrontRight=1;
	int driveRearLeft=2;
	int driveRearRight=3;
	//int loadMC=4;
	int climbMC=5;
	
	//System Motor Controllers
	Victor loader;
	Victor climber;
	
	CameraServer camera = CameraServer.getInstance();
	
	//Range Finder: first parameter is ping channel, second is echo channel.
	//Ultrasonic ultra = new Ultrasonic(1,1);
	
	RobotDrive drive;
	
	//Preferences for autonomous, the drive time of the first and second legs
	// and angles for the 6 different starting positions
	Preferences prefs;
	
	int centerLeg1;
	int baselineLeg;
	int leftRedLeg1, leftRedLeg2, leftRedTurn;
	int leftBlueLeg1, leftBlueLeg2, leftBlueTurn;
	int rightRedLeg1, rightRedLeg2, rightRedTurn;
	int rightBlueLeg1, rightBlueLeg2, rightBlueTurn;
	//Color tracking preferences for rope-aligment system:
	double trackingRed, trackingGreen, trackingBlue;
	/* Xbox controller notes:
	 * 5 Axis: 
	 * 0 = left stick X
	 * 1 = left stick Y
	 * 2 = right stick X
	 * 3 = right stick Y
	 
	 * 
	 * Button Layout:
	 * 1 = X
	 * 2 = A
	 * 3 = B
	 * 4 = Y
	 * 5 = Left bumper
	 * 6 = Right bumper
	 * 7 = Left trigger
	 * 8 = Right trigger
	 * 9 = Back Button
	 * 10 = Start Button
	 * 11 = Left Stick Click In
	 * 12 = Right Stick Click In
	 * 
	 * Note: After an update to the dashboard, this layout changed.
	 * It has since been updated, but this is a warning that this may not be accurate.
	 * To make sure you know the layout, plug in the controller and go to the smartdashboard.
	 * In the controller tab (bottom left side of the drive station) you should see the axiis
	 * and buttons. If you push an axis or a button and something lights up or changes, that
	 * is the associated axis/button. From top to bottom, left to right, the axiis start from 0,
	 * yet the buttons start at 1 (I think, i might have messed that up).
	 */
	Joystick xboxControl;
	
	SendableChooser<String> chooser;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 * 
	 * We are using the RobotDrive class to control
	 * the Drive system.
	 * 
	 * This should be pretty self-explanatory
	 */
	//Variables for rope tracker
	Mat image;
	CvSink backCamera;
	double[] colorToTrack;
	double maxDistance;
	boolean useThreshold;
	double threshold;
	double tolerance = 5.0;
	@Override
	public void robotInit() {
		System.out.println("Robot Init");
		drive = new RobotDrive(driveFrontLeft,driveRearLeft,driveFrontRight,driveRearRight);
		xboxControl = new Joystick(0);
		
		//loader = new Victor(loadMC);
		climber = new Victor(climbMC);
		
		//Inverts the right side motors so that the drive system works correctly
		//IE what would've been a value of 1 is now -1.
		drive.setInvertedMotor(MotorType.kFrontRight,true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		
		
		// Autonomous Chooser initialization; adds several choices then places them on dashboard.
		
		/*
		 * Here's how this works:
		 * SendableChooser is essentially a list of String objects.
		 * These objects have a sort of identifier, which is the second parameter (IE "baseline").
		 * The first parameter is the title of the object that will be displayed on the dashboard.
		 * The dashboard recieves the chooser data in the last line.
		 * 
		 * Note: You don't have to worry about the smartdashboard sending info back
		 * The code for that is integrated with the Java dashboard type, which is the type you should be using
		 * If you cant see the chooser, it seriously might be invisible in the top left
		 * corner of the Java dashboard. Make sure Editing is turned on if this occurs, and click wildly to find it.
		 * In its properties makes sure that the second option is not ticked off.
		 * Play with settings until it works again.
		 */
		
				chooser = new SendableChooser<String>();
				
				chooser.addDefault("Center Position (Test)", "center" );
				chooser.addObject( "Left or Right Position Baseline", "baseline");
				chooser.addObject("Right Position Blue", "rightb");
				chooser.addObject("Left Position Blue", "leftb");
				chooser.addObject("Right Position Red", "rightr");
				chooser.addObject("Left Position Red", "leftr");
		        SmartDashboard.putData("Autonomous mode chooser", chooser);
		        prefs = Preferences.getInstance();
		     
		        centerLeg1 = prefs.getInt("Center Leg 1", 52);
		        
		        //Left Red
		        leftRedLeg1 = prefs.getInt("Left Red Leg 1", 58);
		        leftRedLeg2 = prefs.getInt("Left Red Leg 2", 45);
		        leftRedTurn = prefs.getInt("Left Red Turn", 22);
		      //Left blue ADJUSTED2 leftBlueLeg1 = 53; leftBlueLeg2 = 72; leftBlueTurn = 18;
		        leftBlueLeg1 = prefs.getInt("Left Blue Leg 1", 53);
		        leftBlueLeg2 = prefs.getInt("Left Blue Leg 2", 72);
		        leftBlueTurn = prefs.getInt("Left Blue Turn", 18);
		      //Right blue ADJUSTED2 rightBlueLeg1 = 52; rightBlueLeg2 = 50; rightBlueTurn = 23;
		        rightBlueLeg1 = prefs.getInt("Right Blue Leg 1", 52);
		        rightBlueLeg2 = prefs.getInt("Right Blue Leg 2", 50);
		        rightBlueTurn = prefs.getInt("Right Blue Turn", 23);
		      //Right Red ADJUSTED2 rightRedLeg1 = 48; rightRedLeg2 = 72; rightRedTurn = 28;
		        rightRedLeg1 = prefs.getInt("Right Red Leg 1", 48);
		        rightRedLeg2 = prefs.getInt("Right Red Leg 2", 72);
		        rightRedTurn = prefs.getInt("Right Red Turn", 28);
		        //Baseline
		        baselineLeg = prefs.getInt("Baseline Leg", 75);
		        
		        trackingRed = prefs.getDouble("Tracking Red", 255.0);
		        trackingGreen = prefs.getDouble("Tracking Green", 0.0);
		        trackingBlue = prefs.getDouble("Tracking Blue", 0.0);
		        colorToTrack = new double[] {trackingBlue,trackingGreen,trackingRed};
		        
		        threshold = prefs.getDouble("Rope Tracker Threshold", 10.0);
		        useThreshold = prefs.getBoolean("Rope Tracker Use Threshold", true);
		        tolerance = prefs.getDouble("Rope Tracker Tolerance", 5.0);
		        //prefs.putInt("testResponse", testValue);
		//Camera stuff//
		        
		/*
		 * Cameras are terrifying when it comes to code.
		 * Liams code doesnt work because they cut functionality down for this year.
		 * I pray that they make it easier next year.
		 * 
		 * All I figured out how to do is start up the camera and have it stream.
		 * First parameter is the name assigned to the stream
		 * The second parameter is which USB port the camera uses.
		 */
		
		camera.startAutomaticCapture("Front Camera", 0);
		camera.startAutomaticCapture("Back Camera",1);
		backCamera = camera.getVideo("Back Camera");
		image = new Mat();
		backCamera.grabFrame(image);
		//prefs.putInt("matrix cols", image.cols());
		//prefs.putInt("matrix rows", image.rows());
	
		//Range Finder, another scrapped idea I guess.
		//ultra.setAutomaticMode(true);
		/*ERROR  1  ERROR Unhandled exception: java.lang.NullPointerException at [org.usfirst.frc.team4681.robot.Robot.robotInit(Robot.java:151), edu.wpi.first.wpilibj.IterativeRobot.startCompetition(IterativeRobot.java:64), edu.wpi.first.wpilibj.RobotBase.main(RobotBase.java:247)]  edu.wpi.first.wpilibj.RobotBase.main(RobotBase.java:249) 
ERROR Unhandled exception: java.lang.NullPointerException at [org.usfirst.frc.team4681.robot.Robot.robotInit(Robot.java:151), edu.wpi.first.wpilibj.IterativeRobot.startCompetition(IterativeRobot.java:64), edu.wpi.first.wpilibj.RobotBase.main(RobotBase.java:247)] 
WARNING: Robots don't quit! 
*/
        
        maxDistance = distance(new double[] {0.0,0.0,0.0}, new double[] {255.0,255.0,255.0});
	}
	
	
/*Called at the beginning of autonomous*/
	@Override
	public void autonomousInit() {
		System.out.println("Autonomous Init");
		counter = 0;
		choice = chooser.getSelected();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	//Periodic functions are called roughly every 20 ms. This means
	//that the code runs about 50 cycles per second.
	
	/*
	 * Here's how this works:
	 * choice is a string, determined by the SendableChooser/Smartdashboard interface.
	 * choice is used to access one of the possible autonomous codes.
	 * inside each choice is a check to see how long autonomous has run.
	 * It does this by increasing int counter at the end of each cycle
	 * When counter=50, for example, one second has elapsed.
	 * Depending on which part of autonomous, the robot will do one of three things;
	 * 1. Move forward (there is a small rotation value to counter the imperfection of the drive system)
	 * 2. Rotate (for gear delivery line-up)
	 * 3. Stop
	 */
	public void autonomousPeriodic() {
		
		// This should choose one of these autonomous programs, which should be tailored to start position.
		
		if(choice.equals("center")){
			if(counter>centerLeg1){
				drive.mecanumDrive_Cartesian(0, 0, 0, 0);
			}
			else{
				drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
			}
		}
		else if(choice.equals("leftr")){ //Left Red ADJUSTED2 leftRedLeg1 = 58; leftRedLeg2 = 45; leftRedTurn = 22;
			if(counter>leftRedLeg1+leftRedLeg2+leftRedTurn+10||counter>leftRedLeg1&&counter<leftRedLeg1+10){
				drive.mecanumDrive_Cartesian(0, 0, 0, 0);
			}
			else if(counter>leftRedLeg1+10 && counter<leftRedLeg1+10+leftRedTurn){
			drive.mecanumDrive_Cartesian(0, 0, .5, 0);	
			}
			
			else{
				drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
			
			}
		}
			
		else if(choice.equals("leftb")){ //Left blue ADJUSTED2 leftBlueLeg1 = 53; leftBlueLeg2 = 72; leftBlueTurn = 18;
			if(counter>leftBlueLeg2+10+leftBlueTurn+leftBlueLeg2||counter>leftBlueLeg1&&counter<leftBlueLeg1+10){
				drive.mecanumDrive_Cartesian(0, 0, 0, 0);
			}
			else if(counter>leftBlueLeg1+10 && counter<leftBlueLeg1+10+leftBlueTurn){
			drive.mecanumDrive_Cartesian(0, 0, .5, 0);	
			}
			
			else{
				drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
			}
		}
			
		else if(choice.equals("rightb")){ //Right blue ADJUSTED2 rightBlueLeg1 = 52; rightBlueLeg2 = 50; rightBlueTurn = 23;
			if(counter>rightBlueLeg1+rightBlueLeg2+rightBlueTurn+10||counter>rightBlueLeg1&&counter<rightBlueLeg1+10){
				drive.mecanumDrive_Cartesian(0, 0, 0, 0);
			}
			else if(counter>rightBlueLeg1+10 && counter<rightBlueLeg1+10+rightBlueTurn){
			drive.mecanumDrive_Cartesian(0, 0, -.5, 0);	
			}
			
			else{
				drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
			}
			
			
		}
		else if(choice.equals("rightr")){ //Right Red ADJUSTED2 rightRedLeg1 = 48; rightRedLeg2 = 72; rightRedTurn = 28;
			if(counter>rightRedLeg1+rightRedLeg2+rightRedTurn+10||counter>rightRedLeg1&&counter<rightRedLeg1+10){
				drive.mecanumDrive_Cartesian(0, 0, 0, 0);
			}
			else if(counter>rightRedLeg1+10 && counter<rightRedLeg1+10+rightRedTurn){
			drive.mecanumDrive_Cartesian(0, 0, -.5, 0);	
			}
			
			else{
				drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
			}
		}
		else if(choice.equals("baseline")){
			if(counter>baselineLeg){
				drive.mecanumDrive_Cartesian(0, 0, 0, 0);
			}
			else{
				drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
			}
		}
		
		counter++;
		
		/*
		 * This is what we used since the chooser wasn't working.
		 * Counts the number of times periodic is called,
		 * uses that to stop the robot.
		 * 
		 * 
		 
		if(counter>90){
			drive.mecanumDrive_Cartesian(0, 0, 0, 0);
		}
		else{
			drive.mecanumDrive_Cartesian(0,-.5,0, 0);
		}
		counter++;
		 */
	}

	/**
	 * This function is called periodically during operator control
	 *  
	 * This one is super easy. During teleop, the bot listens for controller inputs.
	 * Using the joystick inputs X, Y, and rotation vectors.
	 * Pushing buttons spins motors.
	 * If no buttons are pushed, motors are shut off. (A critical step that can be overlooked).
	 * Axis always have a value, so it usually just passes 0 when there is no user input to a joystick.
	 */
	//Calculate the euclidean distance between two BGR colors
	public double distance(double[] color1, double[] color2)
	{
		double b = Math.pow(color2[0]-color1[0], 2.0);
		double g = Math.pow(color2[1] - color2[1], 2.0);
		double r = Math.pow(color2[2]-color2[2], 2.0);
		return Math.sqrt(b+g+r);
	}
	
	@Override
	public void teleopPeriodic() {
		//image is a matrix that stores the latest image from the camera in BGR order
		//grabFrame fetches the image from the back camera and stores it in image
		double ropeAlign = 0.0;
		if(xboxControl.getRawButton(8)) {
		backCamera.grabFrame(image);
		trackingRed = prefs.getDouble("Tracking Red", 255.0);
	    trackingGreen = prefs.getDouble("Tracking Green", 0.0);
	    trackingBlue = prefs.getDouble("Tracking Blue", 0.0);
		double xsum = 0;
		int n = 0;
		for(int x = 0; x < image.cols();x++)
		{
			for (int y = 0; y < image.rows(); y++)
			{
				if(useThreshold){
					if(distance(colorToTrack, image.get(y, x)) < threshold) {
						xsum += x;
						n++;
					}
					
				}
				else {
					xsum += x*(distance(colorToTrack, image.get(y, x))/maxDistance);
					n++;
				}
					
			}
		}
		
		double xavg = 0;
		if(n != 0) {
			xavg = xsum/(image.cols()*image.rows());
		} else {
			xavg = image.cols()/2;
		}
		
		
			if(xavg < (image.cols()/2)-tolerance) {
				ropeAlign = 0.1;
			}
			if(xavg > (image.cols()/2)+tolerance) {
				ropeAlign = -0.1;
			}
			
			prefs.putDouble("xavg", xavg);
			prefs.putDouble("CenterB", image.get(image.rows()/2, image.cols()/2)[0]);
			prefs.putDouble("CenterG", image.get(image.rows()/2, image.cols()/2)[1]);
			prefs.putDouble("CenterR", image.get(image.rows()/2, image.cols()/2)[2]);
		}
		//Left Bumper
		if(xboxControl.getRawButton(5))
		{
			threshold--;
		}
		//Right Bumper
		if(xboxControl.getRawButton(6))
		{
			threshold++;
		}
		prefs.putDouble("Rope Tracker Threshold", threshold);
		drive.mecanumDrive_Cartesian(adjust(xboxControl.getRawAxis(0)),adjust(xboxControl.getRawAxis(1)),adjust(xboxControl.getRawAxis(2))+ropeAlign,0);
		
		//prefs.putDouble("pixel[0]", image.get(60, 60)[0]);
		//prefs.putDouble("pixel[1]", image.get(60, 60)[1]);
		//prefs.putDouble("pixel[2]", image.get(60, 60)[2]);
		//System.out.println(image.get(60, 60));
		
		//System Controls
		//Loader, bound to Y and A buttons (A spins it out, Y spins it in)
		
		/* This system was scrapped, so this can't be trusted as far as button inputs go.
		 * 
		if(xboxControl.getRawButton(4)==true){
		loader.set(.75);	
		}
		else if(xboxControl.getRawButton(1)==true){
			loader.set(-.75);
		}
		else{
			loader.set(0);
		}
		*/
		
		//Climber, binds it to left trigger axis
		
		climber.set(adjust(-Math.abs(xboxControl.getRawAxis(3))));
		
	}
	

	/**
	 * This function is called periodically during test mode
	 * 
	 * We clearly never used this. I don't know why you would use code that wouldn't be run the same way for competition.
	 * 
	 */
	@Override
	public void testPeriodic() {
	}
	
/*Taken from 2016 Robot code (Thanks Liam!). This is used to give the joystick input some sensitivity in teleop.
	 * 
	 * special function to allow better control of robot
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
	
}