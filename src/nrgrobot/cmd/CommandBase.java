package nrgrobot.cmd;

import nrgrobot.*;

/**
 * This is the base class that is the superclass for all Command classes that
 * are used to make the Autonomous routine.
 * @author Austin
 */
public abstract class CommandBase
{
    protected static double lastHeading = 0.0;

    public abstract void init();

    /**
     * Runs the command
     * @return whether the command was completed
     */
    public abstract boolean run();
    /*{
        long currentRunTime = System.currentTimeMillis();
        int currentLeftEncoderValue = Sensors.leftEncoder.getRaw();
        int currentRightEncoderValue = Sensors.rightEncoder.getRaw();
        int dLeftEncoder = currentLeftEncoderValue - previousLeftEncoderValue;
        int dRightEncoder = currentRightEncoderValue - previousRightEncoderValue;
        if (previousRunTime == 0)
            return false; // we just started

        previousRunTime = currentRunTime;
        previousLeftEncoderValue = currentLeftEncoderValue;
        previousRightEncoderValue = currentRightEncoderValue;
    }*/

    public boolean initAndRun()
    {
        init();
        return run();
    }
    
    public String toString()
    {
        String name = this.getClass().getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }
}
