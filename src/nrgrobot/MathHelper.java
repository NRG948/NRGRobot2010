package nrgrobot;

import java.util.Random;

/**
 * MathHelper.java provides methods to simplify math functions such as rounding
 * @author Brian, Kenneth
 */
public class MathHelper
{
    public static double round0(double a)
    {
        return (int)(a+0.5);
    }
    public static double round1(double a)
    {
        return ((int) (a*10.0)) / 10.0;
    }
    public static double round2(double a)
    {
        return ((int) (a*100.0)) / 100.0;
    }
    public static double round3(double a)
    {
        return ((int) (a*1000.0)) / 1000.0;
    }
    public static double clamp(double a, double min, double max)
    {
        if (min > max)
            throw new IllegalArgumentException("Min cannot be greater than max.");
        if (a < min)
            return min;
        if (a > max)
            return max;
        return a;
    }
    public static int clamp(int a, int min, int max)
    {
        if (min > max)
            throw new IllegalArgumentException("Min cannot be greater than max.");
        if (a < min)
            return min;
        if (a > max)
            return max;
        return a;
    }

    public static double convertToSmallestRelativeAngle(double angle)
    {
        angle %= 360.0;
        if (angle < -180.0)
            angle += 360.0;
        else if (angle > 180.0)
            angle -= 360.0;
        return angle;
    }
}
