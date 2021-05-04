package frc.robot;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Counter;

/**
 * Lifter robot code for 2019.
 */

public class LifterHeight {
    
    private Counter counter;

    //offset is in inches and added after the value is already converted to inches
    private static final double offSetIn = 0;//.52;

    private static final double minMaxDef = 3;

    private static final double maxHeight = 62.6;
    private static final double minHeight = 2;
    // ALL HEIGHTS NEED TO BE ([MEASURED DISTANCE] + 3)
    // TO COMPENSATE FOR (targetHeight - 3) IN goToHeight()
    public static final double ballFirstLevelHeight = 29;
    public static final double ballLoadingStationHeight = 42;
    public static final double ballSecondLevelHeight = 56;

    public static final double hatchFirstLevelHeight = 9;
    public static final double hatchSecondLevelHeight = 39;
    public static final double hatchThirdLevelHeight = 62;


    public void lifterHeightInit() {
        counter = new Counter(Constants.kDIOLifterHeightSensor);
        counter.setMaxPeriod(1.0);
        counter.setSemiPeriodMode(true);
        counter.reset();
    }

    public double getDistance() {
        if (counter.get() < 1) {
            return 0.0;
        }
        // getPeriod returns a value of 10 usec / cm.
        double rawPeriod = counter.getPeriod();
        double distanceCm = rawPeriod * 100000;
        double distanceIn = distanceCm / 2.54;
        //System.out.println("LIDAR: " + distanceCm + " , " + distanceIn);
        return distanceIn + offSetIn;
    }

    public double minOffSet(double height) {
        return (height - minMaxDef);
    }

    public double maxOffSet(double height) {
        return (height + minMaxDef);
    }
    
    public boolean atBottom() {
        return (getDistance() <= minHeight);
    }

    public boolean atTop() {
        return (getDistance() >= maxHeight);
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
    
    public boolean atBallLoadingStationLevel() {
        return atLevel_(ballLoadingStationHeight);
    }
}
