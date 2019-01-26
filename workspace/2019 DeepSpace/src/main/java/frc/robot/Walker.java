
package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Manages the walking of the robot for 2019.
 */
public class Walker {

  private WalkerLegs m_legs;

  public void WalkerInit() {
    m_legs = new WalkerLegs();
    m_legs.WalkerLegsInit();
  }

  public void RaiseRobot() {
    m_legs.ExtendFront(true);
    m_legs.ExtendBack(true);
  }

  public void Walk() {
    m_legs.Walk(true);
  }

  public void RetractFrontLegs() {
      m_legs.ExtendFront(false);
  }

  public void RetractBackLegs() {
    m_legs.ExtendBack(false);
  }
}
