package snake;

import utils.Configuration;
import utils.IntVector2;

import java.util.ArrayList;
import java.util.Random;

public class Map
{
    public static int BOARDSIZE = 12;
    private String[][] board = new String[BOARDSIZE][BOARDSIZE];
    private ArrayList<IntVector2> emptyFields;
    private ArrayList<IntVector2> snakeBody;
    private Random randomGenerator;
    private IntVector2 movementDirection;
    private IntVector2 applePos;
    private IntVector2 headPos;
    private int score;
    private boolean dirChanged = false;
    private Snake snake;
    public boolean death = false;

    //fitness values
    private double pointsPerApple = 500;
    private double speedBonus;
    private static final double initialSpeedBonus = 400;
    private static final double speedBonusDecay = 10;



    public Map(Snake snake)
    {
        speedBonus = initialSpeedBonus;
        this.snake = snake;
        emptyFields = new ArrayList<>();
        snakeBody = new ArrayList<>();
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
        movementDirection = new IntVector2(1,0);
    }

    private void initialize()
    {
        IntVector2 startPos = new IntVector2(BOARDSIZE/2, BOARDSIZE/2);
        snakeBody.add(startPos);
        headPos = startPos;
        changeCell(headPos, "body");
        instantiateApple();
    }

    private void update()
    {
        move();
        dirChanged = false;
    }

    void updateWithInput(ArrayList<Double> inputs) throws Exception {

        if(inputs.size() != Configuration.NumberOfOutputs)
        {
            throw new Exception("Number of net outputs doesn't match configuration!");
        }

        double dX = inputs.get(0);

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
        if(!dirChanged)
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
        return x < BOARDSIZE && y < BOARDSIZE && x >= 0 && y >= 0;
    }

    private boolean rangeCheck(IntVector2 pos)
    {
        return rangeCheck(pos.x, pos.y);
    }

    private boolean isEmpty(int x, int y)
    {
        return board[x][y].equals("empty");
    }

    private boolean isEmpty(IntVector2 vec)
    {
        return isEmpty(vec.x, vec.y);
    }

    private boolean isApple(int x, int y)
    {
        return board[x][y].equals("apple");
    }

    private boolean isApple(IntVector2 vec)
    {
        return isApple(vec.x, vec.y);
    }

    private void move()
    {
        headPos = IntVector2.add(getHeadPos(), movementDirection);

        if(rangeCheck(headPos))
        {
            //move
            if (isEmpty(headPos))
            {
                changeCell(headPos, "body");
                snakeBody.add(0, headPos);
                changeCell(snakeBody.get(snakeBody.size() - 1),"empty");
                snakeBody.remove(snakeBody.size() - 1);
                addMoveBonus();
            }
            //collect apple
            else if (isApple(headPos))
            {
                changeCell(headPos, "body");
                snakeBody.add(0, headPos);
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
        death = true;
        resetBoard();
        startGame();
    }

    private void gameWon()
    {

    }

    public IntVector2 getHeadPos()
    {
        return headPos;
    }

    private void addScore()
    {
        score += 1;
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

    private void resetBoard()
    {
        score = 0;
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

    ArrayList<Double> getOutputs()
    {
        ArrayList<Double> outputs = new ArrayList<>();

        //distance to apple
        outputs.add(getAppleDistanceX());
        outputs.add(getAppleDistanceY());
        //what is on front of snake's head
        outputs.add((double) getObstacleNextToHead("front"));
        //what is on left of snake's head
        outputs.add((double) getObstacleNextToHead("left"));
        //what is on right of snake's head
        outputs.add((double) getObstacleNextToHead("right"));

        return outputs;
    }

    private double getAppleDistanceX()
    {
        return Math.abs(getApplePos().x - getHeadPos().x);
    }

    private double getAppleDistanceY()
    {
        return Math.abs(getApplePos().y - getHeadPos().y);
    }

    private double getAppleDistance()
    {
        IntVector2 headPos = getHeadPos();

        return Math.sqrt(Math.pow(headPos.x - applePos.x, 2) + Math.pow(headPos.y - applePos.y, 2));
    }

    public IntVector2 getApplePos()
    {
        return applePos;
    }

    private int getObstacleDistance(String raycastDir)
    {
        IntVector2 moveDir;

        switch (raycastDir) {
            case "front":
                moveDir = movementDirection.copy();
                break;
            case "right":
                moveDir = IntVector2.getClockwise(movementDirection);
                break;
            default:
                moveDir = IntVector2.getCounterClockwise(movementDirection);
                break;
        }

        IntVector2 headPos = getHeadPos().copy();
        int distance = 0;

        headPos = IntVector2.add(headPos, moveDir);

        while (rangeCheck(headPos.x, headPos.y)
                && !board[headPos.x][headPos.y].equals("body"))
        {
            distance ++;
            headPos = IntVector2.add(headPos, moveDir);
        }

        return distance;
    }

    public int getObstacleNextToHead(String raycastDir)
    {
        IntVector2 moveDir;

        switch (raycastDir) {
            case "front":
                moveDir = movementDirection.copy();
                break;
            case "right":
                moveDir = IntVector2.getClockwise(movementDirection);
                break;
            default:
                moveDir = IntVector2.getCounterClockwise(movementDirection);
                break;
        }

        IntVector2 checkPos = IntVector2.add(getHeadPos(), moveDir);

        if(rangeCheck(checkPos))
        {
            String cell = board[checkPos.x][checkPos.y];

            if(cell.equals("apple"))
            {
                return 1;
            }
            else if(cell.equals("empty"))
            {
                return 0;
            }
        }
        return -1;
    }

    private void addMoveBonus()
    {
            speedBonus -= speedBonusDecay;
            if(speedBonus <= 0)
            {
                speedBonus = 0;
            }
    }

    public double getNormalizedAppleDistance()
    {
        double diagonal = Math.sqrt(2)*BOARDSIZE;
        return 1 - ((diagonal - getAppleDistance())/diagonal);
    }

    private void addAppleBonus()
    {
        snake.fitness += pointsPerApple + speedBonus;
        speedBonus = initialSpeedBonus;
    }

    private void applyDeathPenalty()
    {

    }
}
