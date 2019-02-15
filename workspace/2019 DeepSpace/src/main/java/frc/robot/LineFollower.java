package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/**
 * Line follower robot code for 2019.
 */
public class LineFollower {

    private AnalogInput m_infraRed1, m_infraRed2, m_infraRed3;

    private AnalogInput m_lightSensor;

    public void LineFollowerInit() {
        m_lightSensor = new AnalogInput(Constants.kAnalogLightSensor);
        m_infraRed1 = new AnalogInput(Constants.kAnalogInfraRed1);
        m_infraRed2 = new AnalogInput(Constants.kAnalogInfraRed2);
        m_infraRed3 = new AnalogInput(Constants.kAnalogInfraRed3);
        

    }

    public void OnLine() {
        System.out.println(" " + m_infraRed1.getValue() + ", " + m_infraRed2.getValue()+", " + m_infraRed3.getValue());
    }
}
