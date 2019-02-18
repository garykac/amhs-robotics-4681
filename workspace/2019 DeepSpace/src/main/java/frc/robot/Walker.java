
package frc.robot;


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
        System.out.println("Raise Bot");
    }

    public void Walk() {
        m_legs.Walk(true);
        System.out.println("Walk");
    }
    
    public void StraightenLegs() {
        m_legs.Walk(false);
        System.out.println("Un-Walk");

    public void RetractFrontLegs() {
        m_legs.ExtendFront(false);
        System.out.println("Retract Front Legs");
    }

    public void RetractBackLegs() {
        m_legs.ExtendBack(false);
        System.out.println("Retract Back Legs");
    }
}
