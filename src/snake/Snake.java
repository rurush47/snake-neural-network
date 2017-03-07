package snake;

import generic.Individual;
import neural_network.NeuralNet;
import neural_network.Neuron;
import utils.Configuration;

import java.util.ArrayList;


public class Snake extends Individual
{

    private NeuralNet brain;
    private Map map;
    private ArrayList<Double> netOutputs = new ArrayList<>();
    private ArrayList<Double> mapOutputs = new ArrayList<>();

    public Snake()
    {
        brain = new NeuralNet();
        resetMap();
    }

    public void resetMap()
    {
        map = new Map(this);
        Initialize();
    }

    @Override
    public void Initialize() {
        netOutputs.add(Neuron.RandomDouble());
        netOutputs.add(Neuron.RandomDouble());
    }

    public ArrayList<Object> getGenome()
    {
        return brain.getNeurons();
    }

    @Override
    public void applyGenome(ArrayList<Object> genome)
    {
        brain.applyNeurons((ArrayList<Double>)(ArrayList<?>) genome);
    }

    @Override
    public Object mutateGene(Object gene) {
        double newDouble = (Double) gene;
        newDouble += Neuron.RandomDouble() * Configuration.MaxPerturbation;
        return newDouble;
    }

    public void update() throws Exception {
        mapOutputs = map.getOutputs();
        netOutputs = brain.update(mapOutputs);
        map.updateWithInput(netOutputs);
    }

    public Map getMap()
    {
        return map;
    }
}
