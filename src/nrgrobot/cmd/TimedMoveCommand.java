package nrgrobot.cmd;

import nrgrobot.*;

/**
 * Moves linearly forward or backward at the specified velocity for the specified time interval
 * @author Andrew
 */
public class TimedMoveCommand extends CommandBase
{
    private long endTime;
    private int timeInMillis;
    private double motorSpeed; // between -1 and 1

    /**
     *
     * @param timeInMillis How long to move forward.
     * @param motorSpeed How fast to move. 
     */
    public TimedMoveCommand(int timeInMillis, double motorSpeed)
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
     * @return True if the TimedMove is done by checking the time. False otherwise.
     */
    public boolean run()
    {
        if (System.currentTimeMillis() >= endTime)
        {
            NRGDrive.drivePID.stop();
            //double x = PositionTracker.getX();
            //double y = PositionTracker.getY();
            //Debug.println(Debug.AUTOCMD, "Dist: " + MathHelper.round1(Math.sqrt(x * x + y * y)) + " MtrSpd: " + motorSpeed);
            return true;
        }
        NRGDrive.drivePID.driveStraight(motorSpeed, lastHeading);
        return false;
    }
}
