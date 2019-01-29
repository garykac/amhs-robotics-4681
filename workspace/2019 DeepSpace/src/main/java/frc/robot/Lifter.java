package frc.robot;
import edu.wpi.first.wpilibj.PWMVictorSPX;


/**
 * Lifter robot code for 2019.
 */

public class Lifter {
    private static final int kPWMLifter = 6;
    PWMVictorSPX m_lifter;
     public void LiftInit(){
        m_lifter = new PWMVictorSPX(kPWMLifter);
        //change from true or false depending on which works.
        m_lifter.setInverted(true);
  }
    public void Lift(){
        m_lifter.set(1);
  }
}

