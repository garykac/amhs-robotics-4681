package frc.robot;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Lifter robot code for 2019.
 */

public class Lifter {
    private static final int kPWMLifter = 6;
    private static final int kDIOlimitSwitchBottom = 0;
    private static final int kDIOlimitSwitchTop = 1;
    private static final int kDIOlimitSwitchMiddle = 2;

    private PWMVictorSPX m_lifter;

    private String location;

    private DigitalInput m_DIOlimitSwitchBottom;
    private DigitalInput m_DIOlimitSwitchTop;
    private DigitalInput m_DIOlimitSwitchMiddle;

     public void LiftInit(){
        m_lifter = new PWMVictorSPX(kPWMLifter);
        //change from true or false depending on which works.
        m_lifter.setInverted(true);

        m_DIOlimitSwitchBottom = new DigitalInput(kDIOlimitSwitchBottom);
        m_DIOlimitSwitchTop = new DigitalInput(kDIOlimitSwitchTop);
        m_DIOlimitSwitchMiddle = new DigitalInput(kDIOlimitSwitchMiddle);
  }
    public void Lift(){
        if(m_DIOlimitSwitchBottom.get()){
            location="B";
            System.out.println(location);
        }
        if(m_DIOlimitSwitchTop.get()){
            m_lifter.set(0);
            location = "T";
            System.out.println(location);
        }
        if(m_DIOlimitSwitchMiddle.get()){
            //not sure yet
            location = "M";
            System.out.println(location);
        }
  }
}

