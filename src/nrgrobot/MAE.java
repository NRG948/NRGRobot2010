package nrgrobot;
import edu.wpi.first.wpilibj.*;

/**
 * MAE stands for Magnetic Accelerometer Encoder.  Measures angle of deflection of a kicker bar.
 * @author compaq, MANDERSON, PDAVIS
 */

public class MAE extends SensorBase
{
    private AnalogChannel m_analog;
    private double m_rawAngle;
    private double m_offset;
    private boolean m_reverse;
    private static final double VOLTS_TO_DEGREES = 72.0d * 360.0/366.1;
    
    public MAE (int slot, int channel, boolean reverse)
    {
        m_analog = new AnalogChannel(slot, channel);
        m_rawAngle = 0.0;
        m_offset = 0.0;
        m_reverse = reverse;
    }

    /* getRawAngle() returns the raw angle indicated by a kicker MAE (note that we invert the
     * angle if m_reverse is set, so that increasing angles always mean more kick power no
     * matter which way the MAE is oriented). Output shall ALWAYS be between 0 and 360 degrees.
     */
    private double getRawAngle()
    {
        if (m_analog != null)
        {
            double volts = m_analog.getAverageVoltage();
            // This test was added because we were getting spurious zero volt samples once in
            // a while. So if this happens, we just return the last 'good' angle we sampled.
            if (volts < 0.1)
                return m_rawAngle;
            m_rawAngle = volts * VOLTS_TO_DEGREES;
            if (this.m_reverse)
                m_rawAngle = 360.0 - m_rawAngle;
            Debug.print(Debug.MAE, "  " + MathHelper.round2(volts) + "V = " + MathHelper.round1(m_rawAngle) + "deg  ");
        }
        return m_rawAngle;
    }

    /* getAngle returns the zero-based angle of the kicker (zero is fully relaxed, wound-up angles are always positive)
     */
    public double getAngle()
    {
        double angle = getRawAngle() - m_offset;
        Debug.println(Debug.MAE, "m_offset=" + MathHelper.round1(m_offset) + " angle=" + MathHelper.round1(angle));
        /* Note: we hand adjust the MAEs so they operate in the approximate middle of their range so we don't have
         * to deal with any mathematical weirdness and electrical noise when they wrap around from 360 to 0 degrees.
         * The kicker should never move more than about 120 degrees, so this test corrects for the case
         * where MAE slippage results in "negative" or wrap-around angle values.
        if (angle > 355.0 || angle < -1.0)
        {
            zero();
            angle = 0.0;
        } */
        return angle;
    }

    // This method is intended to be run whenever we need to reset the MAE relaxed angle back to zero.
    // Don't call this method if the kicker dog is engaged!
    public void zero()
    {
        m_offset = this.getRawAngle();
    }

    /* This method is necessary because we want our kicker to extend beyond the robot frame during its
     * kick follow-through, but the FIRST rules state that the kicker must be within the robot frame at
     * the start of the autonomous period. So we pre-wind the kicker before the match to its MIN_PWR_LVL.
     * This method performs a one-time adjustment to m_offset to compensate for the fact that we don't
     * start with the kicker in the relaxed position. After the first kick, it's no longer needed.
     */
    public void resetKickerAngleTo_KICKER_MIN_PWR_LVL_fromCompletelyRelaxedZero()
    {
        zero();
        m_offset -= KickerSettings.KICKER_MIN_PWR_LVL;
    }
}
