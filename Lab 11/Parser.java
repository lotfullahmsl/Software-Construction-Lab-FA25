package zuul;

import java.util.Scanner;

public class Parser {
    private final Scanner scanner = new Scanner(System.in);
    private final CommandWords commandWords;

    public Parser(CommandWords commandWords) {
        this.commandWords = commandWords;
    }

    public Command getCommand() {
        System.out.print("\n> ");
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return null;

        String[] parts = line.toLowerCase().split("\\s+", 2);
        String word1 = parts[0];
        String word2 = parts.length > 1 ? parts[1] : null;

        if (commandWords.isValid(word1)) {
            Command cmd = commandWords.createCommand(word1);
            if (cmd != null && word2 != null) cmd.setSecondWord(word2);
            return cmd;
        }
        return null;
    }
}