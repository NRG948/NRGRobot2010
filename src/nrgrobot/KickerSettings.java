package nrgrobot;

/**
 * KickerSettings.java provides settings values for the kicker
 * @author Manderson
 */
public class KickerSettings 
{
    public static final int FRONT_MAE_SLOT = 1;
    public static final int FRONT_MAE_CHANNEL = 3;
    public static final int BACK_MAE_SLOT = 1;
    public static final int BACK_MAE_CHANNEL = 4;
    //public static final int FRONT_SERVO_SHIFTER_SLOT = 4;
    //public static final int FRONT_SERVO_SHIFTER_CHANNEL = 5;
    //public static final int BACK_SERVO_SHIFTER_SLOT = 4;
    //public static final int BACK_SERVO_SHIFTER_CHANNEL = 6;
    public static final int FRONT_KICKER_DOG_SOLENOID_SLOT = 8;
    public static final int FRONT_KICKER_DOG_SOLENOID_CHANNEL = 2;
    public static final int BACK_KICKER_DOG_SOLENOID_SLOT = 8;
    public static final int BACK_KICKER_DOG_SOLENOID_CHANNEL = 3;

    public static final boolean FRONT_REVERSE_MAE = true;
    public static final boolean BACK_REVERSE_MAE = false;

    public static final boolean REVERSE_DOG_ACTUATORS = true;

    public static final int WINDER_SLOT = 4;
    public static final int WINDER_CHANNEL = 3;
    public static final double WINDER_REVERSE = -1.0;

    //TODO: And as with every line of code I've written so far, these settings most DEFINITELY need to be tweaked...
    //These values represent angular displacement of the kicker from vertical/relaxed
    public static final double KICKER_RELAXED_POSITION = 0;
    //public static final double KICKER_PWR_LVL_1 = 15;
    //public static final double KICKER_PWR_LVL_2 = 45;
    //public static final double KICKER_PWR_LVL_3 = 65;
    public static final double KICKER_MAX_PWR_LVL = 148;  //90; // Seattle TODO: adjust min/max for both kickers!
    public static final double KICKER_MIN_PWR_LVL = 70;   //26;
    //public static final double KICKER_MAX_PWR_LVL = KICKER_MAX_PWR_LVL_FROM_COMPLETELY_RELAXED_ZERO - KICKER_MIN_PWR_LVL_FROM_COMPLETELY_RELAXED_ZERO;
    //public static final double KICKER_MIN_PWR_LVL = 0;


    //TODO: PID coefficients need adjustment
    public static final double WINDER_KP = 0.08;
    public static final double WINDER_KI = 0.012;
    public static final double WINDER_KD = 0;

    //TODO: NOT EXACT, FIX
    public static final double KICKER_ANGLE_SETPOINT_TOLERANCE = 2;

    // How fast do we turn the ball possessor rollers by default
    public static final double POSSESSOR_MOTORSPEED = 1.0; // old value: 0.6;

    //How long and how fast do we want to "pulse" backwards, just prior to kicking?
    public static final int PULSE_TIME = 5;
    public static final double PULSE_SPEED = 0.8;

    public static final long PIT_KICKER_RESET_ZERO_DELAY = 1000; //this is in milliseconds

    public static final int NUM_LOOPS_TO_WAIT_AFTER_KICKING_AND_BEFORE_REWINDING = 10;

    public static final int MIN_NUM_MILLISECONDS_BETWEEN_KICKS = 2000;

    public static final long MAX_TIME_BEFORE_TIMER_FORCED_REWINDING = 1500;

    public static final long WINDER_EASE_DELAY = 25;
}
