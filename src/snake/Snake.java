package snake;

import neural_network.NeuralNet;
import neural_network.Neuron;

import java.util.ArrayList;


public class Snake {

    private NeuralNet brain;
    public double fitness = 0;
    private Map map;
    private ArrayList<Double> netOutputs = new ArrayList<>();
    private ArrayList<Double> mapOutputs = new ArrayList<>();

    public Snake()
    {
        brain = new NeuralNet();
        map = new Map(this);
        Init();
    }

    private void Init()
    {
        netOutputs.add(Neuron.RandomDouble());
        netOutputs.add(Neuron.RandomDouble());
    }

    public ArrayList<Double> getGenome()
    {
        return brain.getGenome();
    }

    public void applyGenome(ArrayList<Double> genome)
    {
        brain.applyGenome(genome);
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

    public NeuralNet getNet()
    {
        return brain;
    }

    public double getFitness()
    {
        return fitness;
    }

}
