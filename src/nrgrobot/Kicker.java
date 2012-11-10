package nrgrobot;

import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author Manderson
 *
 * DO NOT TOUCH THIS FILE UNLESS IF YOU ARE MATT ANDERSON.  IF YOU ARE NOT, YOU ARE A NOOB.
 * Revision: Matt doesn't want Stephen messing this up. If you aren't Stephen and you aren't
 * going to mess up, go ahead. Just be careful... very careful! I'm watching you...
 */
public class Kicker
{
    private ShifterMAEGroup frontShifterMAEGroup;
    private ShifterMAEGroup backShifterMAEGroup;
    private Jaguar windingMotor;
    private double m_reverseWindingMotor;
    private double m_desiredKickerPosition;    //Ranges between 0 (relaxed position) and 90 (90 degrees to vertical, relaxed position)
    private double m_angleMAE;
    private boolean m_pidEnabled;
    private double kP;
    private double kI;
    private double kD;
    private double m_error;
    private double m_totalError;
    private int numberOfZeroPositionLoops;
    //private boolean frontDogEngaged;
    //private Solenoid dogSolenoid;
    private boolean reversed;
    //private final double kEngagedServoAngle = 0; //TODO: THESE NEED TO BE CALIBRATED!
    //private final double kDisengagedServoAngle = 170;
    private int prevPossessorState = Possessor.NEUTRAL;
    //private double currentLeftDrivePower = 0.0;
    //private double currentRightDrivePower = 0.0;
    private long timestampOfLastKick;
    private boolean hasKickedBefore = false;


    public Kicker()
    {
        frontShifterMAEGroup = new ShifterMAEGroup( /*  KickerSettings.FRONT_SERVO_SHIFTER_SLOT,
                                                        KickerSettings.FRONT_SERVO_SHIFTER_CHANNEL, */
                                                        KickerSettings.FRONT_KICKER_DOG_SOLENOID_SLOT,
                                                        KickerSettings.FRONT_KICKER_DOG_SOLENOID_CHANNEL,
                                                        KickerSettings.FRONT_MAE_SLOT,
                                                        KickerSettings.FRONT_MAE_CHANNEL,
                                                        KickerSettings.FRONT_REVERSE_MAE);

        backShifterMAEGroup  = new ShifterMAEGroup( /*  KickerSettings.BACK_SERVO_SHIFTER_SLOT,
                                                        KickerSettings.BACK_SERVO_SHIFTER_CHANNEL, */
                                                        KickerSettings.BACK_KICKER_DOG_SOLENOID_SLOT,
                                                        KickerSettings.BACK_KICKER_DOG_SOLENOID_CHANNEL,
                                                        KickerSettings.BACK_MAE_SLOT,
                                                        KickerSettings.BACK_MAE_CHANNEL,
                                                        KickerSettings.BACK_REVERSE_MAE);
        //dogSolenoid = new Solenoid(KickerSettings.FRONT_KICKER_DOG_SOLENOID_SLOT, KickerSettings.FRONT_KICKER_DOG_SOLENOID_CHANNEL);
        //engageFrontDog();
        if(Team948Robot.GLOBAL_REVERSE)
        {
            frontShifterMAEGroup.disengageDog();
            backShifterMAEGroup.engageDog();
            this.m_reverseWindingMotor = -KickerSettings.WINDER_REVERSE;
        }
        else
        {
            frontShifterMAEGroup.engageDog();
            backShifterMAEGroup.disengageDog();
            this.m_reverseWindingMotor = KickerSettings.WINDER_REVERSE;
        }
        this.reversed = Team948Robot.GLOBAL_REVERSE;
        windingMotor = new Jaguar(KickerSettings.WINDER_SLOT, KickerSettings.WINDER_CHANNEL);
        m_desiredKickerPosition = KickerSettings.KICKER_RELAXED_POSITION;
        m_pidEnabled = false;
        kP = KickerSettings.WINDER_KP;
        kI = KickerSettings.WINDER_KI;
        kD = KickerSettings.WINDER_KD;
        this.m_error = 0;
        this.m_totalError = 0;
        this.numberOfZeroPositionLoops = 0;
    }

    public ShifterMAEGroup getFrontShifterMAEGroup()
    {
        return this.frontShifterMAEGroup;
    }

