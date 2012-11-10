package nrgrobot.cmd;

import nrgrobot.*;

/**
 *
 * @author Dustin
 */
public class PossessorMode extends CommandBase
{
    private int desiredState;

    public PossessorMode(int desiredState)
    {
        this.desiredState = desiredState;
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
        Team948Robot.possessor.setFrontPossessorState(desiredState);   // TODO: Seattle front or rear???
        return true;
    }
}
