package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends TimedRobot {

    Victor mo;
    Victor m1;
    Joystick controller;
    
    @Override
    public void robotInit(){
    mo = new Victor(0);
    m1 = new Victor(1);
    controller = new Joystick(0);
    }

    @Override
    public void teleopInit(){
    }

    @Override
    public void teleopPeriodic() {
        //xbox a b x y
        mo.set(-controller.getRawAxis(1));
        m1.set(controller.getRawAxis(5));
    }
}