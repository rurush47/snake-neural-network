package main;

import view.EvolutionView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class EvolutionController implements KeyListener
{
    boolean continueEvolution = true;

    EvolutionController(EvolutionView view)
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
