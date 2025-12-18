import java.util.List;

public interface Actor
{
    void act(List<Actor> newActors);

    boolean isAlive();
}
