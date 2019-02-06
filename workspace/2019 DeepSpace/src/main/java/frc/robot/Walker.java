
package frc.robot;


/**
 * Manages the walking of the robot for 2019.
 */
public class Walker {
    private int progress = 0;

    private WalkerLegs m_legs;

    public void WalkerInit() {
        m_legs = new WalkerLegs();
        m_legs.WalkerLegsInit();
    }

    public void RaiseRobot() {
        m_legs.ExtendFront(true);
        m_legs.ExtendBack(true);
        progress = 1;
        System.out.println("Raise Bot");
    }

    public void Walk() {
        m_legs.Walk(true);
        progress = 2;
        System.out.println("Walk");
    }

    public void RetractFrontLegs() {
        m_legs.ExtendFront(false);
        System.out.println("Retract Front Legs");
        progress = 3;
    }

    public void RetractBackLegs() {
        m_legs.ExtendBack(false);
        progress = 0;
        System.out.println("Retract Back Legs");
    }

    public void Climb() {
        if (progress == 0) {
            RaiseRobot();
        } else if (progress == 1) {
            Walk();
        } else if (progress == 2) {
            RetractFrontLegs();
        } else if (progress == 3) {
            RetractBackLegs();
        }
    }
}
