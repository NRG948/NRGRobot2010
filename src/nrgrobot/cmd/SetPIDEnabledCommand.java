package nrgrobot.cmd;

import nrgrobot.NRGDrive;

/**
 * Sets whether the drive straight PID is enabled or disabled
 * @author Austin
 */
public class SetPIDEnabledCommand extends CommandBase
{
    private boolean isPIDEnabled;

    public SetPIDEnabledCommand(boolean isEnabled)
    {
        isPIDEnabled = isEnabled;
    }

    /**
     * Do nothing.
     */
    public void init()
    {
    }

    /**
     * Enables or disables the PID. 
     * @return True when finished.
     */
    public boolean run()
    {
        if (isPIDEnabled)
            NRGDrive.drivePID.enable();
        else
            NRGDrive.drivePID.disable();
        return true;
    }

}
