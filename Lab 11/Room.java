package zuul;

import java.util.HashMap;
import java.util.Map;

public class Room {
    private final String description;
    private final Map<String, Room> exits = new HashMap<>();

    public Room(String description) {
        this.description = description;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction.toLowerCase(), neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public String getExitList() {
        if (exits.isEmpty()) return "nowhere... scary";
        return String.join(", ", exits.keySet());
    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + getExitList() + "\n";
    }

    public String getShortDescription() {
        return description;
    }
}