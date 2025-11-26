package zuul;

public class GoCommand extends Command {
    @Override
    public String execute() {
        if (!hasSecondWord()) {
            return "Go where? Give me a direction!";
        }

        String direction = getSecondWord();
        Room nextRoom = game.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            return "Ouch! There's no way " + direction + " from here.";
        }

        game.goToRoom(nextRoom);
        return game.getCurrentRoom().getLongDescription();
    }
}