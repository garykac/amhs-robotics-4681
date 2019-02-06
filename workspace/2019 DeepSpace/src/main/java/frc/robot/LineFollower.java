package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/**
 * Line follower robot code for 2019.
 */
public class LineFollower {


    private AnalogInput m_lightSensor;

    public void LineFollowerInit() {
        m_lightSensor = new AnalogInput(Constants.kAnalogLightSensor);
    }

    public void OnLine() {
        System.out.println(m_lightSensor.getValue());
    }
}
