//2016 code; Controls low goal shooter
package org.usfirst.frc.team4681.robot;

import edu.wpi.first.wpilibj.*;

public class Shooter {
	private Victor s1;
	private Victor s2;
	private double speed = .75;
	private double speed0 = .3;
	
	boolean trig=false;
	boolean b1 = false;
	public Shooter(int ctrler0, int ctrler1){
		s1 = new Victor(ctrler0); // was 3
		s2 = new Victor(ctrler1); // was 4
	}
	
	public void projectileLaunch(boolean trigger){
		trig = trigger;
		if(trig){
			s1.set(speed);
			s2.set(-speed);
		} else if(!b1) {
			s1.set(0);
			s2.set(0);
		}
	}
	
	
	
	//moves slightly back wards if ball gets caught in wheels
	public void sendBack(boolean button){
		b1 = button;
		if(button){
			s1.set(-speed0);
			s2.set(speed0);
		} else if(!trig) {
			s1.set(0);
			s2.set(0);
		}
	}
	
	public void changeSpeed(boolean but1, boolean but2){
		if(but1){
			speed += .025;
		}
		if(but2){
			speed -= .025;
		}
	}
}
