package frc.robot;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Counter;

/**
 * Lifter robot code for 2019.
 */

public class LifterHight {
    
    private Counter counter;

    private String location;

    private static final double offSet = 0;

    private static final double minMaxDef = 5;

    private static final double maxHight = 196;
    private static final double minHight = 4;

    public static final double ballFirstLevelHight = 67.945;
    public static final double ballSecondLevelHight = 100;
    public static final double ballThirdLevelHight = 150;

    public static final double hatchFirstLevelHight = 47;
    public static final double hatchSecondLevelHight = 90;
    public static final double hatchThirdLevelHight = 120;


    public void lifterHightInit() {
        counter = new Counter(Constants.kDIOLifterHightSensor);
        counter.setMaxPeriod(1.0);
        counter.setSemiPeriodMode(true);
        counter.reset();
        
    }
    
    public double getDistance(){
        if (counter.get() < 1) {
            return 0.0;
        }
        return (counter.getPeriod() * 1000000.0 / 10.0) + offSet;
    }

    public double minOffSet(double hight) {
        return (hight - minMaxDef);
    }

    public double maxOffSet(double hight) {
        return (hight + minMaxDef);
    }
    
    public boolean atBottom(){
        if (getDistance() <= minHight) {
            return true;
        }
        return false;
    }

    public boolean atTop(){
        if(getDistance()>=maxHight){
            return true;
        }
        return false;
    }
    
    private boolean atLevel_(double height) {
        if (minOffSet(height) < getDistance()
                && maxOffSet(height) > getDistance()) {
            return true;
        }
        return false;
    }
    
    public boolean atFirstHatchLevel() {
        return atLevel_(hatchFirstLevelHight);
    }
    
    public boolean atSecondHatchLevel() {
        return atLevel_(hatchSecondLevelHight);
    }
    
    public boolean atThirdHatchLevel() {
        return atLevel_(hatchThirdLevelHight);
    }
    
    public boolean atFirstBallLevel() {
        return atLevel_(ballFirstLevelHight);
    }
    
    public boolean atSecondBallLevel() {
        return atLevel_(ballSecondLevelHight);
    }
    
    public boolean atThirdBallLevel() {
        return atLevel_(ballThirdLevelHight);
    }
}
