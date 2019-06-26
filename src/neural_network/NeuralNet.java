package neural_network;

import main.EvolutionController;

import java.util.ArrayList;


public class NeuralNet
{
    private ArrayList<NeuronLayer> layersList = new ArrayList<>();

    public NeuralNet()
    {
        //first Layer
        layersList.add(new NeuronLayer(EvolutionController.Config.NumberOfInputs, EvolutionController.Config.NumberOfNeuronsPerLayer));

        for (int i = 0; i < EvolutionController.Config.DefaultNumberOfHiddenLayers - 1; i++)
        {
            layersList.add(new NeuronLayer(EvolutionController.Config.NumberOfNeuronsPerLayer, EvolutionController.Config.NumberOfNeuronsPerLayer));
        }

        //output layer
        layersList.add(new NeuronLayer(EvolutionController.Config.NumberOfNeuronsPerLayer, EvolutionController.Config.NumberOfOutputs));
    }

    public ArrayList<Double> update(ArrayList<Double> inputs) throws Exception {

        if (inputs.size() != EvolutionController.Config.NumberOfInputs)
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
            }
        }

        return outputs;
    }

    public ArrayList<Object> getNeurons()
    {
        ArrayList<Object> genome = new ArrayList<>();

        for (NeuronLayer aLayersList : layersList) {
            for (int j = 0; j < aLayersList.size(); j++) {
                Neuron neuron = aLayersList.getNeuronAt(j);
                genome.addAll(neuron.getWeights());
            }
        }
        return genome;
    }

    public void applyNeurons(ArrayList<Double> genome)
    {
        for (NeuronLayer aLayersList : layersList) {
            for (int j = 0; j < aLayersList.size(); j++) {
                Neuron neuron = aLayersList.getNeuronAt(j);
                neuron.addNewWeights(genome);
            }
        }
    }
}
