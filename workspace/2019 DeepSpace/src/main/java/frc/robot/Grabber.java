/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;

/**
 * Main robot code for 2019.
 */
public class Grabber {

    private static final double kMotorPowerLevel = 0.4;
    private static final double kMotorPowerLevelOut = -1;

    private DigitalInput m_DIOlimitSwitchGrabber;
    //private PWMVictorSPX m_grabberRight;
    private PWMVictorSPX m_grabber;

    public void GrabberInit() {
        m_grabber = new PWMVictorSPX(Constants.kPWMGrabber);
        m_DIOlimitSwitchGrabber = new DigitalInput(Constants.kDIOBallSwitch);
    }

    public void Grab() {
        if (m_DIOlimitSwitchGrabber.get()) {
            m_grabber.set(0);
        } else {
            m_grabber.set(kMotorPowerLevel);
        }
    }
    public boolean returnSwitch(){
        return m_DIOlimitSwitchGrabber.get();
    }
    public void Eject() {
        m_grabber.set(kMotorPowerLevelOut);
    } 
    public void motorStop(){
        m_grabber.set(0);
    }  
  }

