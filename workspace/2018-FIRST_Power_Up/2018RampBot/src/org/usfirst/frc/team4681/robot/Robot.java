/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


/**By the way, Ctrl+F11 will build the robot program to the roboRio.
 * 
 * "I wonder if this material is radioactive," said Marie curiously."
 * */

package org.usfirst.frc.team4681.robot;

import org.opencv.core.Mat;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
//import org.ejml.simple.SimpleMatrix;

import edu.wpi.first.wpilibj.CameraServer;




import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/* TODO:
 * 1. Test and use encoders
 * 2. Test smooth victors and fix robot curvature
 * 3. Implement vector addition forward kinematics
 * 4. Perfect/Refine autonomous
 * 5. other stuff?
 * 
 */
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	//xbox controller 
	Joystick controller;
	boolean manualOverride = true;
	 /* Buttons:
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
	/*final int attack =1;
	 * final int rightBumper =4;
	 * final int rightTrigger=2;
	 * final int leftBumper=5;
	 * final int leftTrigger=2;
	 */
	final int buttonX = 1;
	final int buttonA = 2;
	final int buttonB = 3;
	final int buttonY = 4;
	final int leftBumper = 5;
	final int rightBumper = 6;
	final int leftTrigger = 7;
	final int rightTrigger = 8;
	final int backButton = 9;
	final int startButton = 10;
	
	//Autonomous chooser
	public static final String kAuto1= "Station 1";
	public static final String kAuto2 = "Station 2";
	public static final String kAuto3 = "Station 3";
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	//wheel numbers
	private final int frontLeft = 1;
	private final int backLeft = 0;
	private final int backRight = 2;
	private final int frontRight = 3;
	
	//Pneumatics
	Compressor compressor;
	private final int frontExtendPort = 0;
	private final int frontClampPort = 1;
	private final int rearExtendPort = 2;
	private final int rearClampPort = 3;
	Solenoid frontExtend;
	Solenoid frontClamp;
	Solenoid rearExtend;
	Solenoid rearClampOpen;
	
	//Encoders
	Encoder[] encoders;
	double[] pulsesPerRev;
	
	//pre-made mecanum drive class
	MecanumDrive robotDrive;
	
	//Climber motors
	Victor motor4;
	Victor motor5;
	Victor motor6;
	
	//forward kinematics
	/*//wheel offsets
	double[][] wheelOffsets;
	//wheel positions 
	double[][] wheelPositions;
	//mecanum wheel thetas (measured counterclockwise from straight forward
	double[] theta;
	SimpleMatrix R;
	double[][] RData;
	SimpleMatrix V;
	SimpleMatrix Omega;
	double[][] OmegaData;
	 //measured in inches from the robot's starting position
	 //measured in radians counterclockwise from the initial heading of the robot
	double wheelRadius = 4.0; //inches
	double framesPerSecond = 50;
	boolean tryForwardKin = false;
	int forwardKinTimer = 0;
	int forwardKinDelay = 10;*/
	/*double naiveXSpeed = 1.0;
	double naiveYSpeed = 2.0;
	double naiveRotSpeed = 0.05;
	double xPos, yPos = 0.0;
	double direction = 0.0;*/
	
	
	//Autonomous
	AutonomousRoutine routine;
	boolean lastFinished;
	//Camera
	
	CameraServer cameraServer;
	Mat frontFrame;
	Mat backFrame;
	
	
	CvSink frontCamera;
	CvSink backCamera;
	CvSource mainCameraOutput;
	CvSource secondaryCameraOutput;
	boolean camerasInverted = false;
	
	//Macros
	int macroIndex = 0;
	int macroTimer = 0;
	Preferences prefs;
	int case1Delay1;
	int case1Delay2;
	int case2Delay0;
	int case2Delay1;
	int case2Delay2;
	
	Victor frontLeftVictor;// = new AdjustedVictor(frontLeft, 1.0, 1.0); //1.0
	
	Victor backLeftVictor;// = new AdjustedVictor(backLeft, 1.00, 1.0); //1.03
	Victor backRightVictor;// = new AdjustedVictor(backRight, 1.00, 1.0);//1.03
	Victor frontRightVictor;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		case1Delay1 = 80;
		case1Delay2 = 190;
		case2Delay0 = 10;
		case2Delay1 = 90;
		case2Delay2 = 65;
		prefs = Preferences.getInstance();
		//SmartDashboard.putBoolean("Try Forward Kinematics", tryForwardKin);
		//SmartDashboard.putNumber("Forward Kinematics Delay", forwardKinDelay);
		//Controller initialization
		controller = new Joystick(0);
				
		//Pneumatics initialization:
		compressor = new Compressor();
		
		frontExtend = new Solenoid(frontExtendPort);
		frontClamp = new Solenoid(frontClampPort);
		rearExtend = new Solenoid(rearExtendPort);
		rearClampOpen = new Solenoid(rearClampPort);
		
		frontExtend.set(false);
		frontClamp.set(false);
		rearExtend.set(false);
		rearClampOpen.set(false);
		//Encoder initialization:
		//Encoder on wheel n goes in ports 2n and 2n+1 (so wheel 0 goes to ports 0 and 1, wheel 1 goes to ports 2 and 3 etc..).
		//Encoding type k4x counts rising and falling signals on both channels, see wpilib javadoc for more details
		encoders = new Encoder[4];
		encoders[frontLeft] = new Encoder(frontLeft*2,(frontLeft*2)+1,  false, EncodingType.k4X);
		encoders[frontRight]= new Encoder(frontRight*2,(frontRight*2)+1, false, EncodingType.k4X);
		encoders[backLeft] = new Encoder(backLeft*2,(backLeft*2)+1,  false, EncodingType.k4X);
		encoders[backRight] = new Encoder(backRight*2,(backRight*2)+1,  false, EncodingType.k4X);
		pulsesPerRev = new double[] {-250.0, 250.0, -250.0, 250.0}; //Still needs to be verified
		
		//MecanumDrive initialization, this is probably trash
		Victor frontLeftVictor = new AdjustedVictor(frontLeft, 0.92);// = new AdjustedVictor(frontLeft, 1.0, 1.0); //1.0
	
		Victor backLeftVictor = new AdjustedVictor(backLeft, 0.95);// = new AdjustedVictor(backLeft, 1.00, 1.0); //1.03
		Victor backRightVictor = new AdjustedVictor(backRight, 0.95);// = new AdjustedVictor(backRight, 1.00, 1.0);//1.03
		Victor frontRightVictor = new AdjustedVictor(frontRight, 0.92);// = new AdjustedVictor(frontRight,1.0, 1.0); //1.0
		
		robotDrive = new MecanumDrive(frontLeftVictor, backLeftVictor, frontRightVictor, backRightVictor);
		
		//Climber motors
		motor4 = new Victor(4);
		motor5 = new Victor(5);
		motor6 = new Victor(6);
		//SmartDashboard.putNumber("Motor 4 Speed", 0.0);
		//SmartDashboard.putNumber("Motor 5 Speed", 0.0);
		//SmartDashboard.putNumber("Motor 6 Speed", 0.0);
		
		//Forward kinematics
		/*
		theta = new double[] {-Math.PI/4,Math.PI/4,-Math.PI/4,Math.PI/4};
		R = new SimpleMatrix(4,3)5
		V = new SimpleMatrix(3,1);
		Omega = new SimpleMatrix(1,4);
		//check this stuff mendems !
		wheelOffsets = new double[][] { {-13.0625, 10.25}, //front left inches
										{-13.0625, -10.25}, //back left 
										{13.0625, -10.25}, //back right
										{13.0625, 10.25}}; //front right
		wheelPositions = new double[4][2];
		RData = new double[4][3];
		OmegaData = new double[1][4];*/
		
		//Autonomous setup:
		//routine = AutonomousChooser.getAutonomousRoutine(station, targetSide)
		//SmartDashboard.getNumber("Station", 1);
		//SmartDashboard.putNumber("Side", 0);
		
		//Autonomous choice menu:
		m_chooser.addDefault("Station 1", kAuto1);
		
		m_chooser.addObject("Station 2", kAuto2);
		m_chooser.addObject("Station 3", kAuto3);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		//Cameras
		cameraServer = CameraServer.getInstance();
		cameraServer.startAutomaticCapture("Front Camera", 0);
		cameraServer.startAutomaticCapture("Back Camera", 1);
		
		/*frontCamera = cameraServer.getVideo("Front Camera");
		backCamera = cameraServer.getVideo("Back Camera");
		mainCameraOutput = cameraServer.putVideo("Main", 640, 480);
		secondaryCameraOutput = cameraServer.putVideo("Secondary", 320, 240);
		frontFrame = new Mat();
		backFrame = new Mat();*/
		//robotDrive.setSafetyEnabled(false);
		//int station = DriverStation.getInstance().getLocation();
		
		
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. 
	 * You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		routine = AutonomousChooser.getAutonomousRoutine(m_chooser.getSelected(), gameData.charAt(0));
		System.out.println("autonomousInit: We chose this routine:\n" + routine.toString());
	}

	/**
	 * This function is called periodically during autonomous.
	 * Chris- Now my question is, we get a routine. In periodic, we call 'update' on it.
	 * Does that make it drive? Okay... I read[pret] a bit more (read pres, and read pret sound the same.) 
	 * See below.
	 */
	@Override
	public void autonomousPeriodic() {
		//robotDrive.driveCartesian(0.5, 0.5, 0.0); //
		routine.update(robotDrive, rearExtend, rearClampOpen);
		/**This takes our routine and 'updates' it. The update method/function/thingy takes in
		 * a MecanumDrive and two Solenoids. And that's where things get good. The timer starts counting up
		 * and the arms start operating. The AutonomousRouting thingy got movements that it can operate on, and a class-made
		 * movementIndex. It asks if some movement within this array is finished, and that goes back to AutonomousMovement.
		 * Lots of jumping around. If not, keep driving: complicated jumping around again. This goes into
		 * the AutoMovement.move(drive) thing, and that's where we have trouble. It doesn't drive. 
		 * I am concerned with posX (go to AutoMove.java now)
		 * 
		 */
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopInit() 
	{
		compressor.start();
		for(int wheel = 0; wheel < 4; wheel++)
		{
			encoders[wheel].reset();
		}
		//robotDrive.setSafetyEnabled(false);
	
	}
	@Override
	public void teleopPeriodic() {
		case1Delay1 = 50;
		case1Delay2 = 190;
		case2Delay0 = 10;
		case2Delay1 = 70;
		case2Delay2 = 95;
		SmartDashboard.putBoolean("Manual Override: ", manualOverride);
		if(controller.getRawButtonPressed(buttonY)) {
			manualOverride = !manualOverride;
			macroIndex = 0;
			macroTimer = 0;
			frontClamp.set(false);
			frontExtend.set(true);
			rearClampOpen.set(true);
			rearExtend.set(false);
			
		}
		if(!manualOverride) {
			macroTimer++;
			switch(macroIndex) {
			case 0:
				frontClamp.set(false);
				frontExtend.set(true);
				rearClampOpen.set(true);
				rearExtend.set(false);
				//A true rearClamp is actually unclamped!
				break;
			case 1:
				frontClamp.set(true);
				if(macroTimer > case1Delay1)
					frontExtend.set(false);
				if(macroTimer > case1Delay2)
					rearClampOpen.set(false);
				break;
			case 2:
				frontClamp.set(false);
				if(macroTimer > case2Delay0)
					rearExtend.set(true);
				if(macroTimer > case2Delay1)
					rearClampOpen.set(true);
				if(macroTimer > case2Delay2)
					rearExtend.set(false);
				break;
			
			}
			if(controller.getRawButtonPressed(buttonA)) {
				if(macroIndex < 2) {
					macroIndex++;
				} else {
					macroIndex = 0;
				}
				macroTimer = 0;
			}
		} else {
			if (controller.getRawButtonPressed(rightTrigger))
			{
				frontExtend.set(!frontExtend.get());
			}
			if(controller.getRawButtonPressed(rightBumper))
			{
				frontClamp.set(!frontClamp.get());
			}
			if(controller.getRawButtonPressed(leftTrigger))
			{
				rearExtend.set(!rearExtend.get());
			}
			if(controller.getRawButtonPressed(leftBumper))
			{
				rearClampOpen.set(!rearClampOpen.get());
			}
		}
		//forwardKinTimer++;
		robotDrive.driveCartesian(controller.getRawAxis(0), -controller.getRawAxis(1), controller.getRawAxis(2));
		
		//frontCamera.grabFrame(frontFrame);
		
		//backCamera.grabFrame(backFrame);
		// built-in mecanum drive class, takes x, y and rotational motion
		/**
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		if(!camerasInverted) {
			robotDrive.driveCartesian(controller.getRawAxis(0), -controller.getRawAxis(1), controller.getRawAxis(2));
			mainCameraOutput.putFrame(frontFrame);
			secondaryCameraOutput.putFrame(backFrame);
		} else {
			robotDrive.driveCartesian(-controller.getRawAxis(0), controller.getRawAxis(1), controller.getRawAxis(2));
			mainCameraOutput.putFrame(backFrame);
			secondaryCameraOutput.putFrame(frontFrame);
		}
		
	
		if(controller.getRawButtonPressed(buttonX))
		{	camerasInverted = !camerasInverted;	}
		
		if(controller.getRawButton(buttonA))
		{
			motor4.set(SmartDashboard.getNumber("Motor 4 Speed", 0.0));
		}
		if(controller.getRawButton(buttonY)) 
		{
			motor5.set(SmartDashboard.getNumber("Motor 5 Speed", 0.0));
		}
		motor6.set(controller.getRawAxis(3)*SmartDashboard.getNumber("Motor 6 Speed", 0.0));
		
		//SmartDashboard.putNumber("Front Left Encoder", encoders[frontLeft].getRate());
		//SmartDashboard.putNumber("Front right encoder", encoders[frontRight].getRate());
		//SmartDashboard.putNumber("Back left encoder", encoders[backLeft].getRate());
		//SmartDashboard.putNumber("Back Right Encoder", encoders[backRight].getRate());
		
		//SmartDashboard.putBoolean("Trying Forward Kinematics", tryForwardKin);
		/*tryForwardKin = SmartDashboard.getBoolean("Try Forward Kinematics", false);
		forwardKinDelay = (int)SmartDashboard.getNumber("Forward Kinematics Delay", 10);
		for(int wheel = 0; wheel < 4; wheel++) {
			OmegaData[0][wheel] += 2 * Math.PI * encoders[wheel].getRate()/pulsesPerRev[wheel];
		}
		if(forwardKinTimer >= forwardKinDelay) {
			System.out.println("Forward kinematics timer triggered");
			if(tryForwardKin) {
				System.out.println("Here goes nothing");// this might be way too complex of a calculation now that I think about, idk if the RoboRIO can keep time
				double[] forward = new double[] {-Math.sin(direction), Math.cos(direction)}; // unit vector towards the front of the robot from the center
				double[] right = new double[] {Math.cos(direction), Math.sin(direction)}; //unit vector towards the right side of the robot from the center
				
				for(int wheel = 0; wheel < 4; wheel++)
				{
					wheelPositions[wheel][0] = wheelOffsets[wheel][1]*forward[0] + wheelOffsets[wheel][0]*right[0]; //use forward and right unit vectors to calculate where the wheels are
					wheelPositions[wheel][1] = wheelOffsets[wheel][1]*forward[0] + wheelOffsets[wheel][0]*right[0]; 
					RData[wheel][0] = 1.0;
					double t = Math.tan(theta[wheel]);
					RData[wheel][1] = t;
					RData[wheel][2] = wheelPositions[wheel][0]*t - wheelPositions[wheel][1];
					OmegaData[0][wheel] /= forwardKinDelay; //converts pulses per second to radians per second
				}
				R = new SimpleMatrix(RData);
				Omega = new SimpleMatrix(OmegaData);
				SimpleMatrix temp1 = R.transpose().mult(R).invert();
				V = (temp1.mult(R.transpose()).mult(Omega));
				xPos += wheelRadius*V.get(0, 0)/framesPerSecond; //MAKING SURE TO CONVERT FROM INCHES PER SECOND TO INCHES PER FRAME
				yPos += wheelRadius*V.get(1, 0)/framesPerSecond;
				direction += wheelRadius*V.get(2,0)/framesPerSecond; //converting from RAD/SEC to RAD/FRAME
				double xVelMph = 3600*V.get(0,0)/63360; // convert inches/second to mph
				double yVelMph = 3600*V.get(1,0)/63360;
				SmartDashboard.putNumber("Speed (mph)", Math.sqrt(Math.pow(xVelMph, 2) + Math.pow(yVelMph, 2))); //sanity check
				SmartDashboard.putNumber("X Position (inches)", xPos);
				SmartDashboard.putNumber("Y Position (inches)", yPos);
				SmartDashboard.putNumber("Direction (degrees)", 180.0*direction/Math.PI);
			}
			forwardKinTimer = 0;
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		*/
		
	}

	@Override
	public void testInit()
	{
		compressor.start();
	}
	@Override
	public void testPeriodic() {
	}
}
