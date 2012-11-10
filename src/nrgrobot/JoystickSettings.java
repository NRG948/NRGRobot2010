package nrgrobot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Joystick.*;

/**
 * Contains settings to map Joysticks and their axes and buttons to robot functions.
 * @author Andrew, Austin
 */
public class JoystickSettings
{
    public static final Joystick LEFT_DRIVE_JOY = JoystickHelper.JOYSTICK_1;
    public static final AxisType LEFT_DRIVE_AXIS = Joystick.AxisType.kY;

    public static final Joystick RIGHT_DRIVE_JOY = JoystickHelper.JOYSTICK_2;
    public static final AxisType RIGHT_DRIVE_AXIS = Joystick.AxisType.kY;

    public static final double JOY_DEADZONE_THRESHOLD = 0.045;

    public static final double LEFT_DRIVE_MULTIPLIER = 1;
    public static final double RIGHT_DRIVE_MULTIPLIER = 1;

    public static final double LEFT_JOY_MULTIPLIER = -1;
    public static final double RIGHT_JOY_MULTIPLIER = -1;

    public static final Joystick FRONT_ROLLER_JOY = JoystickHelper.JOYSTICK_2;
    public static final int FRONT_ROLLER_BTN = 0; //needs "home"
    public static final int BACK_ROLLER_BTN = 2;
    public static final int ROLLER_DIRECTION_BTN = 5;

    public static final Joystick REVERSE_DRIVER_FRONT_JOY_1 = JoystickHelper.JOYSTICK_1;
    public static final int REVERSE_DRIVER_FRONT_BTN_1 = 2;
    public static final Joystick TOGGLE_SCALE_DRIVE_INPUTS_JOY = JoystickHelper.JOYSTICK_2;
    public static final int TOGGLE_SCALE_DRIVE_INPUTS_BTN = 2;

    public static final Joystick PID_TOGGLE_JOY_1 = JoystickHelper.JOYSTICK_1;
    public static final Joystick PID_TOGGLE_JOY_2 = JoystickHelper.JOYSTICK_2;
    public static final int PID_TOGGLE_BTN = 4;

    public static final Joystick GEAR_SHIFT_LOW_JOY = JoystickHelper.JOYSTICK_1;
    public static final Joystick GEAR_SHIFT_HIGH_JOY = JoystickHelper.JOYSTICK_2;
    public static final int GEAR_SHIFT_LOW_BTN = 3;
    public static final int GEAR_SHIFT_HIGH_BTN = 3;

    public static final Joystick TRACKING_THRESHOLD_ADJUSTMENT_JOY_1 = JoystickHelper.JOYSTICK_1;
    public static final int TRACKING_THRESHOLD_INCREMENT_BTN_1 = 8;
    public static final int TRACKING_THRESHOLD_DECREMENT_BTN_1 = 9;
    public static final Joystick TRACKING_THRESHOLD_ADJUSTMENT_JOY_2 = JoystickHelper.JOYSTICK_2;
    public static final int TRACKING_THRESHOLD_INCREMENT_BTN_2 = 8;
    public static final int TRACKING_THRESHOLD_DECREMENT_BTN_2 = 9;

    public static final Joystick TOGGLE_CAMERA_TRACKING_JOY = JoystickHelper.JOYSTICK_1;
    public static final int TOGGLE_CAMERA_TRACKING_BTN = 10;

    public static final Joystick CAMERA_RESET_JOY = JoystickHelper.JOYSTICK_1;
    public static final int CAMERA_RESET_BUTTON = 11;

    public static final Joystick GYRO_RESET_JOY = JoystickHelper.JOYSTICK_2;
    public static final int GYRO_RESET_BTN = 11;


    // Backup controls for manipulator
    public static final Joystick KICK_JOY = JoystickHelper.JOYSTICK_1;
    public static final int KICK_BTN = 1;
    public static final Joystick.AxisType KICK_STRENGTH_AXIS = AxisType.kTwist;

    //Drive straight button
    public static final Joystick DRIVE_STRAIGHT_JOY = JoystickHelper.JOYSTICK_1;
    public static final int DRIVE_STRAIGHT_BUTTON = 1;

    //Reset kicker leg dealio button
    public static final Joystick KICKER_LEG_RESET_JOY = JoystickHelper.JOYSTICK_1;
    public static final Joystick KICKER_LEG_RESET_OTHER_JOY = JoystickHelper.JOYSTICK_2;
    public static final int KICKER_LEG_RESET_BUTTON = 6;
}