package nrgrobot;

/**
 * Debug.java prints out debugging lines if the static boolean DEBUGGING is true
 * @author Paul Davis
 */
public class Debug
{
    public static boolean DEBUGGING = true;     // "master" enable for all debug output

    public static boolean DRIVEPID     = false; // Both encoder- and gyro-based driving PID adjustments
    public static boolean POSTRACK     = false; // PositionTracker for dead reckoning
    public static boolean LOCATION     = false; // Field location switch settings
    public static boolean CAMERA       = false; // Camera target tracking and range-finding
    public static boolean IRSENSOR     = false; // Ball detection
    public static boolean KICKER       = true;  // Kicker control
    public static boolean MAE          = false; // Excruciating details of raw and offset MAE readings
    public static boolean ROLLER       = false; // Ball possessor rollers
    public static boolean AUTOCMD      = true;  // Detailed trace of autonomous commands
    public static boolean CMDDISPATCH  = true;  // Basic trace of each autonomous command issued

    private static void print(String s)
    {
        if (DEBUGGING)
            System.out.print(s);
    }

    public static void print(boolean enabled, Object o)
    {
        if (enabled)
            print(o.toString());
    }

    private static void println(String s)
    {
        if (DEBUGGING)
            System.out.println(s);
    }

    public static void print(boolean enabled, String s)
    {
        if (DEBUGGING && enabled)
            System.out.print(s);
    }

    public static void println(boolean enabled, String s)
    {
        if (DEBUGGING && enabled)
            System.out.println(s);
    }

    public static void println(boolean enabled, Object o)
    {
        if (enabled)
            println(o.toString());
    }

    public static void enable()
    {
        DEBUGGING = true;
    }

    public static void disable()
    {
        DEBUGGING = false;
    }
}
