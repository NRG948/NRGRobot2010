package nrgrobot;

import edu.wpi.first.wpilibj.*;

/**
 * Possessor.java provides methods to control the ball possesors on the robot.
 * @author Kenneth, Austin, Paul
 */
public class Possessor
{
    private Jaguar frontJaguar;
    private Jaguar backJaguar;
    private Jaguar logicalFront;
    private Jaguar logicalBack;
    private boolean rollerPossess;
    private boolean rollerRepel;

    public static final int POSSESS = 0;
    public static final int NEUTRAL = 1;
    public static final int REPEL = 2;

    public Possessor()
    {
        frontJaguar = new Jaguar(DriveSettings.FRONT_ROLLER_SLOT, DriveSettings.FRONT_ROLLER_CHANNEL);
        backJaguar = new Jaguar(DriveSettings.BACK_ROLLER_SLOT, DriveSettings.BACK_ROLLER_CHANNEL);
        logicalFront = frontJaguar;
        logicalBack = backJaguar;
        Sensors.initIRSensorSets();
        rollerPossess = false;
        rollerRepel = false;
    }

    private void setReversed(boolean reversed)
    {
        if (reversed)
        {
            logicalFront = backJaguar;
            logicalBack  = frontJaguar;
        } else
        {
            logicalFront = frontJaguar;
            logicalBack  = backJaguar;
        }
    }

    private void setFrontRollerEnable(boolean enable)
    {
        double rollerSpeed = 0.0;
        if (enable && (rollerPossess != rollerRepel))
        {
            rollerSpeed = rollerPossess ? -KickerSettings.POSSESSOR_MOTORSPEED : KickerSettings.POSSESSOR_MOTORSPEED;
        }
        logicalFront.set(rollerSpeed);
    }

    private void setBackRollerEnable(boolean enable)
    {
        double rollerSpeed = 0.0;
        if (enable && (rollerPossess != rollerRepel))
        {
            rollerSpeed = rollerPossess ? KickerSettings.POSSESSOR_MOTORSPEED : -KickerSettings.POSSESSOR_MOTORSPEED;
        }
        logicalBack.set(rollerSpeed);
    }

    public void setFrontPossessorState(int desiredState)
    {
        switch (desiredState)
        {
            case Possessor.NEUTRAL:
                logicalFront.set(0.0);
                break;
            case Possessor.REPEL:
                logicalFront.set(-KickerSettings.POSSESSOR_MOTORSPEED);
                break;
            case Possessor.POSSESS:
            default:
                logicalFront.set(KickerSettings.POSSESSOR_MOTORSPEED);
                break;
        }
    }

    public void setRearPossessorState(int desiredState)
    {
        switch (desiredState)
        {
            case Possessor.NEUTRAL:
                logicalBack.set(0.0);
                break;
            case Possessor.REPEL:
                logicalBack.set(-KickerSettings.POSSESSOR_MOTORSPEED);
                break;
            case Possessor.POSSESS:
            default:
                logicalBack.set(KickerSettings.POSSESSOR_MOTORSPEED);
                break;
        }
    }

    public int getFrontPossessorState()
    {
        if (logicalFront.getSpeed() < 0)
        {
            return Possessor.REPEL;
        } else if (logicalFront.getSpeed() > 0)
        {
            return Possessor.POSSESS;
        } else //It must be neutral, dipshit
        {
            return Possessor.NEUTRAL;
        }
    }

    /* TODO: this doesn't appear to be used/needed?
    public void reversePossessorFrontBack()
    {
        if (logicalFront == frontJaguar)
        {
            logicalFront = backJaguar;
            logicalBack = frontJaguar;
        }
        else
        {
            logicalFront = frontJaguar;
            logicalBack = backJaguar;
        }
    }
     */
    public void update(boolean isAutonomous)
    {
        /* <OLDCODE>
        //toggles on/off for front/back
        if (JoystickHelper.getDigitalValue(JoystickSettings.FRONT_ROLLER_JOY, JoystickSettings.FRONT_ROLLER_BTN))
        {
            if (logicalFront.get() == 0.0)
                frontRollerOn();
            else
                frontRollerOff();
        }
        if (JoystickHelper.getDigitalValue(JoystickSettings.FRONT_ROLLER_JOY, JoystickSettings.BACK_ROLLER_BTN))
        {
            if (logicalBack.get() == 0.0)
                backRollerOn();
            else
                backRollerOff();
        }
        //toggles forward/backward rolling
        if (JoystickHelper.getDigitalValue(JoystickSettings.FRONT_ROLLER_JOY, JoystickSettings.ROLLER_DIRECTION_BTN))
        {
            rollForward = !rollforward;
        }
        </OLDCODE> */

        if (!isAutonomous)
        {
            setReversed(NRGIO.getReverseManipulatorFront());
            rollerPossess = NRGIO.getRollerPossessBall() || JoystickHelper.getPossessButton();
            rollerRepel = NRGIO.getRollerRepelBall();
            setFrontRollerEnable(true);
            setBackRollerEnable(true);
        }
        /*if (NRGIO.getToggleRollerDirection() || Team948Robot.autonomousMode)
        {
            boolean ballFront = Sensors.iRSensorsFront.ballPossessed();
            boolean ballRear  = false; // Sensors.iRSensorsBack.ballPossessed();
            setFrontRollerEnable(ballFront || !ballRear);
            setBackRollerEnable(!ballFront);
        }
        else
        {
            // We're in teleop mode and the rear possessor is disabled at the driver station
            // so always run the front possessor and never run the rear possessor
            setFrontRollerEnable(true);
            setBackRollerEnable(false);
        }*/
        //double ballDistance = iRSensor1.getDistance();
        //Debug.println("Distance to Ball: " + ballDistance);
        //NRGIO.setBallFrontLED(ballDistance > 0);
    }
}
