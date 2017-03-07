import org.junit.Assert;
import org.junit.Test;
import snake.Map;
import snake.Snake;
import utils.IntVector2;

public class MapTest
{
    @Test
    public void getObstaclesNextToHeadTest()
    {
        Snake snake = new Snake();
        Map map = new Map(snake);
        String[][] board =  map.getBoard();

        map.startGame();
        map.changeDirection(IntVector2.right);
        IntVector2 headPos = map.getHeadPos();

        board[headPos.x][headPos.y + 1] = "body";
        board[headPos.x + 1][headPos.y] = "apple";
        board[headPos.x][headPos.y - 1] = "empty";

        Assert.assertEquals(map.getObstacleNextToHead("front"), 1);
        Assert.assertEquals(map.getObstacleNextToHead("left"), -1);
        Assert.assertEquals(map.getObstacleNextToHead("right"), 0);
    }

    @Test
    public void getNormalizedAppleDistanceTest()
    {
        Snake snake = new Snake();
        Map map = new Map(snake);
        String[][] board =  map.getBoard();

        map.startGame();
        map.changeDirection(IntVector2.right);
        IntVector2 headPos = map.getHeadPos();
        IntVector2 applePos = map.getApplePos();

        double distance = map.getNormalizedAppleDistance();

        Assert.assertEquals(distance > 0 && distance < 0.6, true);
    }
}
