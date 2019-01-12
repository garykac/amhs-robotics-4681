package org.usfirst.frc.team4681.robot;

public class Position {
	public double x;
	public double y;
	public double direction;
	public Position(double x,double y, double direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	public Position(double x, double y) {
		this(x,y,0);
	}
}
