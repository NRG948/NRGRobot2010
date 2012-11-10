package nrgrobot.cmd;

import nrgrobot.*;

/**
 * @author Dustin
 */
public class WindCommand extends CommandBase
{
    private double kickPercentPower;

    public WindCommand(double kickWindPower)
    {
	kickPercentPower = kickWindPower;
    }

    public void init()
    {
    }

    public boolean run()
    {
        double power = kickPercentPower / 100.0 * (KickerSettings.KICKER_MAX_PWR_LVL - KickerSettings.KICKER_MIN_PWR_LVL)
                     + KickerSettings.KICKER_MIN_PWR_LVL;
        Team948Robot.kicker.windTo(power);
        return true;
    }
}
