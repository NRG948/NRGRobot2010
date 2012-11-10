package nrgrobot;

import edu.wpi.first.wpilibj.*;

/**
 * NRG's implementation of the tank drive with PID and a gear shifter.
 * @author Kenneth Hokwen Austin
 */
public class NRGDrive extends RobotDrive
{ 
    public static DrivePID drivePID;
    private static boolean isReversed;
    private static double scaleDriveJoys;
    DigitalOutput physFrontLED;
    DigitalOutput physBackLED;
    private int pulseTimer;
    private boolean dStraightInit;
    private double dStraightHeading;

    public NRGDrive()
    {
	super(new Jaguar(DriveSettings.LEFT_MOTOR_SLOT, DriveSettings.LEFT_MOTOR_CHANNEL),
		new Jaguar(DriveSettings.RIGHT_MOTOR_SLOT, DriveSettings.RIGHT_MOTOR_CHANNEL));
	Sensors.initLeftEncoder();
	Sensors.initRightEncoder();
	//setInvertedMotor(MotorType.kFrontLeft, true);
	//setInvertedMotor(MotorType.kRearLeft, true);
	// TODO: figure out/fix motors
	Sensors.initGyro();
	drivePID = new DrivePID(this, DriveSettings.DRIVE_PID_CONSTANT_P, DriveSettings.DRIVE_PID_CONSTANT_I, Sensors.leftEncoder, Sensors.rightEncoder);
        GearShift.setLowGear();
	isReversed = false;
        dStraightInit = false;
        scaleDriveJoys = DriveSettings.SCALE_DRIVE_JOYS_DEFAULT;

        //Front/Back LED init
        physFrontLED = new DigitalOutput(DriveSettings.PHYS_FRONT_LED_SLOT, DriveSettings.PHYS_FRONT_LED_CHANNEL);
        physBackLED = new DigitalOutput(DriveSettings.PHYS_BACK_LED_SLOT, DriveSettings.PHYS_BACK_LED_CHANNEL);
        physFrontLED.set(!isReversed);
        physBackLED.set(isReversed);
    }

    public static void setReversed(boolean reversed)
    {
	isReversed = reversed;
    }

    public static boolean getReversed()
    {
	return isReversed;
    }


    public void update()
    {
	if (JoystickHelper.getDriveReverseButton())
        {
	    isReversed = !isReversed;
            physFrontLED.set(!isReversed);
            physBackLED.set(isReversed);
        }

	if (JoystickHelper.getGearShiftHighBtn())
	{
	    GearShift.setHighGear();
	}
	else if (JoystickHelper.getGearShiftLowBtn())
	{
	    GearShift.setLowGear();
	}

        // Toggle between full-speed mode and "finesse" mode that gives finer control
        if (JoystickHelper.getToggleScaleDriveInputs())
        {
            if (scaleDriveJoys != DriveSettings.SCALE_DRIVE_JOYS_DEFAULT)
                scaleDriveJoys = DriveSettings.SCALE_DRIVE_JOYS_DEFAULT;
            else
                scaleDriveJoys = DriveSettings.SCALE_DRIVE_JOYS_FINESSE;
        }
        if (JoystickHelper.getPIDToggleButton())
        {
            if (drivePID.isEnabled())
                drivePID.disable();
            else
            {
                drivePID.reset();
                drivePID.enable();
            }
        }
        if(JoystickHelper.getDriveStraightBtn())
	{  
            if(!dStraightInit)
            {
                dStraightHeading = Sensors.gyro.getAngle();
                dStraightInit = true;
            }
            drivePID.driveStraight(JoystickHelper.getLeftDriveValue(), dStraightHeading);
        }
	else if(pulseTimer <= 0)
	{
            double joyL = scaleDriveJoys * JoystickHelper.getLeftDriveValue();
            double joyR = scaleDriveJoys * JoystickHelper.getRightDriveValue();
            drivePID.calculate(joyL, joyR);
            dStraightInit = false;
        }
        if (JoystickHelper.getGyroReset())
	{
	    Sensors.gyro.reset();
	}

	// Get and display the encoder value on the Driver Station
	// TODO: Set distance per pulse for the encoder to get distance readings.
	// TODO: LCD.println3("L Encoder: " + leftEncoder.getRaw());
	//LCD.println4("GyroA: " + MathHelper.round1(gyro.getAngle()) + "  ");
	//LCD.println5("GyroT: " + MathHelper.round2(gyroTemp. getAverageValue()) + "  ");
        LCD.println4("PID " + (drivePID.isEnabled() ? "On " : "Off") + ", Reverse " + (isReversed ? "On " : "Off"));
    }
}
