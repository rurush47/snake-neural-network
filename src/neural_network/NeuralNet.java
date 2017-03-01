package neural_network;

import utils.Configuration;

import java.util.ArrayList;


public class NeuralNet
{
    private ArrayList<NeuronLayer> layersList = new ArrayList<>();

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

    public ArrayList<Double> update(ArrayList<Double> inputs) throws Exception {

        if (inputs.size() != Configuration.NumberOfInputs)
        {
            throw new Exception("Input size doesn't match!");
        }

        ArrayList<Double> outputs = new ArrayList<>();

        for (int i = 0; i < layersList.size(); i++)
        {
            if(i > 0)
            {
                inputs = (ArrayList<Double>) outputs.clone();
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

    public ArrayList<Double> getGenome()
    {
        ArrayList<Double> genome = new ArrayList<>();

        for (NeuronLayer aLayersList : layersList) {
            for (int j = 0; j < aLayersList.size(); j++) {
                Neuron neuron = aLayersList.getNeuronAt(j);
                genome.addAll(neuron.getWeights());
            }
        }
        return genome;
    }

    public void applyGenome(ArrayList<Double> genome)
    {
        for (NeuronLayer aLayersList : layersList) {
            for (int j = 0; j < aLayersList.size(); j++) {
                Neuron neuron = aLayersList.getNeuronAt(j);
                neuron.addNewWeights(genome);
            }
        }
    }

}
