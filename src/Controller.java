/**
 * Created by Rurarz on 23.11.2016.
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;

public class Controller extends TimerTask implements KeyListener{

    private GameView gameGameView;
    private GameView evolutionGameView;
    private Map map;
    private Snake snake;
    public boolean continueEvolution = true;

    public Controller(Snake snake)
    {
        this.snake = snake;
        map = snake.getMap();
        gameGameView = new GameView();
        gameGameView.addKeyListener(this);
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
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            map.gameOver();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    public void update() throws Exception {
        snake.update();
        gameGameView.updateBoard(map.getBoard(), map.getScore());
    }

    public void addSnake(Snake snake)
    {
        this.snake = snake;
    }

    @Override
    public void run()
    {
        try {
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}