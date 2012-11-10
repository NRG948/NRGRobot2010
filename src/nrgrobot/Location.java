package nrgrobot;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Paul, Dustin
 */
public class Location
{
    private int fieldLocation;
    private int dotsColLocation;

    private DigitalInput switchNearField;
    private DigitalInput switchMidField;
    private DigitalInput switchFarField;
    private DigitalInput switchDotsCol1;
    private DigitalInput switchDotsCol2;
    private DigitalInput switchDotsCol3;

    public static final int BAD_FIELD  = 0;
    public static final int NEAR_FIELD = 1;
    public static final int MID_FIELD  = 2;
    public static final int FAR_FIELD  = 3;

    public static final int DOTS_ROW_BAD = 0;
    public static final int DOTS_COL_1   = 1;
    public static final int DOTS_COL_2   = 2;
    public static final int DOTS_COL_3   = 3;

    private static final int NEAR_FIELD_SWITCH_CHANNEL = 13;
    private static final int MID_FIELD_SWITCH_CHANNEL  = 2;
    private static final int FAR_FIELD_SWITCH_CHANNEL  = 3;
    private static final int DOTS1_SWITCH_CHANNEL = 4;
    private static final int DOTS2_SWITCH_CHANNEL = 5;
    private static final int DOTS3_SWITCH_CHANNEL = 6;

    public Location()
    {
        // These constructors assume that all digital i/o channels are on slot 4
        switchNearField = new DigitalInput(NEAR_FIELD_SWITCH_CHANNEL);
        switchMidField  = new DigitalInput(MID_FIELD_SWITCH_CHANNEL);
        switchFarField  = new DigitalInput(FAR_FIELD_SWITCH_CHANNEL);
        switchDotsCol1  = new DigitalInput(DOTS1_SWITCH_CHANNEL);
        switchDotsCol2  = new DigitalInput(DOTS2_SWITCH_CHANNEL);
        switchDotsCol3  = new DigitalInput(DOTS3_SWITCH_CHANNEL);
        setLocationFromSwitches();
    }

    public int getFieldLocation()
    {
        return fieldLocation;
    }

    public int getDotsColLocation()
    {
        return dotsColLocation;
    }

    public void setLocation(int field, int dotsCol)
    {
        fieldLocation = field;
        dotsColLocation = dotsCol;
    }

    public void setLocationFromSwitches()
    {
        // switch.get() should return false when the switch is selected, true otherwise
        boolean nearField = !switchNearField.get();
        boolean midField  = !switchMidField.get();
        boolean farField  = !switchFarField.get();

        fieldLocation = BAD_FIELD;
        if ((nearField && midField) || (nearField && farField) || (midField && farField))
            Debug.println(Debug.LOCATION, "******* Error: multiple field switches are selected ******");
        else if (nearField)
            fieldLocation = NEAR_FIELD;
        else if (midField)
            fieldLocation = MID_FIELD;
        else if (farField)
            fieldLocation = FAR_FIELD;
        
        boolean dotsCol1 = !switchDotsCol1.get();
        boolean dotsCol2 = !switchDotsCol2.get();
        boolean dotsCol3 = !switchDotsCol3.get();

        dotsColLocation = DOTS_ROW_BAD;
        if ((dotsCol1 && dotsCol2) || (dotsCol1 && dotsCol3) || (dotsCol2 && dotsCol3))
            Debug.println(Debug.LOCATION, "******* Error: multiple dotRow switches are selected ******");
        else if(dotsCol1)
            dotsColLocation = DOTS_COL_1;
        else if(dotsCol2)
            dotsColLocation = DOTS_COL_2;
        else if(dotsCol3)
            dotsColLocation = DOTS_COL_3;
        Debug.println(Debug.LOCATION, this);
    }

    public double getTargetY()
    {
        switch(getFieldLocation())
        {
            case NEAR_FIELD:
                return Autonomous.Y_TARGET_NEAR_FIELD;
            case MID_FIELD:
                return Autonomous.Y_TARGET_MID_FIELD;
            case FAR_FIELD:
                return Autonomous.Y_TARGET_FAR_FIELD;
        }
        return Autonomous.Y_TARGET_MID_FIELD;   // a compromise...
    }

    public String toString()
    {
        String s = "Location: ";
        switch (fieldLocation)
        {
            case NEAR_FIELD: s += "near field, ";  break;
            case MID_FIELD:  s += "mid field, ";   break;
            case FAR_FIELD:  s += "far field, ";   break;
            default: s += "***INVALID FIELD*** ";  break;
        }
        switch (dotsColLocation)
        {
            case DOTS_COL_1: s += "row 1"; break;
            case DOTS_COL_2: s += "row 2"; break;
            case DOTS_COL_3: s += "row 3"; break;
            default: s += "***INVALID ROW***"; break;
        }
        return s;
    }
}