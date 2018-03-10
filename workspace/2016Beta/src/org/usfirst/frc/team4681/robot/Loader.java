//2016 Robot Code
package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.*;

public class Loader {
	//init motor controller
	private Victor bar;
	
	//constructor
	public Loader(int ctrler){
		bar = new Victor(ctrler);
	}
	
	/*if b1 is pressed, load in
	if b2 is pressed, push out*/
		public void loadBall(boolean b1, boolean b2){
		if(b1){
			bar.set(1);
		}
		if(b2){
			bar.set(-1);
		}
		if(!b1 && !b2){
			bar.set(0);
		}
	}
		
		public void loadBall(double x){
			bar.set(x);
		}
	
	//load in for set time
	public void loadIn(double time){
		bar.set(1);
		for(double i =0; i < time; i += .05){
			Timer.delay(.05);
		}
		bar.set(0);
	}
	
	//push out for set time
	public void loadOut(double time){
		bar.set(-1);
		for(double i =0; i < time; i += .05){
			Timer.delay(.05);
		}
		bar.set(0);
	}
}
