import java.util.Vector;

/**
 * Created by Rurarz on 25.11.2016.
 */
public class Snake {

    private NeuralNet brain;
    public int fitness = 0;
    private Map map;
    private Vector<Double> netOutputs = new Vector<>();
    private Vector<Double> mapOutputs = new Vector<>();
    private boolean firstTry = true;

    public Snake()
    {
        brain = new NeuralNet();
        map = new Map(this);
        Init();
    }

    public void Init()
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

    public int getFitness()
    {
        return fitness;
    }

}
