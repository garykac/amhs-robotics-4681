package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/**
 * Line follower robot code for 2019.
 */
public class LineFollower {

    private AnalogInput m_infraRed;

    private AnalogInput m_lightSensor;

    public void LineFollowerInit() {
        m_lightSensor = new AnalogInput(Constants.kAnalogLightSensor);
        m_infraRed = new AnalogInput(Constants.kAnalogInfraRed);


    }

    public void OnLine() {
        System.out.println(m_infraRed.getValue());
    }
}
