/**
 * Created by Rurarz on 23.11.2016.
 */
public class IntVector2
{
    public int x;
    public int y;
    public static IntVector2 up = new IntVector2(0,1);
    public static IntVector2 down = new IntVector2(0,-1);
    public static IntVector2 right = new IntVector2(1,0);
    public static IntVector2 left = new IntVector2(-1,0);


    public IntVector2(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public static IntVector2 add(IntVector2 vec1, IntVector2 vec2)
    {
        return new IntVector2(vec1.x + vec2.x, vec1.y + vec2.y);
    }

    @Override
    public boolean equals(Object vec)
    {
        if(vec == null) return false;
        if(!(vec instanceof  IntVector2))
        {
            return false;
        }
        else
        {
            IntVector2 intVec = (IntVector2)vec;
            return this.x == intVec.x && this.y == intVec.y;
        }
    }
}
