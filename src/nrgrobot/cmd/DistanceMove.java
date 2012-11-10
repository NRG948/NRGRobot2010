package nrgrobot.cmd;

import nrgrobot.*;
/**
 *
 * @author Stephen
 */
public class DistanceMove extends CommandBase
{
    double distanceInInches;
    double startDistanceLeft;
    double startDistanceRight;
    double motorSpeed;
    double heading;

    public static final double AUTO_MOVE_DISTANCE_THRESHOLD = 0.5;

    /**
     *
     * @param distanceInInches Distance to move in inches.
     * @param motorSpeed Speed to move motor. 
     */
    public DistanceMove(double distanceInInches, double motorSpeed)
    {
	this.distanceInInches = distanceInInches;
	this.motorSpeed = motorSpeed;
    }

    /**
     * Initializes command.
     */
    public void init()
    {
	startDistanceLeft = Sensors.leftEncoder.getDistance();
	startDistanceRight = Sensors.rightEncoder.getDistance();
        //heading = Sensors.gyro.getAngle();
    }

    /**
     *
     * @return True if the DistanceMove is done. False otherwise.
     */
    public boolean run()
    {
	double dLeft  = Math.abs(Sensors.leftEncoder.getDistance()  - startDistanceLeft);
	double dRight =	Math.abs(Sensors.rightEncoder.getDistance() - startDistanceRight);
	double inchesRemaining = distanceInInches - (dLeft + dRight) / 2;
        if (inchesRemaining <= AUTO_MOVE_DISTANCE_THRESHOLD)
	{
	    NRGDrive.drivePID.stop();
	    return true;
	}
	else
	{
            // Ramp down the speed for the last 3 inches of movement
            double adjSpeed = motorSpeed * MathHelper.clamp(inchesRemaining / 3.0, 0.3, 1.0);
	    NRGDrive.drivePID.driveStraight(adjSpeed, lastHeading);
	    return false;
	}
    }
}
