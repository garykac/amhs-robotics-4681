package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CameraServer;
//import edu.wpi.first.wpilibj.Ultrasonic;

public class Robot extends TimedRobot {
    
    
    Victor m0;
    Victor m1;
    Victor m2;
    Victor m3;
    Joystick controller;
    MecanumDrive mec;
    double speed;
    CameraServer cam;
    //Ultrasonic ultra; // LEFT OFF HERE

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
    //ultra = new Ultrasonic(0,1);
  }
 
  @Override
  public void autonomousPeriodic() {
    for (int i=0; i < 20; i++) {
      mec.driveCartesian(.6,0,0);
    }
    mec.driveCartesian(0,0,0);
  }

  @Override
  public void teleopPeriodic() {
    mec.driveCartesian(-controller.getRawAxis(1)*speed, controller.getRawAxis(0)*speed, controller.getRawAxis(2)*speed);
    //System.out.println(ultra.getRangeInches());
  }

  @Override
  public void testPeriodic() {
    mec.driveCartesian(-controller.getRawAxis(1), controller.getRawAxis(0), controller.getRawAxis(2));
    //System.out.println(ultra.getRangeInches());
  }
}