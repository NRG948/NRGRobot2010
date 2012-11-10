package nrgrobot;

/**
 * AnalogSettings.java provides channels, slots, and other assorted values as settings for the analog sensors other than encoders.
 * @author Stephen
 */
public class AnalogSettings
{
    //TODO: figure out actual values for all of these
    public static final int SHARP_SENSOR_SLOT = 1;
    public static final int SHARP_SENSOR_FRONT_LEFT_CHANNEL = 5;
    //public static final int SHARP_SENSOR_FRONT_MIDDLE_CHANNEL = ???;
    public static final int SHARP_SENSOR_FRONT_RIGHT_CHANNEL = 6;
    public static final int SHARP_SENSOR_BACK_LEFT_CHANNEL = 7;
    //public static final int SHARP_SENSOR_BACK_MIDDLE_CHANNEL = ???;
    public static final int SHARP_SENSOR_BACK_RIGHT_CHANNEL = 8;
    public static final double SHARP_SENSOR_CALIBRATION_CONSTANT = 4.0;
    public static final double CENTIMETERS_PER_INCH = 2.54;
    public static final double SHARP_SENSOR_DISTANCE_MIN = 1.75;
    public static final double SHARP_SENSOR_DISTANCE_MAX = 12.0;
}
