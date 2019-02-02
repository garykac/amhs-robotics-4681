
package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Manages the pneumatics for the walker.
 */
public class WalkerLegs {

    private static final int kPCMHatchVacuum = 0;
    private static final int kPCMHatchCylinder = 1;
    private static final int kPCMLegsFront = 2;
    private static final int kPCMLegsBack = 3;
    private static final int kPCMLegsWalk = 4;

    private Solenoid m_frontLegs;
    private Solenoid m_backLegs;
    private Solenoid m_walk;

    public void WalkerLegsInit() {
        m_frontLegs = new Solenoid(kPCMLegsFront);
        m_backLegs = new Solenoid(kPCMLegsBack);
        m_walk = new Solenoid(kPCMLegsWalk);

        m_frontLegs.set(false);
        m_backLegs.set(false);
        m_walk.set(true);
    }

    public void ExtendFront(boolean extend) {
        m_frontLegs.set(extend);
    }

    public void ExtendBack(boolean extend) {
        m_backLegs.set(extend);
    }

    public void Walk(boolean walk) {
        m_walk.set(!walk);
    }

}
