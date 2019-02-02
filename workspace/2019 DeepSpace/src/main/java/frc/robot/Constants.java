package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class Constants {

    public static final int kDIOlimitSwitchBottom = 0;
    public static final int kDIOlimitSwitchTop = 1;
    public static final int kDIOultrasonicEcho = 4;
    public static final int kDIOultrasonicTrig = 5;
    public static final int kDIOLimitSwitchGrabber = 3;

    public static final int kPWMGrabberRight = 4;
    public static final int kPWMGrabberLeft = 5;
    public static final int kPWMLifter = 6;
    public static final int kPWMFrontLeft = 0;
    public static final int kPWMRearLeft = 2;
    public static final int kPWMFrontRight = 1;
    public static final int kPWMRearRight = 3;

    public static final int kPCMHatchSucker = 0;
    public static final int kPCMHatchSuckerExtender = 1;
    public static final int kPCMHatchVacuum = 0;
    public static final int kPCMHatchCylinder = 1;
    public static final int kPCMLegsFront = 2;
    public static final int kPCMLegsBack = 3;
    public static final int kPCMLegsWalk = 4;

    public static final int kAnaloglightSensor = 0;

    

    public void ConstantsInit() {
        
    }
}