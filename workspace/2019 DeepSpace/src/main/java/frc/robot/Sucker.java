package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Manages the sucker for the hatch succer.
 */
public class Sucker {

    private static final int kPCMHatchSucker = 0;

    private Solenoid m_sucker;


    public void SuckerInit() {
        m_sucker = new Solenoid(kPCMHatchSucker);
        m_sucker.set(false);
    }
  
    public void Sucker(boolean suckState){
        m_sucker.set(suckState);
    }
}