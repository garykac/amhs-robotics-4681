package org.usfirst.frc.team4681.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.Vector2d;

public class AutonomousChooser {
	//Switch plate positions
	private static final double switchLeftX = 78.0;
	private static final double switchRightX = 186.0;
	private static final double switchY = 168.0;
	//Switch plate dimensions
	private static final double plateDepth = 48.0;
	private static final double plateWidth = 36.0;
	//Switch dimensions -- should probably use fence
	private static final double switchWidth = 144.0;
	private static final double switchDepth = 48.0;
	private static final double fenceWidth = 153.5;
	private static final double fenceDepth = 56;
	
	
	//Robot dimensions
	private static final double robotWidth = 27.0; //Local x axis dimension
	private static final double robotDepth = 32.0; //Local y axis dimension
	
	//station positions
	private static final double stationOneX = 16.0;
	private static final double stationOneY = 13.5;
	private static final double stationTwoX = 132.0;
	private static final double stationTwoY = 16.0;
	private static final double stationThreeX = 248.0;
	private static final double stationThreeY = 13.5;
	//diagonal estimates
	private static final double diagonalY = 100.0;  
	
	//That's 154.5 forward/left and 28 right/back for global/local L1
	
	public static AutonomousRoutine getAutonomousRoutine(String station, char targetSide) {
		ArrayList<AutonomousMovement> movements = new ArrayList<AutonomousMovement>();
		AutonomousMovement baseline = new AutonomousMovement(0.0, switchY); //Goes forward from station one or three next to the switch  (crosses baseline
		AutonomousMovement backToSwitch = new AutonomousMovement((switchLeftX - stationOneX) - robotDepth/2, 0); //Moves from the position next to the switch up to the edge of the switch 
		AutonomousMovement backToSwitch2 = new AutonomousMovement(0.0, ((switchY - switchDepth) - diagonalY) - robotDepth/2); //Moves forward after station 2 diagonal movement
		AutonomousMovement diagonalL = new AutonomousMovement(switchLeftX - stationTwoX, diagonalY); //Moves diagonally left from station 2
		AutonomousMovement diagonalR = new AutonomousMovement(switchRightX - stationTwoX, diagonalY); //Moves diagonally right from station 2 
		/* The AutonomousMovement class method thingy in the other file does some delta stuff for itself*/
		boolean shouldDropCube = false;
		Orientation orientation = Orientation.Forward; //Default orientation so that the compiler doesn't yell
		switch(station) {	
			case(Robot.kAuto1):
				orientation = Orientation.Left;
				if (targetSide == 'L') { //Station 1, left switch
					movements.add(baseline);
					movements.add(backToSwitch);
					shouldDropCube = true;
				} else { //Station 1, right switch, so we're crossing the baseline
					movements.add(baseline);
				}
				break;
			case(Robot.kAuto2):
				orientation = Orientation.Backward;
				if (targetSide == 'L') {
					movements.add(diagonalL);
					movements.add(backToSwitch2);
					shouldDropCube = true;
				} else if (targetSide == 'R') {
					movements.add(diagonalR);
					movements.add(backToSwitch2);
					shouldDropCube = true;
				} 
				break;
			case(Robot.kAuto3):
				orientation = Orientation.Right;
				if (targetSide == 'R') { //Station 1, right switch
					movements.add(baseline);
					movements.add(backToSwitch.scalarMult(-1.0));
					shouldDropCube = true;
				} else { //Station 1, left switch, so we're going to cross baseline
					movements.add(baseline);
				}
				break;
		}
		return new AutonomousRoutine(movements, orientation, shouldDropCube);
	/** 
	 	 * All this code (lines 1 to 80) results in this return function that gives
		 * an array of movements that we should follow. Other files act on the movement one
		 * at a time. We also return the orientation of the robot. Other files will take this orientation
		 * and adjust our movements. We are finally given a boolean (T/F) shouldDropCube
		 * that, when we tested our robot M3/19/18, ran right away. As in, our robot didn't move.
		 * This can be followed further in the file AutonomousRoutine.java
	**/
	}
	
}