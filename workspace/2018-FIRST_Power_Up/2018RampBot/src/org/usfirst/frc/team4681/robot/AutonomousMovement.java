package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class AutonomousMovement {
	public static double adjustmentX = 0.4; //Fudge factor for sideways movment is flawed
	public static double adjustmentY = 1.2; //adjX refers to Y, adjY refers to X. Our robot is stupid.
	private static double motorSpeedX = 1.0;
	private static double motorSpeedY = 1.0;
	private double deltaX;
	private double deltaY;
	private double posX;
	private double posY;
	public AutonomousMovement(double x, double y)
	{
		this.deltaX = x*adjustmentX; 
		this.deltaY = y*adjustmentY;
	}
	@Override
	public String toString()
	{
		return "AutonomousMovement deltaX: " + deltaX + "deltaY: " + deltaY;
	}
	public void adjustForOrientation(Orientation o) { //converts global coordinates to local coordinates
		double globalX = deltaX;
		double globalY = deltaY;
		switch(o) {
		
		case Forward: //No adjustment necessary
			break;
		case Backward: //relative is opposite of global
			deltaX = -globalX;
			deltaY = -globalY;
			break;
		case Left: //relative X = global y, relative y = negative global x
			deltaX = globalY;
			deltaY = -globalX;
			break;
		case Right: //relative X = global y, relative y = global x
			deltaX = -globalY;
			deltaY = globalX;
			break;
		}
	}
	public void move(MecanumDrive drive) 
	{
		double ySpeed = 0.0;
		double xSpeed = 0.0;
		double zRotation = 0.0;
		if(posX < deltaX) {
			xSpeed = motorSpeedX;
			posX++;
/*
 	* This guy right here I'm concerned with. posX isn't set to anything, nor is it added to nor subtracted from. never mind, its in robot.java 
 	* When above it says deltaX = deltaX*adjX, delta X doesn't equal anything except a variable name.
	* And is adjX multiplied by a None type thing, then?
*/
		} else if(posX > deltaX) {
			xSpeed = -motorSpeedX;
			posX--;
		}
		if(posY < deltaY) {
			ySpeed = motorSpeedY;
			posY++;
		} else if (posY > deltaY) {
			ySpeed = -motorSpeedY;
			posY--;
		}
		
		drive.driveCartesian(xSpeed, ySpeed, zRotation);
		//Above statement: the robot is screwed. 
	}
	public boolean isFinished()
	{
		double absPosX = Math.abs(posX); //our movement could be negative
		double absPosY = Math.abs(posY);
		double absDeltaX = Math.abs(deltaX);
		double absDeltaY = Math.abs(deltaY);
		return (absPosX >= absDeltaX && absPosY >= absDeltaY);
	}
	public AutonomousMovement scalarMult(double s)
	{
		return new AutonomousMovement(deltaX*s, deltaY*s);
	}
	
}
