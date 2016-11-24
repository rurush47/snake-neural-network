/**
 * Created by Rurarz on 23.11.2016.
 */

import java.util.*;

public class Model {
    public static int BOARDSIZE = 10;
    private String[][] board = new String[BOARDSIZE][BOARDSIZE];
    private LinkedList<IntVector2> emptyFields;
    private LinkedList<IntVector2> snakeBody;
    private Random randomGenerator;
    private IntVector2 movementDirection;
    private int score;
    private Controller controller;
    private boolean dirChanged = false;

    public Model(Controller controller)
    {
        this.controller = controller;
        emptyFields = new LinkedList<>();
        snakeBody = new LinkedList<>();
        randomGenerator = new Random();
        score = 0;

        for(int i = 0; i < BOARDSIZE; i++)
        {
            for (int j = 0; j < BOARDSIZE; j++)
            {
                emptyFields.add(new IntVector2(i,j));
                board[i][j] = "empty";
            }
        }

        startGame();
    }

    public void startGame()
    {
        initialize();
        movementDirection = IntVector2.right;
    }

    private void initialize()
    {
        snakeBody.add(new IntVector2(BOARDSIZE/2, BOARDSIZE/2));
        IntVector2 headPos = getHeadPos();
        changeCell(headPos, "body");
        instantiateApple();
    }

    public void update()
    {
        move();
        dirChanged = false;
    }

    public void changeDirection(IntVector2 dir)
    {
        if(dirChanged == false)
        {
            IntVector2 sumVec = IntVector2.add(dir, movementDirection);
            if(!sumVec.equals(new IntVector2(0,0)))
            {
                movementDirection = dir;
                dirChanged = true;
            }
        }
    }

    private void instantiateApple()
    {
        if(emptyFields.size() > 0)
        {
            int randomIndex = randomGenerator.nextInt(emptyFields.size());
            IntVector2 randomCell = emptyFields.get(randomIndex);

            changeCell(randomCell, "apple");
        }
        else
        {
            gameWon();
        }
    }

    private boolean rangeCheck(int x, int y)
    {
        if (x < BOARDSIZE && y < BOARDSIZE && x >= 0 && y >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean rangeCheck(IntVector2 pos)
    {
        return rangeCheck(pos.x, pos.y);
    }

    private boolean isEmpty(int x, int y)
    {
        if(board[x][y].equals("empty"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isEmpty(IntVector2 vec)
    {
        return isEmpty(vec.x, vec.y);
    }

    private boolean isApple(int x, int y)
    {
        if(board[x][y].equals("apple"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isApple(IntVector2 vec)
    {
        return isApple(vec.x, vec.y);
    }

    private void move()
    {
        IntVector2 newHeadPos = IntVector2.add(getHeadPos(), movementDirection);

        if(rangeCheck(newHeadPos))
        {
            if (isEmpty(newHeadPos))
            {
                changeCell(newHeadPos, "body");
                snakeBody.addFirst(newHeadPos);
                changeCell(snakeBody.getLast(),"empty");
                snakeBody.removeLast();
            }
            else if (isApple(newHeadPos))
            {
                changeCell(newHeadPos, "body");
                snakeBody.addFirst(newHeadPos);
                addScore();
                instantiateApple();
            }
            else
            {
                gameOver();
            }
        }
        else
        {
            gameOver();
        }
    }

    private void gameOver()
    {
        score = 0;
        controller.gameOver();
        resetBoard();
        startGame();
    }

    private void gameWon()
    {

    }

    private IntVector2 getHeadPos()
    {
        return snakeBody.get(0);
    }

    private void addScore()
    {
        score += 1;
    }

    public int GetScore()
    {
        return score;
    }

    public String[][] getBoard()
    {
        return board;
    }

    private void changeCell(IntVector2 vec, String status)
    {
        board[vec.x][vec.y] = status;
        if(status.equals("empty"))
        {
            emptyFields.add(vec);
        }
        else if (status.equals("body") || status.equals("apple"))
        {
            emptyFields.remove(vec);
        }
    }

    public int getScore()
    {
        return score;
    }

    public void resetBoard()
    {
        emptyFields.clear();
        snakeBody.clear();
        for(int i = 0; i < BOARDSIZE; i++)
        {
            for (int j = 0; j < BOARDSIZE; j++)
            {
                emptyFields.add(new IntVector2(i,j));
                board[i][j] = "empty";
            }
        }
    }
}
