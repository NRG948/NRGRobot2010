package nrgrobot;

/**
 * This class averages drive input values to prevent the drive from switching
 * directions instantaneously and putting stress on the mechanical components
 * @author Brian, Austin, Paul, Stephen
 */
public class InputAverager
{
    double[] arr;
    int index;
    double sum;

    public InputAverager(int length)
    {
        arr = new double[length];
        index = 0;
	sum = 0;
    }

    public void addValue(double value)
    {
        int i = index++ % arr.length;
	sum += value - arr[i];
	arr[i] = value;
    }

    public double getAverageValue()
    {
        return sum / arr.length;
    }

}
