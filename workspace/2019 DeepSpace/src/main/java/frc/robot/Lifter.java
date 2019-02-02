package frc.robot;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Lifter robot code for 2019.
 */

public class Lifter {

    private static final int kRangeBottomInches = 40;
    private static final int kRangeTopInches = 50;

    private PWMVictorSPX m_lifter;

    private static final double kMotorPowerLevel = 0.4;

    private String location;

    private Ultrasonic m_middleUltrasonic;
    private DigitalInput m_DIOlimitSwitchBottom;
    private DigitalInput m_DIOlimitSwitchTop;

    public void lifterInit() {
        m_lifter = new PWMVictorSPX(Constants.kPWMLifter);
        //change from true or false depending on which works.
        m_lifter.setInverted(true);

        //m_middleUltrasonic = new Ultrasonic(Constants.kDIOultrasonicTrig, 
        //                                    Constants.kDIOultrasonicEcho);
        //m_middleUltrasonic.setAutomaticMode(true);

        m_DIOlimitSwitchBottom = new DigitalInput(Constants.kDIOLifterLimitSwitchBottom);
        m_DIOlimitSwitchTop = new DigitalInput(Constants.kDIOLifterLimitSwitchTop); 
    }

    public void Lift() {
        if (m_DIOlimitSwitchTop.get()) {
            m_lifter.set(0);
            location = "T";
            System.out.println(location);
        } else if (m_DIOlimitSwitchBottom.get()) {
            m_lifter.set(kMotorPowerLevel);
            location="B";
            System.out.println(location);
        } else if (m_middleUltrasonic.getRangeInches() > kRangeBottomInches
                && m_middleUltrasonic.getRangeInches() < kRangeTopInches) {
            //not sure on the range yet.
            location = "M";
            System.out.println(m_middleUltrasonic.getRangeInches());
        }
        if (location== "B" || location == "M") {
            m_lifter.set(kMotorPowerLevel);
        }
    }

    public void Lower() {
        if (m_DIOlimitSwitchTop.get()) {
            m_lifter.set(0);
            location = "T";
            System.out.println(location);
        } else if (m_DIOlimitSwitchBottom.get()) {
            m_lifter.set(0);
            location = "B";
            System.out.println(location);
        } else if (m_middleUltrasonic.getRangeInches() > kRangeBottomInches
                    && m_middleUltrasonic.getRangeInches() < kRangeTopInches) {
            //not sure on the range yet.
            location = "M";
            System.out.println(m_middleUltrasonic.getRangeInches());
        }

        if (location == "T" || location == "M"){
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToBottom() {
        if (m_DIOlimitSwitchBottom.get()) {
            m_lifter.set(0);
        } else {
            m_lifter.set(-kMotorPowerLevel);
        }
        location = "B";
    }

    public void GoToTop() {
        if (m_DIOlimitSwitchTop.get()) {
            m_lifter.set(0);
        } else {
            m_lifter.set(kMotorPowerLevel);
        }
        location = "T";
    }

    public void GoToMiddle() {
        if (m_middleUltrasonic.getRangeInches() > kRangeBottomInches
                && m_middleUltrasonic.getRangeInches() < kRangeTopInches) {
            m_lifter.set(0);
        } else if (m_middleUltrasonic.getRangeInches() > kRangeBottomInches){
            m_lifter.set(kMotorPowerLevel);
        } else if (m_middleUltrasonic.getRangeInches() < kRangeTopInches){
            m_lifter.set(-kMotorPowerLevel);
        }
        location = "M";
    }

    public String GetLocation() {
        return location;
    }
}
