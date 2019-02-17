package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
/**
 * Manages the sucker for the hatch succer.
 */
public class Sucker {

    private Solenoid m_sucker;
    private Solenoid m_extender;
    private boolean isSucking;
    private boolean isExtending;


    public void SuckerInit() {
        m_sucker = new Solenoid(Constants.kPCMHatchSucker);
        m_extender = new Solenoid(Constants.kPCMHatchExtender);
        m_sucker.set(false);
        m_extender.set(false);
        isSucking = false;
        isExtending = false;
    }
  
    public void Suck() {
        isSucking = !isSucking;
        m_sucker.set(isSucking);
    }
    public void Extend(){
        isExtending = !isExtending;
        m_extender.set(isExtending);
    }
}