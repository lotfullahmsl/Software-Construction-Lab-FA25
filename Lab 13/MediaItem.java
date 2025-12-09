public abstract class MediaItem {
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
