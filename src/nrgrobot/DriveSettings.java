package nrgrobot;

/**
 * This class contains settings for the NRGDrive and the DrivePID.
 * @author Andrew, Paul
 */
public class DriveSettings
{
    public static final int LEFT_MOTOR_SLOT = 4;
    public static final int LEFT_MOTOR_CHANNEL = 1;
    public static final int RIGHT_MOTOR_SLOT = 4;
    public static final int RIGHT_MOTOR_CHANNEL = 2;

    public static final int DRIVE_ENCODER_SLOT = 4;
    public static final int LEFT_DRIVE_ENCODER_CHANNEL_A = 7;
    public static final int LEFT_DRIVE_ENCODER_CHANNEL_B = 8;
    public static final int RIGHT_DRIVE_ENCODER_CHANNEL_A = 9;
    public static final int RIGHT_DRIVE_ENCODER_CHANNEL_B = 10;

    // For a raw encoder value, should be 1024, otherwise 256
    //public static final int DRIVE_ENCODER_PULSES_PER_ROTATION = 1024;
    public static final int DRIVE_ENCODER_MIN_RATE = 1;
    public static final double DRIVE_ENCODER_PULSES_PER_INCH = 58.38;
    public static final boolean DRIVE_ENCODER_REVERSE = false;

    public static final double DRIVE_PID_CONSTANT_P = 0.8;
    public static final double DRIVE_PID_CONSTANT_I = 0.2;
    //public static final double DRIVE_PID_CONSTANT_D = 0.0;
    //public static final double DRIVE_PID_PERIOD = 0;      // TODO: not required?

    public static final int GYRO_SLOT = 1;
    public static final int GYRO_DATA_CHANNEL = 1;
    public static final int GYRO_TEMP_CHANNEL = 2;
    
    // volts per degree per second
    // TODO: tune this more
    // TODO: compensate gyro readings for temperature changes
    public static final double GYRO_SENSITIVITY = 0.007022 * 364/360;//0.007326667// or .007 * 376.8 / 360;// roughly at 70ish degrees
    
    // TODO: find actual slot and channels for rollers
    public static final int FRONT_ROLLER_SLOT = 4;
    public static final int FRONT_ROLLER_CHANNEL = 5;
    public static final int BACK_ROLLER_SLOT = 4;
    public static final int BACK_ROLLER_CHANNEL = 4;

    // TODO: Find the actual slot, channel and speeds
    public static final int PNEUMATIC_SHIFTER_SLOT = 8;
    public static final int PNEUMATIC_SHIFTER_CHANNEL = 1;
    //public static final int SHIFT_SPEED_1 = 0;
    //public static final int SHIFT_SPEED_2 = 0;

    // TODO: set actual slot/channel values
    public static final int PRESSURE_SWITCH_SLOT = 4;
    public static final int PRESSURE_SWITCH_CHANNEL = 14;
    public static final int COMPRESSOR_RELAY_SLOT = 4;
    public static final int COMPRESSOR_RELAY_CHANNEL = 8;

    //Front/Back LEDs
    public static final int PHYS_FRONT_LED_SLOT = 4;
    public static final int PHYS_FRONT_LED_CHANNEL = 11;
    public static final int PHYS_BACK_LED_SLOT = 4;
    public static final int PHYS_BACK_LED_CHANNEL = 12;

    public static final int DRIVE_INPUT_AVERAGER_PERIOD = 3;

    public static final boolean SQUARE_JOYSTICK_INPUTS = false;
    public static final double SCALE_DRIVE_JOYS_DEFAULT = 1.0;
    public static final double SCALE_DRIVE_JOYS_FINESSE = 0.5;

    public static final double TREAD_TO_TREAD_WIDTH = 20.6;     // In inches

    //TODO: adjust target distance adjustors
    public static final int MIDFIELD_TARGET_DISTANCE_ADJUSTOR = 19 * 12;
    public static final int RAMP_LENGTH_ADJUSTOR = 6;//inches
    public static final int FARFIELD_TARGET_DISTANCE_ADJUSTOR = (54 * 12) - RAMP_LENGTH_ADJUSTOR;
}