package nrgrobot;

import nrgrobot.cmd.*;

/**
 * Old autonomous command lists as they were before we introduced class CommandList which allows us
 * to make "helper method lists" that consolidate duplicate sections of command arrays.
 *
 * @author Kenneth, Austin, Brian, Dustin, Paul
 */
public class AutonomousOld
{
    public static final double X_TARGET = 26.0;                    // x coord of autonomous target goal
    public static final double Y_TARGET_NEAR_FIELD = 198.0;        // y coord of goal from the near field
    public static final double Y_TARGET_MID_FIELD  = 34 * 12 + 4;  // y coord of goal from the middle field
    public static final double Y_TARGET_FAR_FIELD  = 54 * 12;      // y coord of goal from the far field

    public static final double X_CENTER_LINE = 13.5 * 12;   // x coord of center line (13.5 feet from the left field edge)

    // Note that these are not linear because of the need to bounce the ball precisely between the bumps
    // If we could shoot from the far field to the goal without bouncing in between, they could be linear.
    private static final double KICK_POWER1 = 30.0;         // near field, row closest to goal
    private static final double KICK_POWER2 = 35.0;         // near field, middle row
    private static final double KICK_POWER3 = 40.0;         // near field, row furthest from goal
    private static final double KICK_POWER4 = 70.0;         // mid field, row closest to goal
    private static final double KICK_POWER5 = 80.0;         // mid field, middle row
    private static final double KICK_POWER6 = 90.0;         // mid field, row furthest from goal
    private static final double KICK_POWER7 = 70.0;         // far field, row closed to goal
    private static final double KICK_POWER8 = 85.0;         // far field, middle row
    private static final double KICK_POWER9 = 100.0;        // far field, row furthest from goal

    private static final double ROW_SPACING  = 36.0;        // spacing between each dot row
    private static final double COL_SPACING  = 36.0;        // spacing between each dot column
    private static final double X_COL1  = 45.0;
    private static final double X_COL2  = X_COL1 + COL_SPACING;
    private static final double X_COL3  = X_COL2 + COL_SPACING;
    private static final double Y_START = AutonomousSettings.ROBOT_LENGTH/2;  // y coord of the robot's center
    private static final double BUMP_TO_ROW1 = 52.0 - Y_START + 2;  // +2 is so we fully capture the ball
    private static final double BUMP_TO_ROW2 = BUMP_TO_ROW1 + ROW_SPACING;
    private static final double BUMP_TO_ROW3 = BUMP_TO_ROW2 + ROW_SPACING;


    //empty commands
    public static CommandBase[] cmdsEmpty = {};

    // Commands for nearField, dotsRow 1, one ball on 2nd dot straight ahead
    public static CommandBase[] cmdsNearFieldDotsRow1 =
    {
        new SetPositionCommand(X_COL1, Y_START),
        new WindCommand(KICK_POWER2),
        new DistanceMove(BUMP_TO_ROW2, 0.8),
        new TurnTowardCommand(X_TARGET, Y_TARGET_NEAR_FIELD),
        new KickCommand(),
        new TurnToHeadingCommand(90),
        new SetGearCommand(SetGearCommand.HIGH_GEAR),
        new DistanceMove(100 , 1.0),
        new SetGearCommand(SetGearCommand.LOW_GEAR),
    };

    public static CommandBase[] cmdsNearFieldDotsRow2 =
    {
        new WindCommand(KICK_POWER2),
        new SetPositionCommand(X_COL2, Y_START),
        new DistanceMove(BUMP_TO_ROW2, 0.8),
        //CenterOnTargetCommand(),
        new TurnTowardCommand(X_TARGET, Y_TARGET_NEAR_FIELD),
        new KickCommand(),
        new TurnToHeadingCommand(90),
        new DistanceMove(100-36 , 1.0)
    };

    public static CommandBase[] cmdsNearFieldDotsRow3 =
    {
        new SetPositionCommand(42 + 72, Y_START),
        new WindCommand(50.0),
        new DistanceMove(52 - 13 + 36, 0.8),
        new TurnTowardCommand(X_TARGET, Y_TARGET_NEAR_FIELD),
        new KickCommand(),
        new TurnToHeadingCommand(90),
        new DistanceMove(100-72 , 1.0)
    };

