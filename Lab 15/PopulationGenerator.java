import java.util.List;

public class PopulationGenerator
{
    public void populate(List<Actor> actors, int size)
    {
        for (int i = 0; i < size; i++) {
            if (Randomizer.getRandom().nextDouble() <= 0.02) {
                actors.add(new Fox());
            }
            else {
                actors.add(new Rabbit());
            }
        }
    }
}
