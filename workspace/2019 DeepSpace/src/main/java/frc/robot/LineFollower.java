package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;
/**
 * Line follower robot code for 2019.
 */
public class LineFollower {

    private static final int klightSensor = 0;

    private AnalogInput m_lightSensor;

    public void LineFollowerInit(){
        m_lightSensor = new AnalogInput(klightSensor);
    }

    public void OnLine(){
        System.out.println(m_lightSensor.getValue());
    }
}
