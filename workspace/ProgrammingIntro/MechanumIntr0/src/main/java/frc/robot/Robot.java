package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.AnalogInput;

public class Robot extends TimedRobot {
    
    
    Victor m0;
    Victor m1;
    Victor m2;
    Victor m3;
    Joystick controller;
    MecanumDrive mec;
    double speed;
    CameraServer cam;
    AnalogInput infrared; // LEFT OFF HERE

  @Override
  public void robotInit() {
    m0 = new Victor(0);
    m1 = new Victor(1);
    m2 = new Victor(2);
    m3 = new Victor(3);
    controller = new Joystick(0);
    mec = new MecanumDrive(m0, m1, m2, m3);
    speed = 0.5;
    cam.getInstance().startAutomaticCapture();
    infrared = new AnalogInput(0);
  }
 
  @Override
  public void teleopPeriodic() {
    mec.driveCartesian(-controller.getRawAxis(1)*speed, controller.getRawAxis(0)*speed, controller.getRawAxis(2)*speed);
  }
}