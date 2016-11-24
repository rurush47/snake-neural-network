/**
 * Created by Rurarz on 23.11.2016.
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;

public class Controller extends TimerTask implements KeyListener{

    private View view;
    private Model model;

    public Controller()
    {
        model = new Model(this);
        view = new View();
        view.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_A)
        {
            model.changeDirection(IntVector2.left);
        }
        if(e.getKeyCode() == KeyEvent.VK_D)
        {
            model.changeDirection(IntVector2.right);
        }
        if(e.getKeyCode() == KeyEvent.VK_W)
        {
            model.changeDirection(IntVector2.up);
        }
        if(e.getKeyCode() == KeyEvent.VK_S)
        {
            model.changeDirection(IntVector2.down);
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    public void update()
    {
        model.update();
        view.updateBoard(model.getBoard(), model.getScore());
    }

    public void gameOver()
    {

    }

    @Override
    public void run()
    {
        update();
    }
}