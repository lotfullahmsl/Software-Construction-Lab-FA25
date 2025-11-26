package zuul;

public class LookCommand extends Command {
    @Override
    public String execute() {
        return game.getCurrentRoom().getLongDescription();
    }
}