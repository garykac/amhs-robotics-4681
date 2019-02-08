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
            System.out.println("Extender Out");
            m_sucker.set(suckState);
            System.out.println("Sucker On");
        }
        else if (suckState ==false){
            m_sucker.set(suckState);
            System.out.println("Extender in");
            m_extender.set(suckState);
            System.out.println("Sucker Off");
        }
    }
}