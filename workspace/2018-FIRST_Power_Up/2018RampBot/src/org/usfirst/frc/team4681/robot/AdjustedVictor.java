package org.usfirst.frc.team4681.robot;

//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class AdjustedVictor extends Victor {
	double adjustment;
	double adjustmentPositive;
	double adjustmentNegative;
	public AdjustedVictor(int channel, double adjustmentPositive, double adjustmentNegative) {
		super(channel);
		this.adjustmentPositive = adjustmentPositive;
		this.adjustmentNegative = adjustmentNegative;
	}
	public AdjustedVictor(int channel, double adjustment)
	{
		this(channel, adjustment, adjustment);
	}
	@Override
	public void set(double speed) {
		/*System.out.println("Smooth Victor on channel " + super.getChannel() + ": speed set to " + speed + "(" + speed*maxSpeed + ")");
		double goalSpeed = speed*maxSpeed; //What speed do we want to turn at?
		double actualSpeed = encoder.getRate(); //What speed does the encoder say we're turning at?
		currSpeed += (goalSpeed-actualSpeed)/maxSpeed;
		/*Increase or decrease the speed we're telling the
		motor to spin based on the encoder's reported speed.*/
		if(speed > 0.0) {
			adjustment = adjustmentPositive;
		} else {
			adjustment = adjustmentNegative;
		}
		if(Math.abs(adjustment*speed) <= 1.0) {
			super.set(adjustment*speed);
		} else {
			super.set(1.0);
		}
	}
}
