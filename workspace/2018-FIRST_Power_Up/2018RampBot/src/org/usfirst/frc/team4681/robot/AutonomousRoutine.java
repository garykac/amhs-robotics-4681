package org.usfirst.frc.team4681.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class AutonomousRoutine 
{
	private ArrayList<AutonomousMovement> movements;
	private boolean shouldDropCube;
	private int armMacroTimer1 = 0;
	private int armMacroTimer2 = 0;
	private int movementIndex = 0;
	public AutonomousRoutine(ArrayList<AutonomousMovement> movements, Orientation orientation, boolean shouldDropCube)
	{
		this.movements = movements;
		this.shouldDropCube = shouldDropCube;
		for(AutonomousMovement m : movements)
		{
			m.adjustForOrientation(orientation);
		}
		
	}
	@Override
	public String toString()
	{
		String r = "AutonomousRoutine:";
		for(AutonomousMovement m : movements) {
			r += "\n\t" + m.toString();
		}
		return r;
	}
	public void update(MecanumDrive drive, Solenoid rearExtend, Solenoid rearClampOpen)
	{
		//System.out.println("Moving");
		/*move the back arms, clamp cube*/
		armMacroTimer1++;
		//Stage 1 - move back arms forward
		if(armMacroTimer1 > 0)
			rearExtend.set(true);
			rearClampOpen.set(true);
		/*Stage 2 - open clamp
		if(armMacroTimer1 > 40 && armMacroTimer1 < 120)
			rearClampOpen.set(true);*/
		//Stage 3 - Move arms back
		if(armMacroTimer1 > 80)
			rearExtend.set(false);
		//Stage 4 - Close clamp
		if(armMacroTimer1 > 150)
			rearClampOpen.set(false);
		if(movementIndex < movements.size())
		{
			
			if(!movements.get(movementIndex).isFinished()) //Make sure we're not doing something wrong around here
				//The isFinished thing is connected to 'posX' which I'm confused about
			{
				movements.get(movementIndex).move(drive);
			} else {
				movementIndex++;
			}
		} else { //Drop the cube if we should
			if(shouldDropCube) 
			{
				
				armMacroTimer2++;
				//Stage 5 - Extend the back arms (hopefully over the switch)
				if(armMacroTimer2 > 30)
					rearExtend.set(true);
				//Stage 6 - open clamp [ Chris- really cool is if we time it to throw the cube. :-) ]
				if(armMacroTimer2 > 110)
					rearClampOpen.set(true);
			}
		}
		
	}
}