    public void resetFrontKickerToKICKER_MIN_PWR_LVL_FROM_COMPLETELY_RELAXED_ZERO()
    {
        long startTimeInMillis = System.currentTimeMillis(); //Log the command's start time

        Team948Robot.kicker.frontShifterMAEGroup.disengageDog();  //Allow the kicker leg to release to the fully-relaxed position

        while(System.currentTimeMillis() < startTimeInMillis + KickerSettings.PIT_KICKER_RESET_ZERO_DELAY) {} //Wait ~1 second for the kicker leg to reach "zero"
        
        Team948Robot.kicker.frontShifterMAEGroup.magAbsEnc.zero(); //I'll give you 3 guesses to figure out what this does...

        Team948Robot.kicker.frontShifterMAEGroup.engageDog(); //Re-engage the kicker leg

        while(!isWoundAndReadyToFire())
        {
            Team948Robot.kicker.windTo(KickerSettings.KICKER_MIN_PWR_LVL); //Wind it up to the ball-possessing minimum
            Team948Robot.kicker.update(true);
            Watchdog.getInstance().feed(); //Winding the kicker will take a lot of time...enough that the watchdog might time-out before exiting this while loop (hence, we throw it a bone :P)
        }

        /*
         * The following lines of code essentially lock the driver out of controlling the robot...After all, this function is meant to be run only once!
         */
        /*while(true)
        {
            Watchdog.getInstance().feed();
            Team948Robot.drive.drive(0, 0);
            Team948Robot.possessor.setFrontPossessorState(Possessor.NEUTRAL);
            System.out.println("SHUT ME DOWN NOW (after I'm done moving :P!!!");
        }
         * 
         */

    }

    public ShifterMAEGroup getFrontMAE()
    {
        return frontShifterMAEGroup;
    }

    public ShifterMAEGroup getBackMAE()
    {
        return backShifterMAEGroup;
    }

    public void swapFrontAndBack()
    {
        //Swaps references to the front and back
        ShifterMAEGroup temp = this.frontShifterMAEGroup;
        frontShifterMAEGroup = this.backShifterMAEGroup;
        backShifterMAEGroup = temp;

        //Engages front's dog gear, disengages the back's
        frontShifterMAEGroup.engageDog();
        backShifterMAEGroup.disengageDog();

        //When we swap front and back, the direction we need to turn the motor to effect positive winding flips, too
        this.m_reverseWindingMotor = -this.m_reverseWindingMotor;

        this.numberOfZeroPositionLoops = 0;
        m_totalError = 0;
    }

    // This method will attempt to kick, regardless of whether it's wound up to where we want or not.
    // It also enforces the 2010 FIRST rule that if your kicker extends into the bumper zone, you must
    // wait two seconds after it retracts before you can kick again.
    public boolean kickFront(boolean useHowitzer)
    {
        long currentTime = System.currentTimeMillis();

        if(this.hasKickedBefore)
        {
            if(currentTime < this.timestampOfLastKick + KickerSettings.MIN_NUM_MILLISECONDS_BETWEEN_KICKS)
            {
                Debug.println(Debug.KICKER, "Kicker: Last kick was less than 2 seconds ago");
                return false; //Attempt to kick failed
            }
        }
        // We have either never kicked before, or it's been the requisite 2 seconds or more since the last kick
        this.hasKickedBefore = true;
        this.timestampOfLastKick = currentTime;
        this.actuallyKickFront(useHowitzer);
        return true;  //Returning 'true' indicates the the kicker fired
    }

    // Just prior to kicking, the robot will attempt to "pulse" its drivetrain straight backwards
    // so as to unjam the ball from the front possessor. It also briefly reverses the kick winder
    // motor to help break the friction on the dog release.
    public void actuallyKickFront(boolean useHowitzer)
    {
        if (useHowitzer)
        {
            int pulseTimer = KickerSettings.PULSE_TIME;
            double currentLeftDrive = JoystickHelper.getLeftDriveValue();
            double currentRightDrive = JoystickHelper.getRightDriveValue();
            while(pulseTimer > 0)
            {
                pulseTimer--;
                Team948Robot.drive.drivePID.calculate(currentLeftDrive - KickerSettings.PULSE_SPEED, currentRightDrive - KickerSettings.PULSE_SPEED, false);
                Team948Robot.possessor.setFrontPossessorState(Possessor.REPEL);
            }
        }
        Team948Robot.drive.drivePID.stop();
        delayMillis(100);
        // Ease up for a moment on the winding motor to make it easier for the dog to disengage...
        windingMotor.set(-0.25 * m_reverseWindingMotor);
        this.frontShifterMAEGroup.disengageDog();
        m_pidEnabled = false;
        delayMillis(KickerSettings.WINDER_EASE_DELAY);
        windingMotor.set(0.0);
    }

