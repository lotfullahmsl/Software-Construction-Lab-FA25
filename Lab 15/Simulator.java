import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Simulator
{
    private List<Actor> actors;
    private PopulationGenerator generator;

    public Simulator()
    {
        actors = new ArrayList<>();
        generator = new PopulationGenerator();

        Randomizer.useShared = true;
        Randomizer.reset();

        generator.populate(actors, 50);
    }

    public void simulateOneStep()
    {
        List<Actor> newActors = new ArrayList<>();

        Iterator<Actor> it = actors.iterator();
        while (it.hasNext()) {
            Actor actor = it.next();
            actor.act(newActors);
            if (!actor.isAlive()) {
                it.remove();
            }
        }

        actors.addAll(newActors);
    }
}
