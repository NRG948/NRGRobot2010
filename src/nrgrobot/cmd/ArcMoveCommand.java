package nrgrobot.cmd;

import nrgrobot.*;

/**
 * This command makes the NRGDrive follow a circular arc of a given radius
 * for a specified number of degrees.
 * Positive angles denote clockwise arcs, negative angles denote counter-clockwise arcs.
 *
 * @author Paul, Stephen
 */
public class ArcMoveCommand extends CommandBase
{
    private double degreesClockwise;
    private double arcRadiusInInches;
    private double motorSpeed;
    private double desiredHeading;

    private static final double ARC_SLOW_DOWN_THRESHOLD = 5.0;
    private static final double ARC_DONE_THRESHOLD = 1.5;


    /**
     * Construct the ArcMoveCommand
     * @param degreesClockwise is the number of degrees of arc length (positive = clockwise, negative = ccw)
     * @param arcRadiusInInches is the circle's radius
     * @param motorSpeed Speed at which the motor goes.
     */
    public ArcMoveCommand(double degreesClockwise, double arcRadiusInInches, double motorSpeed)
    {
        this.degreesClockwise = degreesClockwise;
        this.arcRadiusInInches = arcRadiusInInches;
        this.motorSpeed = motorSpeed;
    }

    /**
     * Initializes where the robot will go.
     */
    public void init()
    {
        desiredHeading = Sensors.gyro.getAngle() + (motorSpeed > 0 ? degreesClockwise : -degreesClockwise);
    }

    /**
     * @return True if the ArcMove is done. False otherwise.
     */
    public boolean run()
    {
        double currentHeading = Sensors.gyro.getAngle();
        double error = desiredHeading - currentHeading;
        if (error < ARC_DONE_THRESHOLD)
        {
            // Stop
            Debug.println(Debug.AUTOCMD, "ArcMoveCommand completed to within turn threshold...stopping...");
            NRGDrive.drivePID.stop();
            lastHeading = desiredHeading;
            return true;
        }
        double scaledSpeed = motorSpeed * ((Math.abs(error) < ARC_SLOW_DOWN_THRESHOLD) ? .5 : 1.0);
	double slowerScaleFactor = (arcRadiusInInches - .5 * DriveSettings.TREAD_TO_TREAD_WIDTH ) / (arcRadiusInInches + .5 * DriveSettings.TREAD_TO_TREAD_WIDTH);
        if (degreesClockwise > 0)
        {
            // Slows down right tread to turn right.
	    NRGDrive.drivePID.calculate(scaledSpeed , scaledSpeed * slowerScaleFactor, false);
        }
        else
        {
            // Slows down left tread to turn left.
	    NRGDrive.drivePID.calculate(scaledSpeed * slowerScaleFactor, scaledSpeed, false);
        }
        Debug.println(Debug.AUTOCMD, "TURN Speed:" + motorSpeed + " CurHead: " + currentHeading
                + " DesHead: " + desiredHeading);
        return false;
    }
}
