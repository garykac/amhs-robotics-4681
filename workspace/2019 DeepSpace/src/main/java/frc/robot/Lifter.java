package frc.robot;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Lifter robot code for 2019.
 */

public class Lifter {
    private static final int kPWMLifter = 6;

    private static final int kDIOlimitSwitchBottom = 0;
    private static final int kDIOlimitSwitchTop = 1;

    private static final int kDIOultrasonicEcho = 4;
    private static final int kDIOultrasonicTrig = 5;

    private static final int kbottomRangeInches = 40;
    private static final int ktopRangeInches = 50;

    private PWMVictorSPX m_lifter;

    private static final double kMotorPowerLevel = 0.4;

    private String location;

    private Ultrasonic m_middleUltrasonic;
    private DigitalInput m_DIOlimitSwitchBottom;
    private DigitalInput m_DIOlimitSwitchTop;

     public void LiftInit(){
        m_lifter = new PWMVictorSPX(kPWMLifter);
        //change from true or false depending on which works.
        m_lifter.setInverted(true);

        m_middleUltrasonic = new Ultrasonic(kDIOultrasonicTrig, kDIOultrasonicEcho);
        m_middleUltrasonic.setAutomaticMode(true);

        m_DIOlimitSwitchBottom = new DigitalInput(kDIOlimitSwitchBottom);
        m_DIOlimitSwitchTop = new DigitalInput(kDIOlimitSwitchTop); 
     }
    public void Lift(){
        if(m_DIOlimitSwitchTop.get()){
            m_lifter.set(0);
            location = "T";
            System.out.println(location);
        }

        else if(m_DIOlimitSwitchBottom.get()){
            m_lifter.set(kMotorPowerLevel);
            location="B";
            System.out.println(location);
        }
        
        else if(m_middleUltrasonic.getRangeInches()>kbottomRangeInches && m_middleUltrasonic.getRangeInches()<ktopRangeInches){
            //not sure on the range yet.
            location = "M";
            System.out.println(m_middleUltrasonic.getRangeInches());
        }
        if(location== "B" || location == "M"){
            m_lifter.set(kMotorPowerLevel);
        }
  }
  public void Lower(){
    if(m_DIOlimitSwitchTop.get()){
        m_lifter.set(0);
        location = "T";
        System.out.println(location);
    }

    else if(m_DIOlimitSwitchBottom.get()){
        m_lifter.set(0);
        location="B";
        System.out.println(location);
    }
    
    else if(m_middleUltrasonic.getRangeInches()>kbottomRangeInches && m_middleUltrasonic.getRangeInches()<ktopRangeInches){
        //not sure on the range yet.
        location = "M";
        System.out.println(m_middleUltrasonic.getRangeInches());
    }
    if(location == "T" || location == "M"){
        m_lifter.set(-kMotorPowerLevel);
    }
  }
  public void GoToBottom(){
      if(m_DIOlimitSwitchBottom.get()){
          m_lifter.set(0);
      }
      else{
          m_lifter.set(-kMotorPowerLevel);
      }
  }
  public void GoToTop(){
      if(m_DIOlimitSwitchTop.get()){
          m_lifter.set(0);
      }
      else{
          m_lifter.set(kMotorPowerLevel);
      }
  }
  public void GoToMiddle(){
      if(m_middleUltrasonic.getRangeInches()>kbottomRangeInches && m_middleUltrasonic.getRangeInches()<ktopRangeInches){
          m_lifter.set(0);
     }
     else if(m_middleUltrasonic.getRangeInches()>kbottomRangeInches){
         m_lifter.set(kMotorPowerLevel);
     }
     else if(m_middleUltrasonic.getRangeInches()<kbottomRangeInches){
         m_lifter.set(-kMotorPowerLevel);
     }
  
}
}
