package neural_network;

import java.util.LinkedList;

class NeuronLayer
{
    private LinkedList<Neuron> neurons = new LinkedList<>();

    NeuronLayer(int numberOfInputs, int numberOfNeurons)
    {
        for (int i = 0; i < numberOfNeurons; i++)
        {
            neurons.add(new Neuron(numberOfInputs));
        }
    }

    int size()
    {
        return neurons.size();
    }

    Neuron getNeuronAt(int index)
    {
        return neurons.get(index);
    }
}
