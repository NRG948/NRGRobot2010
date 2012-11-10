package nrgrobot.camera;

import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.*;
import nrgrobot.*;

/**
 * NRGCamera gets an instance of the AxisCamera, and allows the camera to track the target using the attached servos
 * @author Paul, Stephen, Austin, Kenneth, Andrew, Dustin
 */
//TODO: Add an actual horizantal servo, figure out slot, channel, angle. Then put in camera settings
public class NRGCamera
{
    private static boolean targetFound;
    private static AxisCamera cam;
    //private Servo horizontalServo;
    private Servo verticalServo;
    //private double horizontalServoAngle = CameraSettings.HORIZ_SERVO_ANGLE;
    private static double verticalServoAngle = CameraSettings.VERT_SERVO_ANGLE;
    private static double horizontalTargetPosition;
    private static double distanceFromTarget;
    public static final double SERVO_ANGLE_MIN = 0.0;
    public static final double SERVO_ANGLE_MAX = 1.0;
    
    public NRGCamera()
    {
        cam = AxisCamera.getInstance();
        cam.writeResolution(CameraSettings.CAMERA_RESOLUTION);
        cam.writeBrightness(CameraSettings.CAMERA_BRIGHTNESS);
        /*cam.writeExposureControl(CameraSettings.CAMERA_EXPOSURE);
        cam.writeExposurePriority(CameraSettings.CAMERA_EXPOSURE_PRIORITY);
        cam.writeWhiteBalance(CameraSettings.CAMERA_WHITE_BALANCE);
        cam.writeRotation(CameraSettings.CAMERA_ROTATION);
        cam.writeCompression(CameraSettings.CAMERA_COMPRESSION);
        cam.writeColorLevel(CameraSettings.CAMERA_COLOR_LEVEL)*/

        verticalServo = new Servo(CameraSettings.VERT_SERVO_SLOT, CameraSettings.VERT_SERVO_CHANNEL);
        verticalServo.set(verticalServoAngle);
        //horizontalServo = new Servo(CameraSettings.HORIZ_SERVO_SLOT, CameraSettings.HORIZ_SERVO_CHANNEL);
        //horizontalServo.set(horizontalServoAngle);
        targetFound = false;
    }
    
