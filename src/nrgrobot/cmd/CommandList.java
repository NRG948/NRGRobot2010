package nrgrobot.cmd;

import nrgrobot.*;

/**
 *
 * @author Austin, Paul
 */
public class CommandList extends CommandBase
{
    private Autonomous auto;

    public CommandList(CommandBase[] cmds)
    {
        auto = new Autonomous(cmds);
    }

    public void init()
    {
    }

    public boolean run()
    {
        auto.update();
        return auto.commandsCompleted();
    }
}