package frc.robot;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Lifter robot code for 2019.
 */

public class Lifter {
    
    private PWMVictorSPX m_lifter;

    private static final double kMotorPowerLevel = 0.4;

    private LifterHight m_hight;

    private enum Location {
        bottom, top, ballFirstLevel, ballSecondLevel, ballThirdLevel, hatchFirstLevel, hatchSecondLevel, hatchThirdLevel;
    }  // I don't see the enums being used much anywhere
    Location loc;
    public void lifterInit() {
        m_lifter = new PWMVictorSPX(Constants.kPWMLifter);
        //change from true or false depending on which works.
        m_lifter.setInverted(true);

        m_hight = new LifterHight();
        m_hight.lifterHightInit();

    }
    public void Lift() {
        m_lifter.set(kMotorPowerLevel);
    }

    public void Lower() {
        m_lifter.set(-kMotorPowerLevel);
    }
    
    public void Stop() {
        m_lifter.set(0);
    }
    
    public void GoToBottom() {
        if (m_hight.atBottom()) {
            m_lifter.set(0);
            loc = Location.bottom;
        } else {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToTop() {
        if (m_hight.atTop()) {
            m_lifter.set(0);
            loc = Location.top;
        } else {
            m_lifter.set(kMotorPowerLevel);
        }
    }

    // Wouldn't it be more worthwile to define a function:
    // public void goTo(int hight), and pass in the variables for game piece heights?
    public void GoToFirstBallLevel() {
        if (m_hight.atFirstBallLevel()) {
            m_lifter.set(0);
            loc = Location.ballFirstLevel;
        } else if (m_hight.getDistance() < m_hight.ballFirstLevelHight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_hight.getDistance() > m_hight.ballFirstLevelHight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToSecondBallLevel() {
        if (m_hight.atSecondBallLevel()) {
            m_lifter.set(0);
            loc = Location.ballSecondLevel;
        } else if (m_hight.getDistance() < m_hight.ballSecondLevelHight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_hight.getDistance() > m_hight.ballSecondLevelHight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToThirdBallLevel() {
        if (m_hight.atThirdBallLevel()) {
            m_lifter.set(0);
            loc = Location.ballThirdLevel;
        } else if (m_hight.getDistance() < m_hight.ballThirdLevelHight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_hight.getDistance() > m_hight.ballThirdLevelHight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }

    public void GoToFirstHatchLevel() {
        if (m_hight.atFirstHatchLevel()) {
            m_lifter.set(0);
            loc = Location.hatchFirstLevel;
        } else if (m_hight.getDistance() < m_hight.hatchFirstLevelHight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_hight.getDistance() > m_hight.hatchFirstLevelHight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToSecondHatchLevel() {
        if (m_hight.atSecondHatchLevel()) {
            m_lifter.set(0);
            loc = Location.hatchSecondLevel;
        } else if (m_hight.getDistance() < m_hight.hatchSecondLevelHight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_hight.getDistance() > m_hight.hatchSecondLevelHight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToThirdHatchLevel() {
        if (m_hight.atThirdHatchLevel()) {
            m_lifter.set(0);
            loc = Location.hatchThirdLevel;
        } else if (m_hight.getDistance() < m_hight.hatchThirdLevelHight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_hight.getDistance() > m_hight.hatchThirdLevelHight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
}
