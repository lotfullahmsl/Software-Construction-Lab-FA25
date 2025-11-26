package zuul;

import java.util.HashMap;
import java.util.Map;

public class CommandWords {
    private final Map<String, Class<? extends Command>> commands = new HashMap<>();

    public CommandWords() {
        commands.put("go", GoCommand.class);
        commands.put("help", HelpCommand.class);
        commands.put("look", LookCommand.class);
        commands.put("quit", QuitCommand.class);
    }

    public boolean isValid(String word) {
        return commands.containsKey(word.toLowerCase());
    }

    public Command createCommand(String word) {
        Class<? extends Command> cmd = commands.get(word.toLowerCase());
        if (cmd == null) return null;
        try {
            return cmd.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}