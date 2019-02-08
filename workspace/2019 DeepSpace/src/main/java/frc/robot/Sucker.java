package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
/**
 * Manages the sucker for the hatch succer.
 */
public class Sucker {

    private Solenoid m_sucker;
    private Solenoid m_extender;


    public void SuckerInit() {
        m_sucker = new Solenoid(Constants.kPCMHatchSucker);
        m_extender = new Solenoid(Constants.kPCMHatchExtender);
        m_sucker.set(false);
        m_extender.set(false);
    }
  
    public void Suck(boolean suckState) {
        if(suckState == true){
            m_extender.set(suckState);
            m_sucker.set(suckState);
        }
        else if (suckState ==false){
            m_sucker.set(suckState);
            m_extender.set(suckState);
        }
    }
}