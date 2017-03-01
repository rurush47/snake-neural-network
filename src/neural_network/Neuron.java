package neural_network;

import utils.Configuration;

import java.util.ArrayList;


public class Neuron
{
    private ArrayList<Double> weights = new ArrayList<>();

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

    public void addNewWeights(ArrayList<Double> genome)
    {
        int size = this.weights.size();
        this.weights.clear();
        for (int i = 0; i < size; i++)
        {
            this.weights.add(genome.get(0));
            genome.remove(0);
        }
    }

    public double getOutput(ArrayList<Double> inputs)
    {
        double sum = 0;
        for (int i = 0; i < weights.size() - 1; i++)
        {
            sum += weights.get(i) * inputs.get(i);
        }

        sum += weights.get(weights.size() - 1) * Configuration.Bias;

        return sigmoid(sum);
    }

    public static double sigmoid(double a)
    {
        return (1/(1 + Math.pow(Math.E, (-a/Configuration.ActivationResponse))));
    }

    public ArrayList<Double> getWeights()
    {
        return weights;
    }
}
