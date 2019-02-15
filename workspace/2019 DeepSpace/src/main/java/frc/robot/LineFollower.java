package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/**
 * Line follower robot code for 2019.
 */
public class LineFollower {

    private AnalogInput m_infraRed0, m_infraRed1, m_infraRed2, m_infraRed3;

    private AnalogInput m_lightSensor;
    private boolean ir0_sensor = false;
    private boolean ir1_sensor = false;
    private boolean ir2_sensor = false;
    private boolean ir3_sensor = false;
    private String [] sensors = new String [4];

    public void LineFollowerInit() {
        m_infraRed0 = new AnalogInput(Constants.kAnalogLightSensor);
        m_infraRed1 = new AnalogInput(Constants.kAnalogInfraRed1);
        m_infraRed2 = new AnalogInput(Constants.kAnalogInfraRed2);
        m_infraRed3 = new AnalogInput(Constants.kAnalogInfraRed3);
    }
    public void detectReflectivity() {
        ir0_sensor = false;
        ir1_sensor = false;
        ir2_sensor = false;
        ir3_sensor = false;
        if (m_infraRed0.getValue() < 800) {
            ir0_sensor = true;
        }
        if (m_infraRed1.getValue() < 800) {
            ir1_sensor = true;
        }
        if (m_infraRed2.getValue() < 800) {
            ir2_sensor = true;
        }
        if (m_infraRed3.getValue() < 800) {
            ir3_sensor = true;
        }
    }

    public String[] display() {
        sensors = new String[] {"_","_","_","_"};
        if (ir0_sensor)
            sensors[0] = "X";
        if (ir1_sensor)
            sensors[1] = "X";
        if (ir2_sensor)
            sensors[2] = "X";
        if (ir3_sensor)
            sensors[3] = "X";
        return sensors;
    }

    public void OnLine() {
        detectReflectivity();
        String [] sensors;
        sensors = display();
        System.out.println(sensors[0]
                        + sensors[1]
                        + sensors[2]
                        + sensors[3]);
        
    }
}
