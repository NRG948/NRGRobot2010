package nrgrobot.cmd;

import nrgrobot.*;

/**
 * This command sets the drive direction of the NRGDrive.
 * @author Kenneth, Paul
 */
public class ReverseDrive extends CommandBase
{
    public static final boolean DRIVE_NORMAL = false;
    public static final boolean DRIVE_REVERSE = true;
    private boolean desiredDrive;

    public ReverseDrive(boolean desiredDrive)
    {
        this.desiredDrive = desiredDrive;
    }

    /**
     * Do nothing.
     */
    public void init()
    {
    }

    /**
     * Changes the front and back.
     * @return True when finished.
     */
    public boolean run()
    {
        NRGDrive.setReversed(desiredDrive);
        return true;
    }
}
