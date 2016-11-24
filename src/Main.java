import java.util.Timer;

/**
 * Created by Rurarz on 16.11.2016.
 */
public class Main
{
    public static void main(String[] args) {
        Controller controller = new Controller();
        Timer timer = new Timer();
        timer.schedule(controller, 0, 200);
    }
}