    // Commands for midField, dotRows 1-3, two balls straight ahead
    public static CommandBase[] cmdsMidFieldDotsRow1 =
    {
        new SetPositionCommand(X_COL1, Y_START),
        new WindCommand(KICK_POWER5),
        new PossessorMode(Possessor.POSSESS),
        new WaitCommand(500),
        new DistanceMove(BUMP_TO_ROW2, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new PossessorMode(Possessor.REPEL),
        new KickCommand(),
        new PossessorMode(Possessor.POSSESS),
        new TurnToHeadingCommand(0),
        new WindCommand(KICK_POWER4),
        new DistanceMove(ROW_SPACING, 1.0),
        new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new PossessorMode(Possessor.REPEL),
        new KickCommand(),
        new PossessorMode(Possessor.POSSESS),
        new TurnToHeadingCommand(90),
        new DistanceMove(94, 1.0)
    };

    /*
     * Setup notes for using this autonomous routine:
     *   -Put the balls in a line, in the middle column, on the two consecutive dots farthest from the driver's station
     *   -Set up the robot ~4 inches to IT'S RIGHT (driver's left) of the column of balls
     *   -As always, make sure the robot is positioned and oriented BEFORE turning it on
     *   -Pneumatics have to be charged, too, as usual. :)
     */
    public static CommandBase[] cmdsMidFieldDotsRow2 =
    {
        new SetPositionCommand(X_COL2, Y_START),
        new WindCommand(KICK_POWER5),
        new WaitCommand(1000),
        new DistanceMove(BUMP_TO_ROW2, 0.8), //(71)
        new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new KickCommand(),
        new TurnToHeadingCommand(0),
        new WindCommand(KICK_POWER4),
        new DistanceMove(ROW_SPACING, 0.8),
        new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new KickCommand(),
        new TurnToHeadingCommand(90),
        new DistanceMove(100-36, 1.0)
    };

    public static CommandBase[] cmdsMidFieldDotsRow3 =
    {
        new SetPositionCommand(X_COL3, Y_START),
        new WindCommand(KICK_POWER5),
        new WaitCommand(1000),
        new DistanceMove(BUMP_TO_ROW2, 1.0), //(65)
        new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new KickCommand(),
        new TurnToHeadingCommand(0),
        new WindCommand(KICK_POWER4),
        new DistanceMove(ROW_SPACING, 1.0),
        new TurnTowardCommand(X_TARGET, Y_TARGET_MID_FIELD),
        new KickCommand(),
        new TurnToHeadingCommand(90),
        new DistanceMove(100-72, 1.0)
    };

    // Commands for farField, dotsRow 1-3, three balls straight ahead
    public static CommandBase[] cmdsFarFieldDotsRow1 =
    {
        new SetPositionCommand(X_COL1, Y_START),
        new WindCommand(KICK_POWER9),
        new WaitCommand(500),
        new DistanceMove(BUMP_TO_ROW1, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new KickCommand(),
        new WindCommand(KICK_POWER8),
        new TurnToHeadingCommand(0),
        new DistanceMove(36, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new KickCommand(),
        new WindCommand(KICK_POWER7),
        new DistanceMove(36, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new KickCommand(),
        new WindCommand(KICK_POWER7),
        new TurnToHeadingCommand(90),
        new DistanceMove(42 + 72, 1.0), // Go over to the centerline
        new TurnToHeadingCommand(0),    // Turn toward the tunnel
        new DistanceMove(75, 1.0)       // Drive partway into tunnel
    };

    public static CommandBase[] cmdsFarFieldDotsRow2 =
    {
        new SetPositionCommand(X_COL2, Y_START),
        new WindCommand(KICK_POWER9),
        new WaitCommand(500),
        new DistanceMove(BUMP_TO_ROW1, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new KickCommand(),
        new WindCommand(KICK_POWER8),
        new TurnToHeadingCommand(0),
        new DistanceMove(36, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new KickCommand(),
        new WindCommand(KICK_POWER7),
        new TurnToHeadingCommand(0),
        new DistanceMove(36, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new KickCommand(),
        new WindCommand(KICK_POWER7),
        new TurnToHeadingCommand(90),
        new DistanceMove(42 + 36, 1.0), // Go over to the centerline
        new TurnToHeadingCommand(0),    // Turn toward the tunnel
        new DistanceMove(75, 1.0)       // Drive partway into tunnel
    };

    public static CommandBase[] cmdsFarFieldDotsRow3 =
    {
        new SetPositionCommand(X_COL3, Y_START),
        new PossessorMode(Possessor.POSSESS),
        new WindCommand(KICK_POWER9),
        new WaitCommand(500),
        new DistanceMove(BUMP_TO_ROW1, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        //new PossessorMode(Possessor.REPEL),
        new KickCommand(),
        new PossessorMode(Possessor.POSSESS),
        new WindCommand(KICK_POWER8),
        new WaitCommand(500),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new KickCommand(),
        new WindCommand(KICK_POWER7),
        new WaitCommand(500),
        new TurnToHeadingCommand(0),
        new DistanceMove(ROW_SPACING, 0.5),
        new TurnTowardCommand(X_TARGET, Y_TARGET_FAR_FIELD),
        new KickCommand(),
        new WindCommand(KICK_POWER7),
        new TurnToHeadingCommand(90),
        new DistanceMove(42, 1.0),      // Go over to the centerline
        new TurnToHeadingCommand(0),    // Turn toward the tunnel
        new DistanceMove(75, 1.0)       // Drive partway into tunnel
    };
}
