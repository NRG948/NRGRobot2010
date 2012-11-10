package nrgrobot.cmd;

import nrgrobot.*;

/**
 * This command makes the NRGDrive turn in place a specified number of degrees.
 * @author Austin, Brian, Andrew
 */
public class TurnCommand extends CommandBase
{
    private double degreesClockwise;
    private double desiredHeading;
    private double motorSpeed;
    private double doneThreshold;
    private double turnSlowdownThreshold;

    private static final double TURN_COMMAND_DONE_THRESHOLD = 2.0; //TODO calibrate: Acceptable error in degrees
    private static final double TURN_SLOWDOWN_THRESHOLD_DEGREES = 35.0;
    private static final double MAX_TURN_SPEED = 0.60;
    private static final double MIN_TURN_SPEED = 0.35;   // Seattle TODO: Set just above the speed where friction stalls the turn

    public TurnCommand(double degreesClockwise)
    {
        this.degreesClockwise = degreesClockwise;
	motorSpeed = MAX_TURN_SPEED;
        doneThreshold = TURN_COMMAND_DONE_THRESHOLD;
        turnSlowdownThreshold = TURN_SLOWDOWN_THRESHOLD_DEGREES;
    }

    public TurnCommand(double degreesClockwise, double motorSpeed)
    {
	this.degreesClockwise = degreesClockwise;
        if (Math.abs(degreesClockwise) < 12.0)    // For small turns, slow it down and tighten the accuracy
        {
            motorSpeed *= 0.85;
            doneThreshold *= 3.0/4.0;
            turnSlowdownThreshold *= 0.2;
        }
	this.motorSpeed = motorSpeed;
    }

    public void init()
    {
        desiredHeading = Sensors.gyro.getAngle() + degreesClockwise;
    }

    public boolean run()
    {
        double currentHeading = Sensors.gyro.getAngle();
        double error = desiredHeading - currentHeading;
        Debug.println(Debug.AUTOCMD, "TurnCommand: curHead: " + MathHelper.round1(currentHeading) + ", error: " + MathHelper.round1(error));
        if (Math.abs(error) < doneThreshold)
        {
            NRGDrive.drivePID.stop();
            Debug.println(Debug.AUTOCMD, "Reached end of TurnCommand");
            lastHeading = desiredHeading;
            return true;
        }
        // Use full motorSpeed for large errors, then taper off as the error drops below a threshold
        double directionalSpeed = motorSpeed * MathHelper.clamp(error / TURN_SLOWDOWN_THRESHOLD_DEGREES, -1.0, 1.0);
        // But make sure we don't drive the motors so slow that drivetrain friction stalls us out
        if (directionalSpeed >= 0.0)
            directionalSpeed = MathHelper.clamp(directionalSpeed, MIN_TURN_SPEED, 1.0);
        else
            directionalSpeed = MathHelper.clamp(directionalSpeed, -1.0, -MIN_TURN_SPEED);
        NRGDrive.drivePID.calculate(directionalSpeed, -directionalSpeed, false);
        Debug.println(Debug.AUTOCMD, "TURN Speed:" + directionalSpeed + " CurHead: " + currentHeading
                + " DesHead: " + desiredHeading);
        return false;
    }
}
