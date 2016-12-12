import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Rurarz on 07.12.2016.
 */
public class EvolutionController implements KeyListener
{
    public boolean continueEvolution = true;

    public EvolutionController(EvolutionView view)
    {
        view.addListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_S)
        {
            continueEvolution = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
