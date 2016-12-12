import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Rurarz on 30.11.2016.
 */
public class Neuron
{
    private Vector<Double> weights = new Vector<>();

    public Neuron(int numberOfInputs)
    {
        for (int i = 0; i < numberOfInputs + 1; i++)
        {
            weights.add(RandomDouble());
        }
    }

    public static double RandomDouble()
    {
        return Math.random()*2 - 1;
    }

    public void addNewWeights(Vector<Double> genome)
    {
        int size = this.weights.size();
        this.weights.clear();
        for (int i = 0; i < size; i++)
        {
            this.weights.add(genome.get(0));
            genome.remove(0);
        }
    }

    public double getOutput(Vector<Double> inputs)
    {
        double sum = 0;
        for (int i = 0; i < weights.size() - 1; i++)
        {
            sum += weights.get(i) * inputs.get(i);
        }

        sum += weights.lastElement() * Configuration.Bias;

        return sigmoid(sum);
    }

    public static double sigmoid(double a)
    {
        return (1/(1 + Math.pow(Math.E, (-a/Configuration.ActivationResponse))));
    }

    public Vector<Double> getWeights()
    {
        return weights;
    }
}
