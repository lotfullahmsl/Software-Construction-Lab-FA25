public class CD extends MediaItem {
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
