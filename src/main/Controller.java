package main;

import snake.Map;
import snake.Snake;
import view.GameView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;

class Controller extends TimerTask implements KeyListener{

    private GameView gameGameView;
    private Map map;
    private Snake snake;

    Controller(Snake snake)
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

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    private void update() throws Exception {
        snake.update();
        gameGameView.updateBoard(map.getBoard(), map.getScore());
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