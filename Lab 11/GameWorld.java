package zuul;

public class GameWorld {
    private Room startingRoom;

    public GameWorld() {
        createRooms();
    }

    private void createRooms() {
        Room outside = new Room("outside the main entrance – sunshine and freedom");
        Room theatre = new Room("in the huge lecture theatre – smells like old coffee");
        Room pub = new Room("in the campus pub – Friday vibes all day");
        Room lab = new Room("in the computing lab – fans humming loudly");
        Room office = new Room("in the admin office – biscuit tin is locked");

        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);
        pub.setExit("east", outside);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);

        startingRoom = outside;
    }

    public Room getStartingRoom() {
        return startingRoom;
    }
}