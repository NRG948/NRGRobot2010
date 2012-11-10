package nrgrobot;

/**
 * This class tracks the absolute (or relative) position of the robot, to be
 * used especially during the Autonomous period. The reported position will be
 * much less accurate after going over a bump or after turning or moving for an
 * extended period. All measurements are reported in inches. The coordinate
 * system has an origin on the side of the field opposite your driver station in
 * the corner on the side of the field on which your alliance's robots start
 * the match. The positive x-axis extends across the end of the field along the
 * opposing alliance's driver station, and the positive y-axis extends along the
 * side of the field towards your alliance's driver station.
 * @author Austin, Hokwen, Paul
 */
public class PositionTracker
{
    private static double x, y;
    private static double lastLeftEncoder, lastRightEncoder;
    private static boolean initialized = false;
    private static final double EPSILON = 1E-5;

    public static void update()
    {
        // Set initial values if this is the first time update is called
        if (!initialized)
        {
            initialized = true;
            reset();
        }
        
        double newAngle = Sensors.gyro.getAngle();
        double newLeft = Sensors.leftEncoder.getDistance();
        double newRight = Sensors.rightEncoder.getDistance();
        double dLeft = newLeft - lastLeftEncoder;
        double dRight = newRight - lastRightEncoder;
        double distance = (dLeft + dRight) / 2.0;
        // sin and cos are reversed to convert heading to direction
        double dx = distance * Math.sin(Math.toRadians(newAngle));
        double dy = distance * Math.cos(Math.toRadians(newAngle));
        if (distance > EPSILON)
        {
            Debug.println(Debug.POSTRACK, "Dx: " + MathHelper.round2(dx) +
                                         ",Dy: " + MathHelper.round2(dy) +
                                         ", distance: " + MathHelper.round2(distance) +
                                         ", heading:" + MathHelper.round1(newAngle));
        }
        x += dx;
        y += dy;
        lastLeftEncoder = newLeft;
        lastRightEncoder = newRight;
        //LCD.println5("X: " + MathHelper.round1(x) + ", Y: " + MathHelper.round1(y));
    }

    public static double getX()
    {
        return x;
    }

    public static double getY()
    {
        return y;
    }

    public static void setPosition(double newX, double newY)
    {
        x = newX;
        y = newY;
    }

    /**
     * This resets the robot to the default robot position, which assumes the robot
     * is positioned in the (0, 0) corner of the field. It also resets the last
     * encoder values so that future readings can be interpreted correctly.
     * It is recommended to call setPosition (either directly or using an autonomous
     * SetPositionCommand) after resetting so that the initial x and y coordinates
     * will be meaniningful and accurate in terms of the field.
     */
    public static void reset()
    {
        x = AutonomousSettings.DEFAULT_ROBOT_POSITION_X;
        y = AutonomousSettings.DEFAULT_ROBOT_POSITION_Y;
        lastLeftEncoder = Sensors.leftEncoder.getDistance();
        lastRightEncoder = Sensors.rightEncoder.getDistance();
    }
}
