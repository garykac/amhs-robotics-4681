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
    
    public boolean atFirstHatchLevel() {
        if(minOffSet(hatchFirstLevelHight) < getDistance() &&
           maxOffSet(hatchFirstLevelHight) > getDistance()) {
            return true;
        }
        return false;
    }
    
    public boolean atSecondHatchLevel() {
        if(minOffSet(hatchSecondLevelHight) < getDistance() &&
           maxOffSet(hatchSecondLevelHight) > getDistance()) {
               return true;
        }
        return false;
    }
    
    public boolean atThirdHatchLevel() {
        if(minOffSet(hatchThirdLevelHight) < getDistance() &&
           maxOffSet(hatchThirdLevelHight) > getDistance()) {
               return true;
        }
        return false;
    }
    
    public boolean atFirstBallLevel() {
        if(minOffSet(ballFirstLevelHight) < getDistance() &&
           maxOffSet(ballFirstLevelHight) > getDistance()) {
               return true;
        }
        return false;
    }
    
    public boolean atSecondBallLevel() {
        if(minOffSet(ballSecondLevelHight) < getDistance() &&
           maxOffSet(ballSecondLevelHight) > getDistance()) {
            return true;
        }
        return false;
    }
    
    public boolean atThirdBallLevel() {
        if(minOffSet(ballThirdLevelHight) < getDistance() &&
           maxOffSet(ballThirdLevelHight) > getDistance()) {
               return true;
        }
        return false;
    }
}
