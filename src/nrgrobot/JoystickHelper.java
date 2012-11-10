package nrgrobot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Joystick.*;
//import java.lang.Math;

/**
 * JoystickHelper.java provides methods to read values from the joystick and its buttons.
 * @author Andrew
 */
public class JoystickHelper
{
    // joysticks

    public static final Joystick JOYSTICK_1 = new Joystick(1);
    public static final Joystick JOYSTICK_2 = new Joystick(2);
    public static final Joystick JOYSTICK_3 = new Joystick(3);
    public static final Joystick JOYSTICK_4 = new Joystick(4);
    public static boolean driveReverseButtonPressed = false;
    public static boolean scaleDriveInputsButtonPressed = false;
    public static boolean PIDToggleButtonPressed = false;

    public static double getAnalogValue(Joystick joy, AxisType axis)
    {
	// can't use a switch statement :(
	if (axis == AxisType.kX)
	{
	    return joy.getX();
	}
	else if (axis == AxisType.kY)
	{
	    return joy.getY();
	}
	else if (axis == AxisType.kZ)
	{
	    return joy.getZ();
	}
	else if (axis == AxisType.kTwist)
	{
	    return joy.getTwist();
	}
	else if (axis == AxisType.kThrottle)
	{
	    return joy.getThrottle();
	}
	else
	{
	    System.out.println("Invalid axis selection.");
	    return 0.0;
	}
    }

    public static boolean getDigitalValue(Joystick joy, int button)
    {
	return joy.getRawButton(button);
    }

    public static boolean getCameraTrackingState()
    {
	return getDigitalValue(JoystickSettings.TOGGLE_CAMERA_TRACKING_JOY, JoystickSettings.TOGGLE_CAMERA_TRACKING_BTN);
    }

    public static double getLeftDriveValue()
    {
	double joyL = JoystickSettings.LEFT_JOY_MULTIPLIER * getAnalogValue(JoystickSettings.LEFT_DRIVE_JOY, JoystickSettings.LEFT_DRIVE_AXIS);
	if (Math.abs(joyL) < JoystickSettings.JOY_DEADZONE_THRESHOLD)
	{
	    return 0.0;
	}
	else
	{
	    return joyL;
	}
    }

    public static double getRightDriveValue()
    {
	double joyR = JoystickSettings.RIGHT_JOY_MULTIPLIER * getAnalogValue(JoystickSettings.RIGHT_DRIVE_JOY, JoystickSettings.RIGHT_DRIVE_AXIS);
	if (Math.abs(joyR) < JoystickSettings.JOY_DEADZONE_THRESHOLD)
	{
	    return 0.0;
	}
	else
	{
	    return joyR;
	}
    }

    public static boolean getDriveReverseButton()
    {
	//sets wasPressed to previous value
	boolean wasPressed = driveReverseButtonPressed;
	//updates driveReverseButtonPressed
	driveReverseButtonPressed = getDigitalValue(JoystickSettings.REVERSE_DRIVER_FRONT_JOY_1, JoystickSettings.REVERSE_DRIVER_FRONT_BTN_1)
		/* || getDigitalValue(JoystickSettings.REVERSE_DRIVER_FRONT_JOY_2, JoystickSettings.REVERSE_DRIVER_FRONT_BTN_2) */
                ;
	return (!wasPressed && driveReverseButtonPressed);
    }

    public static boolean getToggleScaleDriveInputs()
    {
    	//sets wasPressed to previous value
	boolean wasPressed = scaleDriveInputsButtonPressed;
	//updates driveReverseButtonPressed
	getDigitalValue(JoystickSettings.TOGGLE_SCALE_DRIVE_INPUTS_JOY, JoystickSettings.TOGGLE_SCALE_DRIVE_INPUTS_BTN);
        return (!wasPressed && scaleDriveInputsButtonPressed);
    }

    public static boolean getFrontRollerState()
    {
	return getDigitalValue(JoystickSettings.FRONT_ROLLER_JOY, JoystickSettings.FRONT_ROLLER_BTN);
    }

    public static boolean getGyroReset()
    {
	return getDigitalValue(JoystickSettings.GYRO_RESET_JOY, JoystickSettings.GYRO_RESET_BTN);
    }

    public static boolean getPIDToggleButton()
    {
	//sets wasPressed to previous value
	boolean wasPressed = PIDToggleButtonPressed;
	//updates PIDToggleButtonPressed
	PIDToggleButtonPressed = getDigitalValue(JoystickSettings.PID_TOGGLE_JOY_1, JoystickSettings.PID_TOGGLE_BTN)
		|| getDigitalValue(JoystickSettings.PID_TOGGLE_JOY_2, JoystickSettings.PID_TOGGLE_BTN);
	//returns boolean
	return (!wasPressed && PIDToggleButtonPressed);
    }

    public static boolean getTrackingThresholdIncrement()
    {
	return getDigitalValue(JoystickSettings.TRACKING_THRESHOLD_ADJUSTMENT_JOY_1, JoystickSettings.TRACKING_THRESHOLD_DECREMENT_BTN_1)
		|| getDigitalValue(JoystickSettings.TRACKING_THRESHOLD_ADJUSTMENT_JOY_2, JoystickSettings.TRACKING_THRESHOLD_DECREMENT_BTN_2);
    }

    public static boolean getTrackingThresholdDecrement()
    {
	return getDigitalValue(JoystickSettings.TRACKING_THRESHOLD_ADJUSTMENT_JOY_1, JoystickSettings.TRACKING_THRESHOLD_INCREMENT_BTN_1)
		|| getDigitalValue(JoystickSettings.TRACKING_THRESHOLD_ADJUSTMENT_JOY_2, JoystickSettings.TRACKING_THRESHOLD_INCREMENT_BTN_2);
    }

    public static boolean getGearShiftHighBtn()
    {
	return getDigitalValue(JoystickSettings.GEAR_SHIFT_HIGH_JOY, JoystickSettings.GEAR_SHIFT_HIGH_BTN);
    }

    public static boolean getGearShiftLowBtn()
    {
	return getDigitalValue(JoystickSettings.GEAR_SHIFT_LOW_JOY, JoystickSettings.GEAR_SHIFT_LOW_BTN);
    }

    public static boolean getCameraResetBtn()
    {
	return getDigitalValue(JoystickSettings.CAMERA_RESET_JOY, JoystickSettings.CAMERA_RESET_BUTTON);
    }

    public static boolean getDriveStraightBtn()
    {
	return getDigitalValue(JoystickSettings.DRIVE_STRAIGHT_JOY, JoystickSettings.DRIVE_STRAIGHT_BUTTON);
    }

    public static boolean getKickerLegResetFOR_PIT_USE_ONLY()
    {
        return (getDigitalValue(JoystickSettings.KICKER_LEG_RESET_JOY, JoystickSettings.KICKER_LEG_RESET_BUTTON)
                && getDigitalValue(JoystickSettings.KICKER_LEG_RESET_OTHER_JOY, JoystickSettings.KICKER_LEG_RESET_BUTTON));
    }



    // Backup manipulator controls
    public static boolean getKickBtn()
    {
        return getDigitalValue(JoystickSettings.KICK_JOY, JoystickSettings.KICK_BTN);
    }

    public static double getKickStrength()
    {
        return getAnalogValue(JoystickSettings.KICK_JOY, JoystickSettings.KICK_STRENGTH_AXIS);
    }

    public static boolean getPossessButton()
    {
        return getDigitalValue(JoystickHelper.JOYSTICK_1, 7);
        // quick fix for finals!!!
    }
}
