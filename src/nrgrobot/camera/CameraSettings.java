package nrgrobot.camera;

import edu.wpi.first.wpilibj.camera.AxisCamera.*;

/**
 * CameraSettings.java provides values for camera vision settings, as well as slot, channel, and angle values for the servos controlling the camera
 * @author Andrew, Stephen, Kenneth, Austin
 */
public class CameraSettings
{
    public static final ResolutionT CAMERA_RESOLUTION = ResolutionT.k160x120;
    public static final int CAMERA_BRIGHTNESS = 0;
    public static final ExposureT CAMERA_EXPOSURE = ExposureT.automatic;
    public static final ExposurePriorityT CAMERA_EXPOSURE_PRIORITY = ExposurePriorityT.frameRate;
    public static final WhiteBalanceT CAMERA_WHITE_BALANCE = WhiteBalanceT.automatic;
    public static final RotationT CAMERA_ROTATION = RotationT.k0;
    public static final int CAMERA_COMPRESSION = 50;
    public static final int CAMERA_COLOR_LEVEL = 50;
    public static final double TARGET_SCORE_THRESHOLD = .01;
    public static final int VERT_SERVO_SLOT = 4;
    public static final int VERT_SERVO_CHANNEL = 9;
    public static final double VERT_SERVO_ANGLE = 0.5;
    public static final double VERT_SERVO_ERROR_ADJUST = 8.0 / 180;
    public static final int  HORIZ_SERVO_SLOT = 4;
    public static final int  HORIZ_SERVO_CHANNEL = 10;
    public static final double HORIZ_SERVO_ANGLE = 0.5;
    public static final double HORIZ_SERVO_ERROR_ADJUST = 8.0 / 180;

    // The scale factor to convert height of target to distance
    public static final double TARGET_DISTANCE_SCALE = .0547;
    public static final double TARGET_DISTANCE_CONSTANT = .8266;

    public static final int TARGET_THRESHOLD_STARTING_VALUE = 80;
    public static final int TARGET_THRESHOLD_MIN_VALUE = 40;
    
    // How close the Target must be to center for the Target Centered LED to be lit
    public static final double CAMERA_CENTER_THRESHOLD = 0.6;

    public static final double CAMERA_DEGREES_PER_HORIZONTAL_TRACKING_UNIT = 5.0;
}
