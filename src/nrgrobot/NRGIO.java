package nrgrobot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;

/**
 * This class encapsulates the digital and analog inputs and outputs from the
 * Cypress Enhanced IO module used in the custom manipulator driver station
 * and both the driver and manipulator HUDs.
 * @author Stephen, Kenneth, Hokwen, Austin
 */
public class NRGIO
{
    private static final DriverStationEnhancedIO io = DriverStation.getInstance().getEnhancedIO();

    /**
     * Helper method to write a digital value to the specified channel
     * Note: an error occurs if the specified channel is configured as an input
     * @param channel the digital channel of which to set the value, 1 to 16
     * @param value the value to write to the channel
     */
    private static void setDigital(int channel, boolean value)
    {
	try
	{
	    io.setDigitalOutput(channel, value);
	    // There are fewer LEDs on the Cypress board itself than there are
	    // digital channels, so we need to limit which LED channels we set
	    if (channel <= IOSettings.CYPESS_BOARD_LED_COUNT)
		io.setLED(channel, value);
	}
	catch (EnhancedIOException ex)
	{
	    LCD.println1(ex.getMessage());
	}
    }

    /**
     * Helper method for digital inputs
     * @param channel the digital channel to read as a digital input, 1 to 16
     * @return the current value of the specified digital channel
     */
    private static boolean getDigital(int channel)
    {
	boolean isPressed = false;
	try
	{
	    isPressed = io.getDigital(channel);
	}
	catch (EnhancedIOException ex)
	{
	    LCD.println1(ex.getMessage());
	}
	return isPressed;
    }

    /**
     * Helper method for analog inputs
     * @param channel the analog channel to read as an analog input, 1 to 8
     * @return the current value of the specified analog channel
     */
    private static double getAnalogIn(int channel)
    {
	double analog = 0.0;
	try
	{
	    analog = io.getAnalogIn(channel);
	}
	catch (EnhancedIOException ex)
	{
	    LCD.println1(ex.getMessage());
	}
	return analog;
    }

    /**
     * @deprecated don't use - channel in use as input
     */
    public static void setLowGearLED(boolean value)
    {
	setDigital(IOSettings.DIG_OUT_LED_LOW_GEAR, value);
    }

    /**
     * @deprecated don't use - channel in use as input
     */
    public static void setHighGearLED(boolean value)
    {
	setDigital(IOSettings.DIG_OUT_LED_HIGH_GEAR, value);
    }

    public static void setTargetLeftLED(boolean value)
    {
	setDigital(IOSettings.DIG_OUT_LED_TARGET_LEFT, value);
    }

    public static void setTargetCenterLED(boolean value)
    {
	setDigital(IOSettings.DIG_OUT_LED_TARGET_CENTER, value);
    }

    public static void setTargetRightLED(boolean value)
    {
	setDigital(IOSettings.DIG_OUT_LED_TARGET_RIGHT, value);
    }

    public static void setBallFrontLED(boolean value)
    {
	setDigital(IOSettings.DIG_OUT_LED_BALL_FRONT, value);
    }

    public static void setBallBackLED(boolean value)
    {
	setDigital(IOSettings.DIG_OUT_LED_BALL_BACK, value);
    }

    public static void setKickStrengthLEDs()
    {
	double kickStrength = getKickStrengthPotentiometer();
        //kickStrength = Math.max(kickStrength, JoystickHelper.getKickStrength()); // backup manipulator control
	double voltageRatio = IOSettings.KICK_STRENGTH_METER_MAX_VOLTAGE / (IOSettings.KICK_STRENGTH_METER_LED_COUNT - 1);
	setDigital(IOSettings.DIG_OUT_KICK_STRENGTH_METER_1, kickStrength >= 0.0);
	setDigital(IOSettings.DIG_OUT_KICK_STRENGTH_METER_2, kickStrength >= (1.0 * voltageRatio));
	setDigital(IOSettings.DIG_OUT_KICK_STRENGTH_METER_3, kickStrength >= (2.0 * voltageRatio));
	setDigital(IOSettings.DIG_OUT_KICK_STRENGTH_METER_4, kickStrength >= (3.0 * voltageRatio));
	setDigital(IOSettings.DIG_OUT_KICK_STRENGTH_METER_5, kickStrength >= (4.0 * voltageRatio));
	setDigital(IOSettings.DIG_OUT_KICK_STRENGTH_METER_6, kickStrength >= ((IOSettings.KICK_STRENGTH_METER_LED_COUNT - 1)
                * (1.0 - IOSettings.KICK_STRENGTH_METER_FULL_TOLERANCE) * voltageRatio));
    }

    public static boolean getReverseManipulatorFront()
    {
        // Reads whether the manipulator is NOT reversed, so the return value
        // is the reverse of input reading.
	return !getDigital(IOSettings.DIG_IN_REVERSE_MANIPULATOR_FRONT);
    }

    public static boolean getKickButton()
    {
	return getDigital(IOSettings.DIG_IN_BTN_KICK);
    }

    public static boolean getRollerPossessBall()
    {
	return getDigital(IOSettings.DIG_IN_ROLLER_POSSESS_BALL);
    }

    public static boolean getRollerRepelBall()
    {
	return getDigital(IOSettings.DIG_IN_ROLLER_REPEL_BALL);
    }

    /**
     * @deprecated No deflector in use on the robot.
     */
    public static double getDeflectorAnglePotentiometer()
    {
	return getAnalogIn(IOSettings.ANA_IN_DEFLECTOR_ANGLE_POTENTIOMETER);
    }

    public static double getKickStrengthPotentiometer()
    {
	return getAnalogIn(IOSettings.ANA_IN_KICK_STRENGTH_POTENTIOMETER);
    }
}