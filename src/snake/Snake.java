package snake;

import neural_network.NeuralNet;
import neural_network.Neuron;

import java.util.Vector;

public class Snake {

    private NeuralNet brain;
    public double fitness = 0;
    private Map map;
    private Vector<Double> netOutputs = new Vector<>();
    private Vector<Double> mapOutputs = new Vector<>();

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

    public Vector<Double> getGenome()
    {
        return brain.getGenome();
    }

    public void applyGenome(Vector<Double> genome)
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
