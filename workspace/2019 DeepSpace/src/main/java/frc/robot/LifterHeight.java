package frc.robot;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Counter;

/**
 * Lifter robot code for 2019.
 */

public class LifterHeight {
    
    private Counter counter;

    private String location;

    private static final double offSet = .52;

    private static final double minMaxDef = 5;

    private static final double maxHeight = 196;
    private static final double minHeight = 4;

    public static final double ballFirstLevelHeight = 67.945;
    public static final double ballSecondLevelHeight = 100;
    public static final double ballThirdLevelHeight = 150;

    public static final double hatchFirstLevelHeight = 47;
    public static final double hatchSecondLevelHeight = 90;
    public static final double hatchThirdLevelHeight = 120;


    public void lifterHeightInit() {
        counter = new Counter(Constants.kPWMLiftersensor);
        counter.setMaxPeriod(1.0);
        counter.setSemiPeriodMode(true);
        counter.reset();
    }
    public double getDistance(){
        //if (counter.get() < 1) {
            //return 0.0;
        //}
        return (counter.getPeriod());// * 37724) + offSet;
        // This is the conversion to get to inches.
    }
    public void distanceReturn(){
        System.out.println("" + counter.get()  + " " + counter.getPeriod());
    }
    public double minOffSet(double height) {
        return (height - minMaxDef);
    }

    public double maxOffSet(double height) {
        return (height + minMaxDef);
    }
    
    public boolean atBottom(){
        if (getDistance() <= minHeight) {
            return true;
        }
        return false;
    }

    public boolean atTop(){
        if(getDistance()>=maxHeight){
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
        return atLevel_(hatchFirstLevelHeight);
    }
    
    public boolean atSecondHatchLevel() {
        return atLevel_(hatchSecondLevelHeight);
    }
    
    public boolean atThirdHatchLevel() {
        return atLevel_(hatchThirdLevelHeight);
    }
    
    public boolean atFirstBallLevel() {
        return atLevel_(ballFirstLevelHeight);
    }
    
    public boolean atSecondBallLevel() {
        return atLevel_(ballSecondLevelHeight);
    }
    
    public boolean atThirdBallLevel() {
        return atLevel_(ballThirdLevelHeight);
    }
}
