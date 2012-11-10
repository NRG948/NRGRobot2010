package nrgrobot.cmd;

import nrgrobot.PositionTracker;

/**
 * Sets the robot's position on the field to the given coordinates.
 * @author Austin
 */
public class SetPositionCommand extends CommandBase
{
    private double x;
    private double y;

    /**
     *
     * @param newX Desired X
     * @param newY Desired Y
     */
    public SetPositionCommand(double newX, double newY)
    {
        x = newX;
        y = newY;
    }

    public void init()
    {
    }

    /**
     * Sets the current position to the coordinates in constructor. 
     * @return True when finished.
     */
    public boolean run()
    {
        PositionTracker.setPosition(x, y);
        return true;
    }
}
