package nrgrobot.cmd;

import nrgrobot.*;
import nrgrobot.camera.*;
import com.sun.squawk.util.MathUtils;

/**
 * Commands the robot to turn to center on the target tracked by the camera.
 * @author Andrew, Austin
 */
public class CenterOnTargetCommand extends CommandBase
{
    // Do nothing.
    public void init()
    {

    }

    /**
     * @return True if the CenterOnTarget is done. False otherwise.
     */
    public boolean run()
    {
        if (NRGCamera.getTargetFound())
        {
            double error = NRGCamera.getHorizontalTargetPosition();
            double degreesToTurn = error * CameraSettings.CAMERA_DEGREES_PER_HORIZONTAL_TRACKING_UNIT;
            Debug.println(Debug.AUTOCMD, "Target Centering: error " + MathHelper.round1(error) + ", turning " + MathHelper.round1(degreesToTurn) + " deg.");
            // Adds a turn command to turn to target. 
            TurnCommand cmd = new TurnCommand(degreesToTurn);
            cmd.init();
            boolean done = cmd.run();
            Debug.println(Debug.AUTOCMD, "CenterOnTargetCommand " + (done ? "" : "not ") + "done...");
            return done;
        }

        Debug.println(Debug.AUTOCMD, "CenterOnTargetCommand couldn't find a target!");
        // Haven't found a target yet! So use dead reckoning to aim at where we think the target is...
        Target.decrementThreshold();
        double dX = Autonomous.X_TARGET - PositionTracker.getX();
        double dY = Team948Robot.location.getTargetY() - PositionTracker.getY();
        double desiredAngle = Math.toDegrees(MathUtils.atan(dX / dY));
        if (dY < 0)
            desiredAngle += 180.0;
        double currentAngle = Sensors.gyro.getAngle();
        double errorAngle = MathHelper.convertToSmallestRelativeAngle(desiredAngle - currentAngle);
        TurnCommand cmd = new TurnCommand(errorAngle);
        cmd.init();
        cmd.run();
        return false;
    }
}
