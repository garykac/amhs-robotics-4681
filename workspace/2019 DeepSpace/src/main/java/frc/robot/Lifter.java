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
    
    private enum Location {
        bottom, top, ballFirstLevel, ballSecondLevel, ballThirdLevel, hatchFirstLevel, hatchSecondLevel, hatchThirdLevel;
    }  // I don't see the enums being used much anywhere
    Location m_loc;

    public void lifterInit() {
        m_lifter = new PWMVictorSPX(Constants.kPWMLifter);
        //change from true or false depending on which works.
        m_lifter.setInverted(true);

        m_height = new LifterHeight();
        m_height.lifterHeightInit();

        m_loc = Location.bottom;
        m_DIOLifterSwitch = new DigitalInput(Constants.kDIOLifterSwitch);
    }
    public void Lift() {
        m_lifter.set(kMotorPowerLevel);
    }

    public void getTotalDistance(){
        System.out.println(m_height.getDistance());
    }

    public double getNewDistance(){
        return m_height.getDistance();
    }

    public void Lower() {
        /*if (!m_DIOLifterSwitch.get()){//MAY NEED TO FLIP OPPOSITE OF SWITCH
            m_lifter.set(-kMotorPowerLevel);
            System.out.println("Lowering");
        }
        else{
            System.out.println("AT BOTTOM");
        }*/
        m_lifter.set(-kMotorPowerLevel);

        
    }
    
    public void Stop() {
        m_lifter.set(0);
    }
    
    public void GoToBottom() {
        if (m_height.atBottom()) {
            m_lifter.set(0);
            m_loc = Location.bottom;
        } else {
            m_lifter.set(-kMotorPowerLevel);
        }
    }

    public void printRawValues() {
        m_height.printRawValues();
    }
    
    public void GoToTop() {
        if (m_height.atTop()) {
            m_lifter.set(0);
            m_loc = Location.top;
        } else {
            m_lifter.set(kMotorPowerLevel);
        }
    }

    // Wouldn't it be more worthwile to define a function:
    // public void goTo(int hight), and pass in the variables for game piece heights?
    public void GoToFirstBallLevel() {
        if (m_height.atFirstBallLevel()) {
            m_lifter.set(0);
            m_loc = Location.ballFirstLevel;
        } else if (m_height.getDistance() < m_height.ballFirstLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.ballFirstLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToSecondBallLevel() {
        if (m_height.atSecondBallLevel()) {
            m_lifter.set(0);
            m_loc = Location.ballSecondLevel;
        } else if (m_height.getDistance() < m_height.ballSecondLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.ballSecondLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToThirdBallLevel() {
        if (m_height.atThirdBallLevel()) {
            m_lifter.set(0);
            m_loc = Location.ballThirdLevel;
        } else if (m_height.getDistance() < m_height.ballThirdLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.ballThirdLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }

    public void GoToFirstHatchLevel() {
        if (m_height.atFirstHatchLevel()) {
            m_lifter.set(0);
            m_loc = Location.hatchFirstLevel;
        } else if (m_height.getDistance() < m_height.hatchFirstLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.hatchFirstLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToSecondHatchLevel() {
        if (m_height.atSecondHatchLevel()) {
            m_lifter.set(0);
            m_loc = Location.hatchSecondLevel;
        } else if (m_height.getDistance() < m_height.hatchSecondLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.hatchSecondLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToThirdHatchLevel() {
        if (m_height.atThirdHatchLevel()) {
            m_lifter.set(0);
            m_loc = Location.hatchThirdLevel;
        } else if (m_height.getDistance() < m_height.hatchThirdLevelHeight) {
            m_lifter.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.hatchThirdLevelHeight) {
            m_lifter.set(-kMotorPowerLevel);
        }
    }
}
