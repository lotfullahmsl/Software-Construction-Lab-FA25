package zuul;

public class Game {
    private final Parser parser;
    private Room currentRoom;
    private boolean playing = true;

    public Game() {
        CommandWords words = new CommandWords();
        parser = new Parser(words);
        GameWorld world = new GameWorld();
        currentRoom = world.getStartingRoom();
    }

    public void play() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("    Welcome to World of Zuul!           ");
        System.out.println("    (now with better code)              ");
        System.out.println("========================================");
        System.out.println("Type 'help' if you get stuck.\n");
        System.out.println(currentRoom.getLongDescription());

        while (playing) {
            Command c = parser.getCommand();
            if (c == null) {
                System.out.println("I don't know that command. Try 'help'.");
            } else {
                c.setGame(this);
                System.out.println(c.execute());
            }
        }
    }

    public void goToRoom(Room room) {
        currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void endGame() {
        playing = false;
    }
}