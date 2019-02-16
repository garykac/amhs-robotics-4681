/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
  private Compressor m_compressor;
  private Solenoid m_solenoid;
  private Joystick m_joystick;

  @Override
  public void robotInit() {
    m_compressor = new Compressor(0);
    m_compressor.start();
    m_solenoid = new Solenoid(0);
    m_joystick = new Joystick(0);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {  }

  @Override
  public void autonomousPeriodic() {  }

  @Override
  public void teleopInit() {
    m_compressor.start();
  }

  @Override
  public void teleopPeriodic() {
    if (m_joystick.getRawButtonPressed(5))
      m_solenoid.set(true);
    if (m_joystick.getRawButtonPressed(1))
      m_compressor.start();
    System.out.println(m_compressor.enabled());
    System.out.println(m_compressor.getClosedLoopControl());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
