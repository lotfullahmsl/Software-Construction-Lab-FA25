import java.util.Random;

public class Randomizer
{
    private static Random rand = new Random();
    public static boolean useShared = false;

    public static Random getRandom()
    {
        if (useShared) {
            return rand;
        }
        else {
            return new Random();
        }
    }

    public static void reset()
    {
        rand = new Random(1111);
    }
}
