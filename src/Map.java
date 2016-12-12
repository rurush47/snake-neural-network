/**
 * Created by Rurarz on 23.11.2016.
 */

import java.util.*;

public class Map {
    public static int BOARDSIZE = 8;
    private String[][] board = new String[BOARDSIZE][BOARDSIZE];
    private LinkedList<IntVector2> emptyFields;
    private LinkedList<IntVector2> snakeBody;
    private Random randomGenerator;
    private IntVector2 movementDirection;
    private IntVector2 applePos;
    private int score;
    private boolean dirChanged = false;
    private Snake snake;
    public boolean death = false;
    //fitnnss booleans
    private boolean movementPoints = true;
    private boolean applePoints = true;
    private boolean deathPenalty = false;
    private boolean notEatingPenalty = false;
    private boolean penalty = false;
    //fitness values
    private int penaltyThreshold = 7;
    private int ticksCounter = 0;
    private int pointsPerMove = 1;
    private int pointsPerApple = 100;
    private int penaltyPoints = 1;
    private int deathPenaltyPoints = 2;



    public Map(Snake snake)
    {
        this.snake = snake;
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

    public void updateWithInput(Vector<Double> inputs) throws Exception {

        if(inputs.size() != Configuration.NumberOfOutputs)
        {
            throw new Exception("Number of net outputs doesnt match configuration!");
        }

        double dX = inputs.firstElement();

        IntVector2 moveVec = movementDirection;

        if(dX >= 0 && dX < 0.33)
        {
            moveVec = IntVector2.getClockwise(moveVec);
        }
        else if (dX >= 0.33 && dX < 0.66)
        {
            moveVec = IntVector2.getCounterClockwise(moveVec);
        }
        else
        {
            update();
            return;
        }

        changeDirection(moveVec);
        update();
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
            applePos = randomCell;
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
            //move
            if (isEmpty(newHeadPos))
            {
                changeCell(newHeadPos, "body");
                snakeBody.addFirst(newHeadPos);
                changeCell(snakeBody.getLast(),"empty");
                snakeBody.removeLast();
                addMoveBonus();
            }
            //collect apple
            else if (isApple(newHeadPos))
            {
                changeCell(newHeadPos, "body");
                snakeBody.addFirst(newHeadPos);
                addScore();
                instantiateApple();
                addAppleBonus();
            }
            //death
            else
            {
                gameOver();
            }
        }
        //death
        else
        {
            gameOver();
        }
    }

    public void gameOver()
    {
        applyDeathPenalty();
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

    public Vector<Double> getOutputs()
    {
        Vector<Double> outputs = new Vector<>();

        //headPos
        outputs.add((double)getHeadPos().x);
        outputs.add((double)getHeadPos().y);
        //applePos
        outputs.add((double)applePos.x);
        outputs.add((double)applePos.x);
        //distance to apple
        outputs.add(getAppleDistance());
        //how far is obstacle in the front of the head
        outputs.add((double) getObstacleDistance("front"));
        //how far is obstacle on the left of the head
        outputs.add((double) getObstacleDistance("left"));
        //how far is obstacle on the right of the head
        outputs.add((double) getObstacleDistance("right"));

        return outputs;
    }

    public double getAppleDistance()
    {
        IntVector2 headPos = snakeBody.getFirst();

        return Math.sqrt(Math.pow(headPos.x - applePos.x, 2) + Math.pow(headPos.y - applePos.y, 2));
    }

    private int getObstacleDistance(String raycastDir)
    {
        IntVector2 moveDir;

        if(raycastDir.equals("front"))
        {
            moveDir = movementDirection.copy();
        }
        else if(raycastDir.equals("right"))
        {
            moveDir = IntVector2.getClockwise(movementDirection);
        }
        else
        {
            moveDir = IntVector2.getCounterClockwise(movementDirection);
        }

        IntVector2 headPos = snakeBody.getFirst().copy();
        int distance = 0;

        headPos = IntVector2.add(headPos, moveDir);

        while (rangeCheck(headPos.x, headPos.y)
                && board[headPos.x][headPos.y] != "body")
        {
            distance ++;
            headPos = IntVector2.add(headPos, moveDir);
        }

        return distance;
    }

    private void addMoveBonus()
    {
        if(movementPoints)
        {
            ticksCounter++;
            if (ticksCounter < penaltyThreshold)
            {
                snake.fitness += pointsPerMove;
            }
            else
            {
                applyPenalty(penaltyPoints);
            }
        }
    }

    private void addAppleBonus()
    {
        if(applePoints)
        {
            snake.fitness += pointsPerApple;
            ticksCounter = 0;
        }
    }

    private void applyDeathPenalty()
    {
        applyPenalty(deathPenaltyPoints);
    }

    private void applyPenalty(int value)
    {
        if (snake.fitness > 0)
        {
            snake.fitness -= value;
            if(snake.fitness < 0)
            {
                snake.fitness = 0;
            }
        }
    }
}