    public static void delayMillis(long msec)
    {
        long endTime = System.currentTimeMillis() + msec;
        while(System.currentTimeMillis() < endTime) 
        {
            Watchdog.getInstance().feed(); // Just wait...but don't forget to feed the watchdog!
        }
    }

    public void windTo(double angle)
    {
        this.m_desiredKickerPosition = MathHelper.clamp(angle, KickerSettings.KICKER_MIN_PWR_LVL, KickerSettings.KICKER_MAX_PWR_LVL);
        m_pidEnabled = true;
    }

    public boolean isWoundAndReadyToFire()
    {
        return(Math.abs(this.m_error) <= KickerSettings.KICKER_ANGLE_SETPOINT_TOLERANCE);
    }

    public void update(boolean isAutonomous)
    {
        long currentTime = System.currentTimeMillis();
        //Do we want to swap the front and back?
        if (NRGIO.getReverseManipulatorFront() != reversed)
        {
            reversed = !reversed;
            this.swapFrontAndBack();
            Debug.println(Debug.KICKER, "Kicker: swapping front/back...");
            m_angleMAE = 0.0;     // Don't try to read the MAE while swapping front/back
        }
        else
        {
            m_angleMAE = this.frontShifterMAEGroup.getMAE().getAngle();
            // Every time the dog is not engaged, reset the MAE relaxed position to account for slippage
            // (except don't reset if the kicker is still pulled back, which means the dog failed to release)
            if (!this.frontShifterMAEGroup.isEngaged() && m_angleMAE < 15.0)
                this.frontShifterMAEGroup.getMAE().zero();
        }

        //Check to see if the kicker leg is essentially at the 'zero' position...this is relevant for re-engaging the dog purposes...
        if (m_angleMAE <= (KickerSettings.KICKER_MIN_PWR_LVL - 20))
        {
            this.numberOfZeroPositionLoops++;
        }
        else
        {
            this.numberOfZeroPositionLoops = 0;
        }

        //If the kicker leg has been at the zero position for 5 loops, then it is reasonable to ASSUME that we have recently kicked and need to re-engage...
        if (this.numberOfZeroPositionLoops >= KickerSettings.NUM_LOOPS_TO_WAIT_AFTER_KICKING_AND_BEFORE_REWINDING)
        {
            this.m_pidEnabled = true;
            this.frontShifterMAEGroup.engageDog();
            //engageFrontDog();
            Debug.println(Debug.KICKER, "Kicker: re-engaged front dog...");
            numberOfZeroPositionLoops = 0;
            //m_totalError = 0;     // REVIEW: this seems to let motor stall out
        }

        //If the dog has been disengaged for 1.5 seconds, then it is reasonable to assume the zero-MAE if-clause above has failed and we need to rewind
        if((currentTime >= this.timestampOfLastKick + KickerSettings.MAX_TIME_BEFORE_TIMER_FORCED_REWINDING) && this.hasKickedBefore)
        {
            this.m_pidEnabled = true;
            this.frontShifterMAEGroup.engageDog();
            Debug.println(Debug.KICKER, "Kicker: re-engaged front dog due to timer over-ride");
            numberOfZeroPositionLoops = 0;
        }

        double desiredAngle = m_desiredKickerPosition;
        if (!isAutonomous)
        {
            //Update the setpoint being given by the manipulator driver...
            desiredAngle = this.getDesiredKickerAngle();
        }
        Debug.print(Debug.KICKER, "Kicker: windto: " + MathHelper.round2(desiredAngle));
        this.windTo(desiredAngle);

        //Kick?
        if (NRGIO.getKickButton() || JoystickHelper.getKickBtn())
        {
            Debug.println(Debug.KICKER, "Kicker: calling kickFront()...");
            this.kickFront(!isAutonomous);
        }

        //PID Code
        if (m_pidEnabled)
        {
            //POSITIVE error means that the setpoint is "more wound-up" than the current position
            m_error = m_desiredKickerPosition - m_angleMAE;
            // Don't let the integral term contribute more than 50% motor power (prevents big integral windups)
            if (Math.abs(kI * (m_totalError + m_error)) < 0.5)
                m_totalError += m_error;
            double pidOut = kP * m_error + kI * m_totalError;
            //Reverse the winding motor output as appropriate...
            pidOut *= this.m_reverseWindingMotor;
            // Stop winding the kicker when we're within a couple percent of the setpoint so we don't burn out the motor
            if (this.isWoundAndReadyToFire())
                pidOut = 0.0;
            //Let that beezy fly!
            this.windingMotor.set(pidOut);
            if (Debug.KICKER)
            {
                String s = " MAE=" + MathHelper.round1(m_angleMAE)
                         + " err=" + MathHelper.round2(m_error)
                         + " totErr=" + MathHelper.round2(m_totalError)
                         + " pidOut=" + MathHelper.round2(pidOut);
                Debug.println(Debug.KICKER, s);
            }
        }

        if(JoystickHelper.getKickerLegResetFOR_PIT_USE_ONLY())
        {
            resetFrontKickerToKICKER_MIN_PWR_LVL_FROM_COMPLETELY_RELAXED_ZERO();
        }
    }

