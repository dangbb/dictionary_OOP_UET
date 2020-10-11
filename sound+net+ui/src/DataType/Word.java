package DataType;

public class Word {
    private String wordTarget;
    private String wordExpland;
    private String wordPronun;
    private String wordAudioLink;

    public Word(String wordTarget, String wordExpland) {
        wordTarget = wordTarget.toLowerCase();

        this.wordTarget = wordTarget;
        this.wordExpland = wordExpland;
    }

    public Word(String wordTarget, String wordExpland, String wordPronun, String wordAudioLink) {
        wordTarget = wordTarget.toLowerCase();

        this.wordTarget = wordTarget;
        this.wordExpland = wordExpland;
        this.wordAudioLink = wordAudioLink;
        this.wordPronun = wordPronun;
    }

    public void setWordTarget(String wordTarget) {
        wordTarget = wordTarget.toLowerCase();
        this.wordTarget = wordTarget;
    }

    public void setWordAudioLink(String wordAudioLink) {
        this.wordAudioLink = wordAudioLink;
    }

    public void setWordExpland(String wordExpland) {
        this.wordExpland = wordExpland;
    }

    public void setWordPronun(String wordPronun) {
        this.wordPronun = wordPronun;
    }

    public String getWordAudioLink() {
        return wordAudioLink;
    }

    public String getWordExpland() {
        return wordExpland;
    }

    public String getWordPronun() {
        return wordPronun;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String toString() {
        return wordTarget;
    }
}