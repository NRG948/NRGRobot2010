package nrgrobot.cmd;

import nrgrobot.*;
import com.sun.squawk.util.MathUtils;

/**
 * @author Paul, Dustin
 */

public class TurnTowardCommand extends CommandBase
{
    private double desiredX, desiredY;
    private TurnCommand turnCmd;

    /**
     *
     * @param desiredX What X value we want to go to.
     * @param desiredY What Y value we want to go to.
     */
    public TurnTowardCommand(double desiredX, double desiredY)
    {
        this.desiredX = desiredX;
        this.desiredY = desiredY;
    }

    /**
     * Constructs and initializes a turn command.
     */
    public void init()
    {
        double dX = desiredX - PositionTracker.getX();
        double dY = desiredY - PositionTracker.getY();
        double currentHeading = Sensors.gyro.getAngle();
        double desiredHeading = 90.0 - Math.toDegrees(MathUtils.atan(dY / dX));
        if (dX < 0)
            desiredHeading += 180.0;
        double angleToTurnClockwise = MathHelper.convertToSmallestRelativeAngle(desiredHeading - currentHeading);
        turnCmd = new TurnCommand(angleToTurnClockwise);
        turnCmd.init();
        Debug.println(Debug.AUTOCMD, "Initializing turn command. ");
    }

    public boolean run()
    {
        return turnCmd.run();
    }
}
