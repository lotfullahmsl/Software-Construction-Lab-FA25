public class Video extends MediaItem {
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
