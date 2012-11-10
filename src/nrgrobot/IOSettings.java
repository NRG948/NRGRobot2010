package nrgrobot;

/**
 * This class contains settings for the Cypress Enhanced IO module
 * that are used for the custom driver station manipulator panel
 * and for the HUDs for both the driver and the manipulator
 * @author Stephen, Kenneth, Hokwen, Austin
 */
public class IOSettings
{
    public static final int CYPESS_BOARD_LED_COUNT = 8;

    // These are all channels for the Cypress IO board

    /**
     * @deprecated Low gear LED is obsolete; channel 1 has been converted to input
     */
    public static final int DIG_OUT_LED_LOW_GEAR = 1;
    /**
     * @deprecated High gear LED is obsolete; channel 2 has been converted to input
     */
    public static final int DIG_OUT_LED_HIGH_GEAR = 2;

    public static final int DIG_OUT_LED_TARGET_LEFT = 3;
    public static final int DIG_OUT_LED_TARGET_CENTER = 4;
    public static final int DIG_OUT_LED_TARGET_RIGHT = 5;

    public static final int DIG_OUT_LED_BALL_FRONT = 6;
    public static final int DIG_OUT_LED_BALL_BACK = 7;

    public static final int DIG_OUT_KICK_STRENGTH_METER_1 = 8;
    public static final int DIG_OUT_KICK_STRENGTH_METER_2 = 9;
    public static final int DIG_OUT_KICK_STRENGTH_METER_3 = 10;
    public static final int DIG_OUT_KICK_STRENGTH_METER_4 = 11;
    public static final int DIG_OUT_KICK_STRENGTH_METER_5 = 12;
    public static final int DIG_OUT_KICK_STRENGTH_METER_6 = 13;

    // Max voltage of the kick strength potentiometer,
    // the value that will lead to the maximum possible kick strength.
    public static final double KICK_STRENGTH_METER_MAX_VOLTAGE = 3.3;
    
    // Tolerance for the final light of the kick strength meter to be lit
    public static final double KICK_STRENGTH_METER_FULL_TOLERANCE = 0.03;

    public static final int KICK_STRENGTH_METER_LED_COUNT = 6;

    public static final int DIG_IN_REVERSE_MANIPULATOR_FRONT = 14;
    public static final int DIG_IN_BTN_KICK = 15;
    public static final int DIG_IN_ROLLER_POSSESS_BALL = 1;
    public static final int DIG_IN_ROLLER_REPEL_BALL = 2;

    /**
     * @deprecated Not in use - no deflector on robot.
     */
    public static final int ANA_IN_DEFLECTOR_ANGLE_POTENTIOMETER = 1;
    public static final int ANA_IN_KICK_STRENGTH_POTENTIOMETER = 2;

}
