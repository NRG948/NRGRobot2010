package nrgrobot.cmd;
import nrgrobot.*;
/**
 *
 * @author Stephen, Dustin
 */
public class KickCommand extends CommandBase
{
    private boolean isTrackingTarget;
    private boolean needToVerifyKick;
    private double angleBeforeKick;

    /**
     *
     * @param kickWindPower
     */
    public KickCommand()
    {
    }

    public void init()
    {
        isTrackingTarget = false;
        needToVerifyKick = false;
    }

    /**
     * Kicks the ball in autonomous mode
     * @return True if the KickCommand is done. False otherwise.
     */
    public boolean run()
    {
        int possessorState = Possessor.POSSESS;

        if (isTrackingTarget)
        {
            // TODO: .... not implemented yet
            isTrackingTarget = false;
            return false;
        }
        else if (needToVerifyKick)
        {
            // Don't end this kick command until we see the MAE angle decrease by at least 1/2
            // the minimum kicker wind angle (in case the kicker dog gets stuck momentarily).
            boolean actuallyKicked = (Team948Robot.kicker.getActualKickerAngle() < angleBeforeKick - KickerSettings.KICKER_MIN_PWR_LVL/2);
            if (actuallyKicked)
                Team948Robot.possessor.setFrontPossessorState(possessorState);
            return actuallyKicked;
        }
        else if (Team948Robot.kicker.isWoundAndReadyToFire())
        {
            possessorState = Team948Robot.possessor.getFrontPossessorState();
            angleBeforeKick = Team948Robot.kicker.getActualKickerAngle();
            Team948Robot.kicker.actuallyKickFront(false);
            needToVerifyKick = true;
            Debug.println(Debug.AUTOCMD, "KickCmd: angle=" + MathHelper.round1(angleBeforeKick) + " Released the kicker: " + needToVerifyKick);
            return false;
        }
        else
        {
            Debug.println(Debug.AUTOCMD, "KickCmd: waiting to wind...");
            return false;
        }
    }
}
