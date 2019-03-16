package frc.robot;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Lifter robot code for 2019.
 */

public class Lifter {
    
    //private PWMVictorSPX m_winch;
    private PWMTalonSRX m_winch;

    private static final double kMotorPowerLevel = 1;

    private LifterHeight m_height;
    
    private DigitalInput m_DIOLifterSwitch;

    public void lifterInit() {
        //m_winch = new PWMVictorSPX(Constants.kPWMLifter);
        m_winch = new PWMTalonSRX(Constants.kPWMLifter);
        //change from true or false depending on which works.
        m_winch.setInverted(true);

        m_height = new LifterHeight();
        m_height.lifterHeightInit();

        m_DIOLifterSwitch = new DigitalInput(Constants.kDIOLifterSwitch);
        
    }
    public void Lift() {
        m_winch.set(kMotorPowerLevel);
    }

    public double getDistance(){
        return m_height.getDistance();
    }

    public void Lower() {
        if (!m_DIOLifterSwitch.get()) { //MAY NEED TO FLIP OPPOSITE OF SWITCH
            m_winch.set(-kMotorPowerLevel);
            System.out.println("Lowering");
        } else {
            m_winch.set(0); 
            System.out.println("AT BOTTOM");
        }
    }
    
    public void Stop() {
        m_winch.set(0);
    }
    boolean reached = false;
    int offset = 5;
    public void goToHeight(int targetHeight) {
        if (m_height.getDistance() < (targetHeight + offset) && !reached) {
            m_winch.set(kMotorPowerLevel);//Copying of Lift() function, code errors if calling .Lower() should be fixed at later date
        }
        else {
            reached = true;
        }
        if (m_height.getDistance() < (targetHeight - offset) && reached) {
            reached = false;
        }
        if (m_height.getDistance() > (targetHeight + offset)) {//Copying of Lower() function, code errors if calling .Lower() should be fixed at later date
            if (!m_DIOLifterSwitch.get()) { //MAY NEED TO FLIP OPPOSITE OF SWITCH
                m_winch.set(-kMotorPowerLevel);
                System.out.println("Lowering");
            } else {
                m_winch.set(0); 
                System.out.println("AT BOTTOM");
            }
        }
    }






    public void GoToBottom() {
        if (m_height.atBottom() || m_DIOLifterSwitch.get()) {
            m_winch.set(0);
        } else {
            m_winch.set(-kMotorPowerLevel);
        }
    }

    public void GoToTop() {
        if (m_height.atTop()) {
            m_winch.set(0);
        } else {
            m_winch.set(kMotorPowerLevel);
        }
    }

    public void GoToFirstBallLevel() {
        if (m_height.atFirstBallLevel()) {
            m_winch.set(0);
        } else if (m_height.getDistance() < m_height.ballFirstLevelHeight) {
            m_winch.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.ballFirstLevelHeight) {
            m_winch.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToSecondBallLevel() {
        if (m_height.atSecondBallLevel()) {
            m_winch.set(0);
        } else if (m_height.getDistance() < m_height.ballSecondLevelHeight) {
            m_winch.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.ballSecondLevelHeight) {
            m_winch.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToBallLoadingStation() {
        if (m_height.atBallLoadingStationLevel()) {
            m_winch.set(0);
        } else if (m_height.getDistance() < m_height.ballLoadingStationHeight) {
            m_winch.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.ballLoadingStationHeight) {
            m_winch.set(-kMotorPowerLevel);
        }
    }

    public void GoToFirstHatchLevel() {
        if (m_height.atFirstHatchLevel()) {
            m_winch.set(0);
        } else if (m_height.getDistance() < m_height.hatchFirstLevelHeight) {
            m_winch.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.hatchFirstLevelHeight) {
            m_winch.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToSecondHatchLevel() {
        if (m_height.atSecondHatchLevel()) {
            m_winch.set(0);
        } else if (m_height.getDistance() < m_height.hatchSecondLevelHeight) {
            m_winch.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.hatchSecondLevelHeight) {
            m_winch.set(-kMotorPowerLevel);
        }
    }
    
    public void GoToThirdHatchLevel() {
        if (m_height.atThirdHatchLevel()) {
            m_winch.set(0);
        } else if (m_height.getDistance() < m_height.hatchThirdLevelHeight) {
            m_winch.set(kMotorPowerLevel);
        } else if (m_height.getDistance() > m_height.hatchThirdLevelHeight) {
            m_winch.set(-kMotorPowerLevel);
        }
    }
}
