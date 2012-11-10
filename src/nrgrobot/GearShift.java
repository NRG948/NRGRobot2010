package nrgrobot;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * GearShift.java implements a solenoid to be used to shift gears on the robot.
 * @author Stephen
 */
public class GearShift
{
    private static boolean isLowGear = true;
    private static Solenoid shiftSolenoid
             = new Solenoid(DriveSettings.PNEUMATIC_SHIFTER_SLOT, DriveSettings.PNEUMATIC_SHIFTER_CHANNEL);

    // Calls method from superclass to tell if the shifter is in the low gear position, and actually activate it
    private static void update()
    {
        shiftSolenoid.set(!isLowGear);
	//NRGIO.setLowGearLED(isLowGear);
	//NRGIO.setHighGearLED(!isLowGear);
    }

    // Sets the value of the boolean isLowGear to true, still need to call update method
    public static void setHighGear()
    {
        isLowGear = false;
        update();
    }

    // Sets the value of the boolean isLowGear to true, still need to call update method
    public static void setLowGear()
    {
        isLowGear = true;
        update();
    }

    // Return the current gear of the shifter. True for low gear, false for high gear.
    public static boolean inLowGear()
    {
        return isLowGear;
    }
}