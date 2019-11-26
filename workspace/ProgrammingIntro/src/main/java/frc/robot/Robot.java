/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class Robot extends TimedRobot {
    Victor m0;
    Victor m1;
    Joystick controller;
    double speed;

    @Override
    public void robotInit(){
        m0 = new Victor(0);
        m1 = new Victor(1);
        controller = new Joystick(0);
    }

    @Override
    public void teleopInit(){
        speed = 0.5;
    }

    @Override
    public void teleopPeriodic() {
        if(controller.getRawButton(2))
            speed = 0.7;
        if(controller.getRawButton(3))
            speed = 0.4;
        if(controller.getRawButton(1))
            speed += 0.1;
        if(controller.getRawButton(4))
            speed += -0.1;
        if(speed < 0.1){
            speed = 0.1;
        }
        if(speed > 1){
            speed = 1;
        }
        m0.set(controller.getRawAxis(1)*speed);
        m1.set(controller.getRawAxis(3)*speed);
    }
}