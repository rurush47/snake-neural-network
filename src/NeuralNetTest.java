import org.junit.Test;

import java.util.Vector;

/**
 * Created by Rurarz on 30.11.2016.
 */
public class NeuralNetTest {
    @Test
    public void testRandomDouble() throws Exception {
        double test = Neuron.RandomDouble();
        assert (test >= -1 && test <= 1);
    }

    @Test
    public void testSth() throws Exception {
        NeuralNet net = new NeuralNet();
        Vector<Double> inputs = new Vector();

        inputs.add(0.3);
        inputs.add(-0.5);

        Vector<Double> outputs = net.update(inputs);
        int i = 0;

        assert (true);
    }

}