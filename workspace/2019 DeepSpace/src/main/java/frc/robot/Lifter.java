package frc.robot;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Lifter robot code for 2019.
 */

public class Lifter {
    
    private PWMVictorSPX m_lifter;

    private static final double kMotorPowerLevel = 1;

    private LifterHeight m_height;
    
    private DigitalInput m_DIOLifterSwitch;

    public void lifterInit() {
        m_lifter = new PWMVictorSPX(Constants.kPWMLifter);
        //change from true or false depending on which works.
        m_lifter.setInverted(true);

        m_height = new LifterHeight();
        m_height.lifterHeightInit();

        m_DIOLifterSwitch = new DigitalInput(Constants.kDIOLifterSwitch);
    }
    public void Lift() {
        m_lifter.set(kMotorPowerLevel);
    }

    public double getDistance(){
        return m_height.getDistance();
    }

    public void Lower() {
        if (!m_DIOLifterSwitch.get()) { //MAY NEED TO FLIP OPPOSITE OF SWITCH
            m_lifter.set(-kMotorPowerLevel);
            System.out.println("Lowering");
        } else {
            m_lifter.set(0); 
            System.out.println("AT BOTTOM");
        }
    }
    
    public void Stop() {
        m_lifter.set(0);
    }

    public void GoToBottom() {
        if (m_height.atBottom() || m_DIOLifterSwitch.get()) {
            m_lifter.set(0);
        } else {
            m_lifter.set(-kMotorPowerLevel);
        }
    }

    public void GoToTop() {
        if (m_height.atTop()) {
            m_lifter.set(0);
        } else {
            m_lifter.set(kMotorPowerLevel);
        }
    }

    public void GoToFirstBallLevel() {
        if (m_height.atFirstBallLevel()) {
            m_lifter.set(0);
        } else if (m_height.getDistance() < m_height.ballFirstLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.ballFirstLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToSecondBallLevel() {
        if (m_height.atSecondBallLevel()) {
            m_lifter.set(0);
        } else if (m_height.getDistance() < m_height.ballSecondLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.ballSecondLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToBallLoadingStation() {
        if (m_height.atBallLoadingStationLevel()) {
            m_lifter.set(0);
        } else if (m_height.getDistance() < m_height.ballLoadingStationHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.ballLoadingStationHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }

    public void GoToFirstHatchLevel() {
        if (m_height.atFirstHatchLevel()) {
            m_lifter.set(0);
        } else if (m_height.getDistance() < m_height.hatchFirstLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.hatchFirstLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToSecondHatchLevel() {
        if (m_height.atSecondHatchLevel()) {
            m_lifter.set(0);
        } else if (m_height.getDistance() < m_height.hatchSecondLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.hatchSecondLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToThirdHatchLevel() {
        if (m_height.atThirdHatchLevel()) {
            m_lifter.set(0);
        } else if (m_height.getDistance() < m_height.hatchThirdLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.hatchThirdLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
}
