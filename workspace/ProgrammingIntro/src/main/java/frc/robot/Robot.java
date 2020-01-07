package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends TimedRobot {

    Victor mo;
    Victor m1;
    Joystick controller;
    double kSpeed;
    
    @Override
    public void robotInit(){
    mo = new Victor(0);
    m1 = new Victor(1);
    controller = new Joystick(0);
    }

    @Override
    public void teleopInit(){
        kSpeed = 0.6;
    }

    @Override
    public void teleopPeriodic() {
        //xbox a b x y
        mo.set(-kSpeed*controller.getRawAxis(1));
        m1.set(kSpeed*controller.getRawAxis(5));
        if (controller.getRawButtonPressed(4)) {
            if (kSpeed < 1)
                kSpeed += 0.2;
            System.out.println(kSpeed);
        }
        if (controller.getRawButtonPressed(1)) {
            if (kSpeed > 0)
            kSpeed -= 0.2;
        System.out.println(kSpeed);
        }
    }
}