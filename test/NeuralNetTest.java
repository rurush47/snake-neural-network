import neural_network.Neuron;
import org.junit.Test;

public class NeuralNetTest {

    @Test
    public void testRandomDouble() throws Exception
    {
        double test = Neuron.RandomDouble();
        assert (test >= -1 && test <= 1);
    }
}