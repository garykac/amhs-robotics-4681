
package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Manages the pneumatics for the walker.
 */
public class WalkerLegs {

    private Solenoid m_frontLegs;
    private Solenoid m_frontLegs_2;
    private Solenoid m_backLegs;
    private Solenoid m_walk;

    public void WalkerLegsInit() {
        m_frontLegs = new Solenoid(Constants.kPCMLegsFront);
        m_frontLegs_2 = new Solenoid(Constants.kPCMLegsFront_2);
        m_backLegs = new Solenoid(Constants.kPCMLegsBack);
        m_walk = new Solenoid(Constants.kPCMLegsWalk);

        m_frontLegs.set(false);
        m_frontLegs_2.set(false);
        m_backLegs.set(false);
        m_walk.set(false);
    }

    public void ExtendFront(boolean extend) {
        m_frontLegs.set(extend);
        m_frontLegs_2.set(extend);
    }

    public void ExtendBack(boolean extend) {
        m_backLegs.set(extend);
    }

    public void Walk(boolean walk) {
        m_walk.set(walk);
    }
}
