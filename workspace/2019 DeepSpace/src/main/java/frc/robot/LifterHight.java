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

    private static final double ballFirstLevelHight = 50;
    private static final double ballSecondLevelHight = 100;
    private static final double ballThirdLevelHight = 150;

    private static final double hatchFirstLevelHight = 60;
    private static final double hatchSecondLevelHight = 90;
    private static final double hatchThirdLevelHight = 120;


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

    public double minOffSet(double hight){
        return (hight-minMaxDef);
    }

    public double maxOffSet(double hight){
        return (hight+minMaxDef);
    }
    public boolean atBottom(){
        if (getDistance()>=minHight){
            return true;
        } return false;}

    public boolean atTop(){
        if(getDistance()>=maxHight){
            return true;
        } return false; }
    public boolean atFirstHatchLevel(){
        if(minOffSet(getDistance()) < hatchFirstLevelHight &&
           maxOffSet(getDistance()) < hatchFirstLevelHight){
               return true;
           }return false; }
    public boolean atSecondHatchLevel(){
        if(minOffSet(getDistance()) < hatchSecondLevelHight &&
           maxOffSet(getDistance()) < hatchSecondLevelHight){
               return true;
           }return false; }
    public boolean atThirdHatchLevel(){
        if(minOffSet(getDistance()) < hatchThirdLevelHight &&
           maxOffSet(getDistance()) < hatchThirdLevelHight){
               return true;
           }return false; }
    public boolean aFirstBallLevel(){
        if(minOffSet(getDistance()) < ballFirstLevelHight &&
           maxOffSet(getDistance()) < ballFirstLevelHight){
               return true;
           }return false; }
    public boolean aSecondBallLevel(){
        if(minOffSet(getDistance()) < ballSecondLevelHight &&
           maxOffSet(getDistance()) < ballSecondLevelHight){
               return true;
           }return false; }
    public boolean aThirdBallLevel(){
        if(minOffSet(getDistance()) < ballThirdLevelHight &&
           maxOffSet(getDistance()) < ballThirdLevelHight){
               return true;
           }return false; }
}