package zuul;

public class QuitCommand extends Command {
    @Override
    public String execute() {
        if (hasSecondWord()) {
            return "Quit what exactly? Just type 'quit' to escape.";
        }
        game.endGame();
        return "Thanks for playing! See you next time!";
    }
}