import java.util.ArrayList;
import java.util.List;

public class DoMEApp {

    // ===== Core Model =====

    public static abstract class MediaItem {
        private String title;
        private int playingTime;
        private boolean owned;
        private String comment;

        public MediaItem(String title, int playingTime) {
            this.title = title;
            this.playingTime = playingTime;
            this.owned = false;
            this.comment = "<no comment>";
        }

        public String getTitle() {
            return title;
        }

        public int getPlayingTime() {
            return playingTime;
        }

        public boolean isOwned() {
            return owned;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setOwned(boolean owned) {
            this.owned = owned;
        }

        public void printDetails() {
            System.out.println("------------------------------");
            System.out.println("Title       : " + title);
            System.out.println("Playing time: " + playingTime + " min");
            System.out.println("Owned       : " + (owned ? "yes" : "no"));
            System.out.println("Comment     : " + comment);
            printExtraDetails();
        }

        protected abstract void printExtraDetails();
    }

    public static class CD extends MediaItem {
        private String artist;
        private int trackCount;

        public CD(String title, String artist, int trackCount, int playingTime) {
            super(title, playingTime);
            this.artist = artist;
            this.trackCount = trackCount;
        }

        public String getArtist() {
            return artist;
        }

        public int getTrackCount() {
            return trackCount;
        }

        @Override
        protected void printExtraDetails() {
            System.out.println("Type        : CD");
            System.out.println("Artist      : " + artist);
            System.out.println("Tracks      : " + trackCount);
        }
    }

    public static class Video extends MediaItem {
        private String director;

        public Video(String title, String director, int playingTime) {
            super(title, playingTime);
            this.director = director;
        }

        public String getDirector() {
            return director;
        }

        @Override
        protected void printExtraDetails() {
            System.out.println("Type        : Video");
            System.out.println("Director    : " + director);
        }
    }

    public static class VideoGame extends MediaItem {
        private String platform;
        private int maxPlayers;

        public VideoGame(String title, String platform, int maxPlayers, int playingTime) {
            super(title, playingTime);
            this.platform = platform;
            this.maxPlayers = maxPlayers;
        }

        public String getPlatform() {
            return platform;
        }

        public int getMaxPlayers() {
            return maxPlayers;
        }

        @Override
        protected void printExtraDetails() {
            System.out.println("Type        : Video Game");
            System.out.println("Platform    : " + platform);
            System.out.println("Max players : " + maxPlayers);
        }
    }

    public static class Database {
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
                if (item instanceof CD) {
                    CD cd = (CD) item;
                    if (cd.getArtist().equalsIgnoreCase(artistName)) {
                        cd.printDetails();
                    }
                }
            }
        }

        public void searchVideosByDirector(String directorName) {
            System.out.println("=== Videos by: " + directorName + " ===");
            for (MediaItem item : items) {
                if (item instanceof Video) {
                    Video v = (Video) item;
                    if (v.getDirector().equalsIgnoreCase(directorName)) {
                        v.printDetails();
                    }
                }
            }
        }
    }

    // ===== Tasks =====

    private static void runTask1() {
        System.out.println("========== TASK 1 ==========");
        Database db = new Database();

        CD cd1 = new CD("Hybrid Theory", "Linkin Park", 12, 37);
        CD cd2 = new CD("Thriller", "Michael Jackson", 9, 42);

        Video v1 = new Video("Inception", "Christopher Nolan", 148);
        Video v2 = new Video("The Matrix", "The Wachowskis", 136);

        db.addItem(cd1);
        db.addItem(cd2);
        db.addItem(v1);
        db.addItem(v2);

        db.listAll();
        System.out.println();
    }

    private static void runTask2() {
        System.out.println("========== TASK 2 ==========");
        Database db = new Database();

        CD cd = new CD("Random Access Memories", "Daft Punk", 13, 75);
        db.addItem(cd);

        System.out.println("Before adding comment:");
        db.listAll();

        cd.setComment("Amazing production");
        cd.setOwned(true);

        System.out.println();
        System.out.println("After adding comment on the same CD object:");
        db.listAll();
        System.out.println();
    }

    private static void runTask3() {
        System.out.println("========== TASK 3 ==========");
        CD cd = new CD("Back in Black", "AC/DC", 10, 42);

        System.out.println("Artist: " + cd.getArtist());
        System.out.println("Tracks: " + cd.getTrackCount());

        cd.setComment("Rock classic");
        cd.setOwned(true);

        System.out.println("Owned?   " + cd.isOwned());
        System.out.println("Comment: " + cd.getComment());

        System.out.println();
        cd.printDetails();
        System.out.println();
    }

    private static void runTask4() {
        System.out.println("========== TASK 4 ==========");
        Database db = new Database();

        VideoGame g1 = new VideoGame("The Witcher 3", "PC", 1, 120);
        VideoGame g2 = new VideoGame("FIFA 25", "PS5", 4, 60);

        g1.setComment("Super fun");
        g1.setOwned(true);

        db.addItem(g1);
        db.addItem(g2);

        db.listVideoGames();
        System.out.println();
    }

    private static void runTask5() {
        System.out.println("========== TASK 5 ==========");
        Database db = new Database();

        CD cd = new CD("Nevermind", "Nirvana", 13, 49);
        cd.setComment("Grunge classic");
        cd.setOwned(true);

        Video video = new Video("Interstellar", "Christopher Nolan", 169);
        video.setComment("Epic movie");

        VideoGame game = new VideoGame("Elden Ring", "PC", 1, 100);
        game.setComment("Challenging but rewarding");

        db.addItem(cd);
        db.addItem(video);
        db.addItem(game);

        db.listAll();
        db.searchCDsByArtist("Nirvana");
        db.searchVideosByDirector("Christopher Nolan");
        db.searchByTitle("Inter");

        db.removeItem(cd);
        System.out.println();
        System.out.println("After removing one CD:");
        db.listAll();
        System.out.println();
    }

    public static void main(String[] args) {
        runTask1();
        runTask2();
        runTask3();
        runTask4();
        runTask5();
    }
}
