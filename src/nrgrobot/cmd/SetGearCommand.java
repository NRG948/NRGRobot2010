package nrgrobot.cmd;

import nrgrobot.*;

/**
 * This command sets the current gear of the NRGDrive.
 * @author Austin, Brian, Andrew
 */
public class SetGearCommand extends CommandBase
{
    public static final boolean HIGH_GEAR = true;
    public static final boolean LOW_GEAR = false;
    private boolean desiredGear;

    public SetGearCommand(boolean desiredGear)
    {
        this.desiredGear = desiredGear;
    }

    /**
     * Do nothing. 
     */
    public void init()
    {
    }

    /**
     * Changes the gear.
     * @return True when finished.
     */
    public boolean run()
    {
        if (desiredGear == LOW_GEAR)
            GearShift.setLowGear();
        else
            GearShift.setHighGear();
        return true;
    }
}