    public double getDesiredKickerAngle()
    {
        //This method gets the potentiometer's raw value and converts it into a real, usable angular position for the kicker
        double kickStrength = NRGIO.getKickStrengthPotentiometer(); // = JoystickHelper.getKickStrength());
        kickStrength = Math.max(kickStrength, JoystickHelper.getKickStrength());
        return (kickStrength * (KickerSettings.KICKER_MAX_PWR_LVL - KickerSettings.KICKER_MIN_PWR_LVL)/IOSettings.KICK_STRENGTH_METER_MAX_VOLTAGE + KickerSettings.KICKER_MIN_PWR_LVL);
    }

    public double getActualKickerAngle()
    {
        return m_angleMAE;
    }

    public class ShifterMAEGroup
    {
        /*
         * THERE IS METHOD TO THIS MADNESS!
         * I need to be able to change which MAE and Shifter group is in the front/back...so I pair them up and swap the references! :=D
         */

        private MAE magAbsEnc;
        private Solenoid dogSolenoid;
        //private Servo servo;
        private boolean dogIsEngaged;

        //public ShifterMAEGroup(int servoSlot, int servoChannel, int maeSlot, int maeChannel, boolean reverse)
        public ShifterMAEGroup(int solenoidSlot, int solenoidChannel, int maeSlot, int maeChannel, boolean reverse)
        {
            magAbsEnc = new MAE(maeSlot, maeChannel, reverse);
            magAbsEnc.resetKickerAngleTo_KICKER_MIN_PWR_LVL_fromCompletelyRelaxedZero();

            //servo = new Servo(servoSlot, servoChannel);
            //servo.setAngle(kDisengagedServoAngle);
            dogSolenoid = new Solenoid(solenoidSlot, solenoidChannel);
            engageDog();
        }

        public void engageDog()
        {
            //servo.setAngle(kEngagedServoAngle);
            dogIsEngaged = true;
            dogSolenoid.set(!KickerSettings.REVERSE_DOG_ACTUATORS);
        }

        public void disengageDog()
        {
            //servo.setAngle(kDisengagedServoAngle);
            dogIsEngaged = false;
            dogSolenoid.set(KickerSettings.REVERSE_DOG_ACTUATORS);
        }

        public void toggleDog()
        {
            if(dogIsEngaged)
            {
                dogIsEngaged = false;
                //servo.setAngle(kDisengagedServoAngle);
            }
            else
            {
                dogIsEngaged = true;
                //servo.setAngle(kEngagedServoAngle);
            }
            dogSolenoid.set(KickerSettings.REVERSE_DOG_ACTUATORS ? !dogIsEngaged : dogIsEngaged);
        }

        public boolean isEngaged()
        {
            return this.dogIsEngaged;
        }

        public double currentKickerAngle()
        {
            return (magAbsEnc.getAngle());
        }

        public MAE getMAE()
        {
            return this.magAbsEnc;
        }
    }
}
