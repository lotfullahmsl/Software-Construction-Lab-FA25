import java.util.List;

public class Rabbit extends Animal
{
    private static final int BREEDING_AGE = 5;
    private static final int MAX_AGE = 40;

    public Rabbit()
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
        if (Randomizer.getRandom().nextDouble() <= 0.12) {
            newAnimals.add(new Rabbit());
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
