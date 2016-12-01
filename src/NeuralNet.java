import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

/**
 * Created by Rurarz on 30.11.2016.
 */
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

        Vector<Double> outputs = new Vector<Double>();

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
        //TODO
        return null;
    }

}
