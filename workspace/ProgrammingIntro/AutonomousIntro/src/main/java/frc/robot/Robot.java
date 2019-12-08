package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class Robot extends TimedRobot {

  Victor m0;
  Victor m1;
  Victor m2;
  Victor m3;
  MecanumDrive m_drive;
  int counter;

  @Override
  public void robotInit() {
    m0 = new Victor(0);
    m1 = new Victor(1);
    m2 = new Victor(2);
    m3 = new Victor(3);
    m_drive = new MecanumDrive(m0, m1, m2, m3);
  }


  @Override
  public void autonomousInit() {
    counter = 0;

  }

  @Override
  public void autonomousPeriodic() {
    counter++;
    if (counter < 80 && counter > 70) {
      m_drive.driveCartesian(0.6, 0, 0);
    } else if (counter > 50 && counter < 64) {
      m_drive.driveCartesian(0, 0, 1);
    } else if (counter < 20) {
      m_drive.driveCartesian(0.6, 0, 0);
     } else {
      m_drive.driveCartesian(0, 0, 0);
    }
  }
}