    public void update()
    {
        targetFound = false;
        try
        {
            if (cam.freshImage())
            {// && turnController.onTarget()) {
                if(JoystickHelper.getTrackingThresholdIncrement())
                {
                    Target.incrementThreshold();
                }
                if(JoystickHelper.getTrackingThresholdDecrement())
                {
                    Target.decrementThreshold();
                }
		if(JoystickHelper.getCameraResetBtn())
		{
		    this.resetServo();
		}
                long time1 = System.currentTimeMillis();
                ColorImage image = cam.getImage();
                time1 = System.currentTimeMillis() - time1;
                Watchdog.getInstance().feed();
                Thread.yield();
                long time2 = System.currentTimeMillis();
                Target[] targets = Target.findCircularTargets(image);
                Watchdog.getInstance().feed();
                time2 = System.currentTimeMillis() - time2;
                //Prints out the times for picture grabbing and processing, respectively, and then the current edge threshold
                LCD.println2(time1 + "ms " + time2 + "ms thrsh:" + Target.k_thresholdSettings);
                Thread.yield();
                Watchdog.getInstance().feed();
                image.free();
                Watchdog.getInstance().feed();
                if (targets.length == 0 || targets[0].m_score < CameraSettings.TARGET_SCORE_THRESHOLD)
                {
                    Debug.println(Debug.CAMERA, "No target found");
                    LCD.println6("Cam: No Targets Found" );
		    NRGIO.setTargetLeftLED(false);
		    NRGIO.setTargetCenterLED(false);
		    NRGIO.setTargetRightLED(false);
                    Target[] newTargets = new Target[targets.length + 1];
                    newTargets[0] = new Target();
                    newTargets[0].m_majorRadius = 0;
                    newTargets[0].m_minorRadius = 0;
                    newTargets[0].m_score = 0;
                    Watchdog.getInstance().feed();
                    for (int i = 0; i < targets.length; i++)
                    {
                        newTargets[i + 1] = targets[i];
                    }
                    targetFound = false;
                    horizontalTargetPosition = 0.0;

                    // REVIEW: If no target found, just set the camera angle(s) to default positions
                    resetServo();
                    // Pan the camera somewhat randomly
                    /*horizontalServoAngle += Math.cos(System.currentTimeMillis() / 2000);
                    horizontalServoAngle = MathHelper.clamp(horizontalServoAngle, SERVO_ANGLE_MIN, SERVO_ANGLE_MAX);
                    horizontalServo.set(horizontalServoAngle);
                    verticalServoAngle += Math.sin(System.currentTimeMillis() / 1800);
                    verticalServoAngle = MathHelper.clamp(verticalServoAngle, SERVO_ANGLE_MIN, SERVO_ANGLE_MAX);
                    verticalServo.set(verticalServoAngle);*/
                }
                else
                {
                    Watchdog.getInstance().feed();
                    verticalServoAngle += CameraSettings.VERT_SERVO_ERROR_ADJUST * targets[0].m_yPos;
                    verticalServoAngle = MathHelper.clamp(verticalServoAngle, SERVO_ANGLE_MIN, SERVO_ANGLE_MAX);
                    verticalServo.set(verticalServoAngle);
                    horizontalTargetPosition = targets[0].m_xPos;
                    Debug.println(Debug.CAMERA, "Vert Servo Angle: " + verticalServoAngle);
                    //horizontalServoAngle += CameraSettings.HORIZ_SERVO_ERROR_ADJUST * targets[0].m_xPos;
                    //horizontalServoAngle = MathHelper.clamp(horizontalServoAngle, SERVO_ANGLE_MIN, SERVO_ANGLE_MAX);
                    //horizontalServo.set(horizontalServoAngle);
                    distanceFromTarget = CameraSettings.TARGET_DISTANCE_SCALE / targets[0].m_majorRadius + CameraSettings.TARGET_DISTANCE_CONSTANT;
                    Debug.println(Debug.CAMERA, "Major Radius: " + MathHelper.round2(targets[0].m_majorRadius) + "  Distance: " + distanceFromTarget);

		    // update LEDs on the driver station used for aiming the camera to the target
		    boolean centered = Math.abs(targets[0].m_xPos) < targets[0].m_minorRadius * CameraSettings.CAMERA_CENTER_THRESHOLD;
		    NRGIO.setTargetCenterLED(centered);
		    NRGIO.setTargetLeftLED(!centered && targets[0].m_xPos < 0);
		    NRGIO.setTargetRightLED(!centered && targets[0].m_xPos > 0);

                    //LCD.println3("Pos X: " + targets[0].m_xPos );
                    //LCD.println4("Pos Y: " + targets[0].m_yPos );
                    LCD.println6("Cam: " + targets.length + " target(s)");
                    //LCD.println6("Servo Y: " + verticalServoAngle);
                    Watchdog.getInstance().feed();
                    targetFound = true;

                }
            }
        }
        catch (NIVisionException ex)
        {
            LCD.println6("Cam: " + ex.getMessage());
        }
        catch (AxisCameraException ex)
        {
            LCD.println6("Cam: " + ex.getMessage());
        }
    }

    public void resetServo()
    {
	//horizontalServoAngle = CameraSettings.HORIZ_SERVO_ANGLE;
	verticalServoAngle = CameraSettings.VERT_SERVO_ANGLE;
	verticalServo.set(verticalServoAngle);
	//horizontalServo.set(horizontalServoAngle);
    }

    /*public double getHorizontalServoAngle()
    {
        return horizontalServoAngle;
    }*/

    /**
     * Gets the position of the target horizontally during the last update loop.
     * @return a negative value if the target is left of center, positive if to
     * the right, or zero if the target is centered or not found.
     */
    public static double getHorizontalTargetPosition()
    {
        return horizontalTargetPosition;
    }

    public static double getVerticalServoAngle()
    {
        return verticalServoAngle;
    }

    public static boolean getTargetFound()
    {
        return targetFound;
    }

    public static double getDistanceFromTarget()
    {
        if(targetFound)
            return distanceFromTarget;
        return 0;
    }

    public String toString()
    {
        return cam.toString();
    }
}