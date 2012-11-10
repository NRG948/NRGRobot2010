package nrgrobot.cmd;

import nrgrobot.*;

/**
 * Pretty much only useful for testing...
 * @author Paul
 */
public class TimedTurnCommand extends CommandBase
{
    private long endTime;
    private int timeInMillis;
    private double motorSpeed; // between -1 and 1

    /**
     *
     * @param timeInMillis How long to turn.
     * @param motorSpeed How fast to turn (positive motorSpeed=clockwise, negative=counter-clockwise).
     */
    public TimedTurnCommand(int timeInMillis, double motorSpeed)
    {
        this.timeInMillis = timeInMillis;
        this.motorSpeed = motorSpeed;
    }

    /**
     * Sets what time to stop moving.
     */
    public void init()
    {
        endTime = System.currentTimeMillis() + timeInMillis;
    }

    /**
     *
     * @return True if the TimedTurnCOmmand is done by checking the time. False otherwise.
     */
    public boolean run()
    {
        if (System.currentTimeMillis() >= endTime)
        {
            NRGDrive.drivePID.stop();
            Debug.println(Debug.AUTOCMD, "********************** End of motorSpeed=" + MathHelper.round2(motorSpeed));
            return true;
        }
        NRGDrive.drivePID.calculate(motorSpeed, -motorSpeed, false);
        return false;
    }
}
