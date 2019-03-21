package frc.robot;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Lifter robot code for 2019.
 */

public class Lifter {
    
    private PWMTalonSRX m_winch;

    private static final double kMotorPowerLevel = 1;
    boolean reached = false;
    private int offset = 5;
    private int lifterSpeed = 0;
    private int kSpeedSteps = 50; // arbitrary number. if too slow, decrease

    private LifterHeight m_height;
    
    private DigitalInput m_DIOLifterSwitch;

    public void lifterInit() {
        m_winch = new PWMTalonSRX(Constants.kPWMLifter);
        m_winch.setInverted(true);

        m_height = new LifterHeight();
        m_height.lifterHeightInit();

        m_DIOLifterSwitch = new DigitalInput(Constants.kDIOLifterSwitch);  
    }
    
    public double getDistance(){
        return m_height.getDistance();
    }
    
    public void Lift() {
        if (lifterSpeed < kSpeedSteps)  
            lifterSpeed++;
        m_winch.set(lifterSpeed/kSpeedSteps);
    }

    public void Lower() {
        if (!m_DIOLifterSwitch.get()) {
            if (lifterSpeed > -kSpeedSteps)
                lifterSpeed--;
        } else {
            lifterSpeed = 0;
        }
        m_winch.set(lifterSpeed/kSpeedSteps);
    }
    
    public void Stop() {
        lifterSpeed = 0;
        m_winch.set(0);
    }
    
    public void gotoID(int ID) {
        switch (ID) {
            case -1:
            case 0:
                goToHeight(0);
                break;
            case 1:
                goToHeight(m_height.hatchFirstLevelHeight);
                break;
            case 2:
                goToHeight(m_height.ballFirstLevelHeight);
                break;
            case 3:
                goToHeight(m_height.hatchSecondLevelHeight);
                break;
            case 4:
                goToHeight(m_height.ballLoadingStationHeight);
                break;
            case 5:
                goToHeight(m_height.hatchThirdLevelHeight);
                break;
            case 6:
                goToHeight(m_height.ballSecondLevelHeight);
                break;
        }
    }
    
    public void goToHeight(double targetHeight) {
        if (getDistance() < (targetHeight + offset) && !reached) {
            Lift();
        } else {
            reached = true;
            Stop();
        }
        if (getDistance() < (targetHeight - offset) && reached) {
            reached = false;
        }
        if (getDistance() > (targetHeight + offset)) {
            Lower();
        } else {
            reached = true;
            Stop();
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
}
