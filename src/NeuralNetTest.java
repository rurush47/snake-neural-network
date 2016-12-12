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

        Snake snake = new Snake();

        double dist = snake.getMap().getAppleDistance();
        snake.update();
        dist = snake.getMap().getAppleDistance();
        snake.update();
        dist = snake.getMap().getAppleDistance();
        snake.update();
        dist = snake.getMap().getAppleDistance();
        snake.update();
        dist = snake.getMap().getAppleDistance();
        int i = 0;


    }
}