package nrgrobot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;

/**
 * This class simplifies printing to the dashboard.
 * @author Dustin Lee
 */
public class LCD
{
    private static DriverStationLCD lcd = null;
    
    // Gets an instance of the LCD class
    private static DriverStationLCD getLCDInstance()
    {
        if (lcd == null)
            lcd = DriverStationLCD.getInstance();
        return lcd;
    }
    
    // Prints Strings to dashboard
    public static void println1 (String s) { getLCDInstance().println(Line.kMain6, 1, s); }    
    public static void println2 (String s) { getLCDInstance().println(Line.kUser2, 1, s); }
    public static void println3 (String s) { getLCDInstance().println(Line.kUser3, 1, s); }
    public static void println4 (String s) { getLCDInstance().println(Line.kUser4, 1, s); }
    public static void println5 (String s) { getLCDInstance().println(Line.kUser5, 1, s); }
    public static void println6 (String s) { getLCDInstance().println(Line.kUser6, 1, s); }

    public static void println1 (int col, String s) { getLCDInstance().println(Line.kMain6, col, s); }
    public static void println2 (int col, String s) { getLCDInstance().println(Line.kUser2, col, s); }
    public static void println3 (int col, String s) { getLCDInstance().println(Line.kUser3, col, s); }
    public static void println4 (int col, String s) { getLCDInstance().println(Line.kUser4, col, s); }
    public static void println5 (int col, String s) { getLCDInstance().println(Line.kUser5, col, s); }
    public static void println6 (int col, String s) { getLCDInstance().println(Line.kUser6, col, s); }
    
    // Clears LCD on dashboard
    public static void clear()
    {
        String s = "";
        for (int i = 0; i < DriverStationLCD.kLineLength; i++)
            s += " ";
        getLCDInstance().println(Line.kMain6, 1, s);
        getLCDInstance().println(Line.kUser2, 1, s);
        getLCDInstance().println(Line.kUser3, 1, s);
        getLCDInstance().println(Line.kUser4, 1, s);
        getLCDInstance().println(Line.kUser5, 1, s);
        getLCDInstance().println(Line.kUser6, 1, s);
        // Make sure to update LCD later (if we update here it flickers blank...)
    }

    // Sends changes to the LCD on dashboard
    public static void update()
    {
        getLCDInstance().updateLCD();
    }
}
