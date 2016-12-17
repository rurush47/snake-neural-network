package neural_network;

import utils.Configuration;

import java.util.LinkedList;
import java.util.Vector;

public class NeuralNet
{
    private LinkedList<NeuronLayer> layersList = new LinkedList<>();

    public NeuralNet()
    {
        //first Layer
        layersList.add(new NeuronLayer(Configuration.NumberOfInputs, Configuration.NumberOfNeuronsPerLayer));

        for (int i = 0; i < Configuration.DefaultNumberOfHiddenLayers - 1; i++)
        {
            layersList.add(new NeuronLayer(Configuration.NumberOfNeuronsPerLayer, Configuration.NumberOfNeuronsPerLayer));
        }

        //output layer
        layersList.add(new NeuronLayer(Configuration.NumberOfNeuronsPerLayer, Configuration.NumberOfOutputs));
    }

    public Vector<Double> update(Vector<Double> inputs) throws Exception {

        if (inputs.size() != Configuration.NumberOfInputs)
        {
            throw new Exception("Input size doesn't match!");
        }

        Vector<Double> outputs = new Vector<>();

        for (int i = 0; i < layersList.size(); i++)
        {
            if(i > 0)
            {
                inputs = (Vector<Double>) outputs.clone();
            }

            outputs.clear();

            for (int j = 0; j < layersList.get(i).size(); j++)
            {
                Neuron neuron = layersList.get(i).getNeuronAt(j);
                outputs.add(neuron.getOutput(inputs));
                int l = 0;
            }
        }

        return outputs;
    }

    public Vector<Double> getGenome()
    {
        Vector<Double> genome = new Vector<>();

        for (NeuronLayer aLayersList : layersList) {
            for (int j = 0; j < aLayersList.size(); j++) {
                Neuron neuron = aLayersList.getNeuronAt(j);
                genome.addAll(neuron.getWeights());
            }
        }
        return genome;
    }

    public void applyGenome(Vector<Double> genome)
    {
        for (NeuronLayer aLayersList : layersList) {
            for (int j = 0; j < aLayersList.size(); j++) {
                Neuron neuron = aLayersList.getNeuronAt(j);
                neuron.addNewWeights(genome);
            }
        }
    }

}
