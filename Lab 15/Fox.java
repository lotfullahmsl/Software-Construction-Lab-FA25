import java.util.List;
import java.util.Iterator;

public class Fox extends Animal
{
    private static final int BREEDING_AGE = 10;
    private static final int MAX_AGE = 150;

    public Fox()
    {
        super();
    }

    public void act(List<Actor> newActors)
    {
        incrementAge();
        if (!isAlive()) {
            return;
        }

        if (canBreed()) {
            breed(newActors);
        }
    }

    protected void breed(List<Actor> newAnimals)
    {
        if (Randomizer.getRandom().nextDouble() <= 0.08) {
            newAnimals.add(new Fox());
        }
    }

    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    protected int getMaxAge()
    {
        return MAX_AGE;
    }
}
