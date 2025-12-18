import java.util.List;

public abstract class Animal implements Actor
{
    protected int age;
    protected boolean alive;

    public Animal()
    {
        age = 0;
        alive = true;
    }

    public boolean isAlive()
    {
        return alive;
    }

    protected void setDead()
    {
        alive = false;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    /**
     * An animal can breed if it has reached breeding age.
     */
    public boolean canBreed()
    {
        return age >= getBreedingAge();
    }

    /**
     * Increase the age of the animal.
     */
    protected void incrementAge()
    {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    // Abstract methods to be implemented by subclasses
    protected abstract int getBreedingAge();
    protected abstract int getMaxAge();
    protected abstract void breed(List<Actor> newAnimals);


}
