package nrgrobot.cmd;

import nrgrobot.*;

/**
 * Waits for the specified time interval
 * @author Austin
 */
public class WaitCommand extends CommandBase
{
    private long endTime;
    private int timeInMillis;

    public WaitCommand(int timeInMillis)
    {
        this.timeInMillis = timeInMillis;
    }

    public void init()
    {
        endTime = System.currentTimeMillis() + timeInMillis;
        NRGDrive.drivePID.stop();
    }

    public boolean run()
    {
        Debug.println(Debug.AUTOCMD, "WaitCommand: " + (endTime - System.currentTimeMillis()) + " msec remaining...");
        return (System.currentTimeMillis() >= endTime);
    }
}
