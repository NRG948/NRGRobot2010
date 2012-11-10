package nrgrobot;

import edu.wpi.first.wpilibj.*;
/**
 * Sensors.java makes static references to the different sensors, encoders and gyros, used in the NRG Drive class.
 * @author Stephen and Kenneth
 */
public class Sensors
{
    public static Encoder leftEncoder;
    public static Encoder rightEncoder;
    public static Gyro gyro;
    public static AnalogChannel gyroTemp;
    public static IRSensorSet iRSensorsFront, iRSensorsBack;

    public static void initLeftEncoder()
    {
	leftEncoder = new Encoder(DriveSettings.DRIVE_ENCODER_SLOT, DriveSettings.LEFT_DRIVE_ENCODER_CHANNEL_A,
		DriveSettings.DRIVE_ENCODER_SLOT, DriveSettings.LEFT_DRIVE_ENCODER_CHANNEL_B,
		DriveSettings.DRIVE_ENCODER_REVERSE);
	leftEncoder.setMinRate(DriveSettings.DRIVE_ENCODER_MIN_RATE);
	leftEncoder.setDistancePerPulse(-1.0 / DriveSettings.DRIVE_ENCODER_PULSES_PER_INCH);
	leftEncoder.start();
    }

    public static void initRightEncoder()
    {
	rightEncoder = new Encoder(DriveSettings.DRIVE_ENCODER_SLOT, DriveSettings.RIGHT_DRIVE_ENCODER_CHANNEL_A,
		DriveSettings.DRIVE_ENCODER_SLOT, DriveSettings.RIGHT_DRIVE_ENCODER_CHANNEL_B,
		DriveSettings.DRIVE_ENCODER_REVERSE);
	rightEncoder.setMinRate(DriveSettings.DRIVE_ENCODER_MIN_RATE);
	rightEncoder.setDistancePerPulse(1.0 / DriveSettings.DRIVE_ENCODER_PULSES_PER_INCH);
	rightEncoder.start();
    }

    public static void initGyro()
    {
	gyro = new Gyro(DriveSettings.GYRO_SLOT, DriveSettings.GYRO_DATA_CHANNEL);
	gyroTemp = new AnalogChannel(DriveSettings.GYRO_SLOT, DriveSettings.GYRO_TEMP_CHANNEL);
	gyro.setSensitivity(DriveSettings.GYRO_SENSITIVITY);
	gyro.reset();
    }

    public static void initIRSensorSets()
    {
	iRSensorsFront = new IRSensorSet(AnalogSettings.SHARP_SENSOR_FRONT_LEFT_CHANNEL,
					 // AnalogSettings.SHARP_SENSOR_FRONT_MIDDLE_CHANNEL,
					 AnalogSettings.SHARP_SENSOR_FRONT_RIGHT_CHANNEL);
	/*iRSensorsBack = new IRSensorSet(AnalogSettings.SHARP_SENSOR_BACK_LEFT_CHANNEL,
					 // AnalogSettings.SHARP_SENSOR_BACK_MIDDLE_CHANNEL,
					 AnalogSettings.SHARP_SENSOR_BACK_RIGHT_CHANNEL); */
    }
    
    public static double iR1Distance(AnalogChannel m_Analog)
    {
        double averageVoltage = m_Analog.getAverageVoltage();
        double distanceToBall = 5.22 / averageVoltage + 0.368 - .6; // convert voltage to distance in inches
        if (distanceToBall < AnalogSettings.SHARP_SENSOR_DISTANCE_MIN || distanceToBall > AnalogSettings.SHARP_SENSOR_DISTANCE_MAX)
            distanceToBall = 0.0;
        return distanceToBall;
    }

    public static double iR2Distance(AnalogChannel m_Analog)
    {
        double averageVoltage = m_Analog.getAverageVoltage();
        double distanceToBall = ((5.22 / averageVoltage + 0.368 - .6) + 1.126) / .9788; // convert voltage to distance in inches
        if (distanceToBall < AnalogSettings.SHARP_SENSOR_DISTANCE_MIN || distanceToBall > AnalogSettings.SHARP_SENSOR_DISTANCE_MAX)
            distanceToBall = 0.0;
        return distanceToBall;
    }
}
