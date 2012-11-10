package nrgrobot;

/**
 *
 * @author Stephen
 */
public class IRSensorSet
{
    SharpIRSensor left, middle, right;
    private final double BALL_POSSESSED_THRESHOLD = 6.0;   // TODO: a possessed ball should be within this # of inches

    public IRSensorSet(int leftChannel, /* int middleChannel, */  int rightChannel)
    {
	left = new SharpIRSensor(leftChannel);
	//middle = new SharpIRSensor(middleChannel);
	right = new SharpIRSensor(rightChannel);
    }

    public SharpIRSensor getLeft()
    {
	return left;
    }

    /* public SharpIRSensor getMiddle()
    {
	return middle;
    } */

    public SharpIRSensor getRight()
    {
	return right;
    }

    public boolean ballPossessed()
    {
        // TODO: actually implement something that works here
        //return (middle.getDistance() <= BALL_POSSESSED_THRESHOLD);
        return false;
    }
}
