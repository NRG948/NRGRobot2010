package nrgrobot;

import edu.wpi.first.wpilibj.*;

/**
 * SharpIRSensor.java creates a sensor for tracking the ball on the field
 * @author Stephen
 */
public class SharpIRSensor extends SensorBase
{
    // range values in centimeters

    private double minRange;
    private double maxRange;
    // channel sensor is plugged into
    private AnalogChannel m_analogChannel;
    //private boolean m_allocatedChannel;
    private double m_calibrationConstant;

    //Constructors
    public SharpIRSensor(int sensorChannel) // Default constructor calling values from analog settings class
    {
        // m_allocatedChannel = true;
        minRange = AnalogSettings.SHARP_SENSOR_DISTANCE_MIN;
        maxRange = AnalogSettings.SHARP_SENSOR_DISTANCE_MAX;
        m_analogChannel = new AnalogChannel(AnalogSettings.SHARP_SENSOR_SLOT, sensorChannel);
    }

    /*public SharpIRSensor(double min, double max, int slot, int channel)//Constructor for user defined values
    {
    // m_allocatedChannel = true;
    minRange = min;
    maxRange = max;
    m_analogChannel = new AnalogChannel(slot, channel);
    }*/
    //Matt told me to comment this out
    /*
    protected void free()
    {
    if (m_analogChannel != null && m_allocatedChannel)
    {
    m_analogChannel.free();
    }
    m_analogChannel = null;
    }
     */
    public double getDistance() // Returns the distance to the ball
    {
        double averageVoltage = m_analogChannel.getAverageVoltage();
        //Debug.print(Debug.IRSENSOR, "IR Voltage: " + MathHelper.round2(averageVoltage) + "   ");
        //double distanceToBall = ((1.0d / averageVoltage) - m_calibrationConstant) * AnalogSettings.CENTIMETERS_PER_INCH;

        // Regression equation calculated in Excel using sample data
        double distanceToBall = 5.22 / averageVoltage + 0.368 - .6; // convert voltage to distance in inches
        if (distanceToBall < minRange || distanceToBall > maxRange)
            distanceToBall = 0.0;
        //Debug.println(Debug.IRSENSOR, "Distance: " + MathHelper.round1(distanceToBall));
        return distanceToBall;


    }
}

