import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<MediaItem> items;

    public Database() {
        items = new ArrayList<>();
    }

    public void addItem(MediaItem item) {
        items.add(item);
    }

    public void removeItem(MediaItem item) {
        items.remove(item);
    }

    public void listAll() {
        if (items.isEmpty()) {
            System.out.println("Database is empty.");
            return;
        }
        System.out.println("=== All items in the database ===");
        for (MediaItem item : items) {
            item.printDetails();
        }
    }

    public void listCDs() {
        System.out.println("=== CDs in the database ===");
        for (MediaItem item : items) {
            if (item instanceof CD) {
                item.printDetails();
            }
        }
    }

    public void listVideos() {
        System.out.println("=== Videos in the database ===");
        for (MediaItem item : items) {
            if (item instanceof Video) {
                item.printDetails();
            }
        }
    }

    public void listVideoGames() {
        System.out.println("=== Video games in the database ===");
        for (MediaItem item : items) {
            if (item instanceof VideoGame) {
                item.printDetails();
            }
        }
    }

    public void searchByTitle(String keyword) {
        System.out.println("=== Search results for: " + keyword + " ===");
        for (MediaItem item : items) {
            if (item.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                item.printDetails();
            }
        }
    }

    public void searchCDsByArtist(String artistName) {
        System.out.println("=== CDs by: " + artistName + " ===");
        for (MediaItem item : items) {
            if (item instanceof CD cd) {
                if (cd.getArtist().equalsIgnoreCase(artistName)) {
                    cd.printDetails();
                }
            }
        }
    }

    public void searchVideosByDirector(String directorName) {
        System.out.println("=== Videos by: " + directorName + " ===");
        for (MediaItem item : items) {
            if (item instanceof Video video) {
                if (video.getDirector().equalsIgnoreCase(directorName)) {
                    video.printDetails();
                }
            }
        }
    }
}
