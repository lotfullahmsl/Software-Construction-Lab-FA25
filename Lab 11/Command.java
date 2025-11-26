package zuul;

public abstract class Command {
    protected Game game;
    private String secondWord;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public boolean hasSecondWord() {
        return secondWord != null && !secondWord.isEmpty();
    }

    public abstract String execute();
}