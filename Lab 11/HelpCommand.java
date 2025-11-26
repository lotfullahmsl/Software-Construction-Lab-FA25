package zuul;

public class HelpCommand extends Command {
    @Override
    public String execute() {
        return """
            You're looking a bit lost, friend.

            Available commands:
              go     → go east / go lab / etc.
              look   → see your surroundings again
              help   → this message (hi again!)
              quit   → leave the adventure

            Have fun exploring!
            """;
    }
}