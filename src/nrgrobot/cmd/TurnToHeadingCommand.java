package nrgrobot.cmd;

import nrgrobot.*;

/**
 *
 * @author Dustin
 */
public class TurnToHeadingCommand extends CommandBase
{
    private double destinationHeading;
    private double currentHeading;
    private TurnCommand turnCommand;

    public TurnToHeadingCommand(double destinationHeading)
    {
        this.destinationHeading = destinationHeading;
    }

    public void init()
    {
        currentHeading = Sensors.gyro.getAngle();
        double angleToTurnClockwise = MathHelper.convertToSmallestRelativeAngle(destinationHeading - currentHeading);
        turnCommand = new TurnCommand(angleToTurnClockwise);
        turnCommand.init();
    }

    public boolean run()
    {
        return turnCommand.run();
    }
}
