package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/**
 * Line follower robot code for 2019.
 */
public class LineFollower {

    private AnalogInput m_infraredFL, m_infraredCL, m_infraredCR, m_infraredFR;

    private AnalogInput m_lightSensor;
    private boolean ir0_sensor = false;
    private boolean ir1_sensor = false;
    private boolean ir2_sensor = false;
    private boolean ir3_sensor = false;
    private String [] sensors = new String [4];

    public void LineFollowerInit() {
        m_infraredFL = new AnalogInput(Constants.kAnalogInfraredFarLeft);
        m_infraredCL = new AnalogInput(Constants.kAnalogInfraredCenterLeft);
        m_infraredCR = new AnalogInput(Constants.kAnalogInfraredCenterRight);
        m_infraRedFR = new AnalogInput(Constants.kAnalogInfraredFarRight);
    }
    public void detectReflectivity() {
        ir0_sensor = false;
        ir1_sensor = false;
        ir2_sensor = false;
        ir3_sensor = false;
        if (m_infraredFL.getValue() < 800) {
            ir0_sensor = true;
        }
        if (m_infraredCL.getValue() < 800) {
            ir1_sensor = true;
        }
        if (m_infraredCR.getValue() < 800) {
            ir2_sensor = true;
        }
        if (m_infraredFR.getValue() < 800) {
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
