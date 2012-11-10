package nrgrobot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStationLCD.*;
import nrgrobot.camera.*;
import nrgrobot.cmd.*;

/**
 * The VM is configured to run this class automatically, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team948Robot extends SimpleRobot
{
    public static final String VERSION = "3/25/10SEA";
    public static final boolean USE_DEFAULT_AUTONOMOUS = false;
    public static final boolean GLOBAL_REVERSE = false;   // Set true if robot was assembled backwards with the camera on the rear (doh!)
    
    NRGCamera camera;
    DriverStation ds;
    public static NRGDrive drive;
    TrackerDashboard dash = new TrackerDashboard();
    public static Location location;
    Autonomous auto;
    public static Kicker kicker;
    public static Possessor possessor;
    Compressor compressor;
    public static boolean autonomousMode;

    public Team948Robot()
    {
        // Get reference to driver station object
        ds = DriverStation.getInstance();

        getWatchdog().setExpiration(1.0);

        // Instantiate our main robot classes
        drive = new NRGDrive();
        camera = new NRGCamera();
        location = new Location();
	kicker = new Kicker();
        possessor = new Possessor();
        compressor = new Compressor(DriveSettings.PRESSURE_SWITCH_SLOT,  DriveSettings.PRESSURE_SWITCH_CHANNEL,
                                    DriveSettings.COMPRESSOR_RELAY_SLOT, DriveSettings.COMPRESSOR_RELAY_CHANNEL);
        autonomousMode = false;
    }

    // <editor-fold defaultstate="collapsed" desc="This is how to make an editor fold">

    // </editor-fold>

    public void robotMain()
    {
        compressor.start();     //TODO: review if we want compressor to start while robot still disabled
        while (true)
        {
            getWatchdog().feed();
            if (isDisabled())
            {
                LCD.clear();
                LCD.println1("DISABLED: " + VERSION);
                LCD.update();
                while (isDisabled())
                {
                    location.setLocationFromSwitches();
                    getWatchdog().feed();        
                }
            }
            else if (isAutonomous())
            {
                initAutonomous();
                autonomousMode = true;
                while (isAutonomous() && !isDisabled())
                {
                    LCD.clear();
                    LCD.println1("AUTO: " + VERSION);
                    autonomous();
                    LCD.update();
                }
                autonomousMode = false;
            }
            else if (isOperatorControl())
            {
                // Assume that the air tanks are precharged for autonomous mode and only
                // start the compressor once we enter teleop (so that the compressor's
                // vibrations don't disturb the gyro readings).
                NRGDrive.setReversed(GLOBAL_REVERSE);
                while (isOperatorControl() && !isDisabled())
                {
                    LCD.clear();
                    LCD.println1("TELEOP: " + VERSION);
                    operatorControl();
                    LCD.update();
                }
            }
        }
    }

    private CommandBase[] findFieldDotAutoRoutine()
    {
        switch(location.getFieldLocation())
        {
            case Location.NEAR_FIELD:
                switch(location.getDotsColLocation())
                {
                    case Location.DOTS_COL_1: return Autonomous.cmdsNearFieldDotsCol1;
                    case Location.DOTS_COL_2: return Autonomous.cmdsNearFieldDotsCol2;
                    case Location.DOTS_COL_3: return AutonomousTests.cmdsTestingGyro; //Autonomous.cmdsNearFieldDotsCol2; //
                }
                break;
            case Location.MID_FIELD:
                switch(location.getDotsColLocation())
                {
                    case Location.DOTS_COL_1: return Autonomous.cmdsMidFieldDotsCol1;
                    case Location.DOTS_COL_2: return Autonomous.cmdsMidFieldDotsCol2;
                    case Location.DOTS_COL_3: return AutonomousTests.cmdsTestingDiamond; //;Autonomous.cmdsMidFieldDotsCol3; //
                }
                break;
            case Location.FAR_FIELD:
                switch(location.getDotsColLocation())
                {
                    case Location.DOTS_COL_1: return Autonomous.cmdsFarFieldDotsCol1;
                    case Location.DOTS_COL_2: return Autonomous.cmdsFarFieldDotsCol2;
                    case Location.DOTS_COL_3: return Autonomous.cmdsFarFieldDotsCol3;
                }
                break;
        }
        Debug.println(Debug.CMDDISPATCH, "***** Error: field location is invalid *****");
        return Autonomous.cmdsDefault;
    }

    private void initAutonomous()
    {
        NRGDrive.setReversed(GLOBAL_REVERSE);
        NRGDrive.drivePID.reset();
        GearShift.setLowGear();
        if (!USE_DEFAULT_AUTONOMOUS)
            auto = new Autonomous(findFieldDotAutoRoutine());
        else
            auto = new Autonomous(Autonomous.cmdsDefault);
        possessor.setFrontPossessorState(Possessor.POSSESS);
        possessor.setRearPossessorState(Possessor.POSSESS);
        Target.setThreshold(CameraSettings.TARGET_THRESHOLD_STARTING_VALUE);
        Sensors.gyro.reset();
    }

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous()
    {
        getWatchdog().feed();
        PositionTracker.update();
        camera.update();
	auto.update();
        kicker.update(true);
        possessor.update(true);   //REVIEW: doesn't do anything right now

        double x = MathHelper.round0(PositionTracker.getX());
        double y = MathHelper.round0(PositionTracker.getY());
        double lDist = MathHelper.round0(Sensors.leftEncoder.getDistance());
        double rDist = MathHelper.round0(Sensors.rightEncoder.getDistance());
        
        LCD.println3("(" + x + "," + y + ") LE=" + lDist + " RE=" + rDist);
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl()
    {
        getWatchdog().feed();
        PositionTracker.update();
        drive.update();
        camera.update();
	kicker.update(false);
	//TODO: Take next line out and add kicker
	NRGIO.setKickStrengthLEDs();
        possessor.update(false);
        LCD.println5("MAE F:" + MathHelper.round1(kicker.getFrontMAE().currentKickerAngle()) + " B:" + MathHelper.round1(kicker.getBackMAE().currentKickerAngle()) + " ");
	Debug.println(Debug.IRSENSOR, "FLeft: " + Sensors.iRSensorsFront.getLeft().getDistance() + /* " FMiddle: " +Sensors.iRSensorsFront.getMiddle().getDistance() + */ " FRight: " + Sensors.iRSensorsFront.getRight().getDistance());
	//Debug.println(Debug.IRSENSOR, "BLeft: " + Sensors.iRSensorsBack.getLeft().getDistance() + /*" BMiddle: " +Sensors.iRSensorsBack.getMiddle().getDistance() + */ " BRight: " + Sensors.iRSensorsBack.getRight().getDistance());
    }
}