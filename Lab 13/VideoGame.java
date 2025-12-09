public class VideoGame extends MediaItem {
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
