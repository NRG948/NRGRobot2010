package nrgrobot.cmd;

import nrgrobot.PositionTracker;

/**
 * This class commands the robot to reset its position in PositionTracker
 * @author Austin
 */
public class ResetPositionCommand extends CommandBase
{
    // Do nothing.
    public void init()
    {

    }

    /**
     * Sets the X and Y values to default values defined in AutonomousSettings class. 
     * @return True if the ResetPosition is done. False otherwise.
     */
    public boolean run()
    {
        PositionTracker.reset();
        return true;
    }
}
