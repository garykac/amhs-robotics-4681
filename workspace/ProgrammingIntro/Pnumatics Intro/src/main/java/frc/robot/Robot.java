package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
Compressor compressor;
Solenoid vacuum;
Joystick controller;

  @Override
  public void robotInit() {
  compressor = new Compressor(0);
  vacuum = new Solenoid(0);
  controller  = new Joystick(0);
  }
  
  @Override
  public void teleopInit() {
    compressor.start();
  }


  @Override
  public void teleopPeriodic() {
    //compressor.start();
    if (controller.getRawButton(6)){
      vacuum.set(true);
    } else {
      vacuum.set(false);
    }
  }


  @Override
  public void testPeriodic() {
  }
}
