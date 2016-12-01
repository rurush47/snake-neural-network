import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rurarz on 30.11.2016.
 */
public class NeuronLayer
{
    private LinkedList<Neuron> neurons = new LinkedList<>();

    public NeuronLayer(int numberOfInputs, int numberOfNeurons)
    {
        for (int i = 0; i < numberOfNeurons; i++)
        {
            neurons.add(new Neuron(numberOfInputs));
        }
    }

    public int size()
    {
        return neurons.size();
    }

    public Neuron getNeuronAt(int index)
    {
        return neurons.get(index);
    }
}
