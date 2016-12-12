/**
 * Created by Rurarz on 23.11.2016.
 */

import java.awt.*;
import javax.swing.*;

public class GameView extends JPanel
{

    String board[][];
    JLabel scoreText;
    int score;

    public GameView()
    {
        JFrame window = new JFrame();
        window.setBounds(400, 400, 400, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.requestFocusInWindow();
        window.setContentPane(this);
        window.setVisible(true);
        scoreText = new JLabel();
        scoreText.setBounds(30, 400, 50, 50);
        scoreText.setText("0");
        window.add(scoreText);
    }

    public void updateBoard(String[][] board, int score)
    {
        this.board = board;
        this.score = score;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        if(board != null)
        {
            for(int i = 0; i < board.length; i++)
            {
                for(int j = 0; j < board[0].length; j++)
                {
                    if(board[i][j].equals("empty"))
                    {
                        g.setColor(Color.WHITE);
                        g.fillRect(i*20,(Map.BOARDSIZE -1 -j)*20,20,20);
                    }
                    if(board[i][j].equals("body"))
                    {
                        g.setColor(Color.RED);
                        g.fillRect(i*20,(Map.BOARDSIZE -1 -j)*20,20,20);
                    }
                    if(board[i][j].equals("apple"))
                    {
                        g.setColor(Color.BLUE);
                        g.fillRect(i*20,(Map.BOARDSIZE -1 -j)*20,20,20);
                    }
                }
            }
            scoreText.setText(score + "");
        }
    }

    public void gameOver()
    {
        JOptionPane.showConfirmDialog(this, "game over");
    }

}
