package DataType;

public class Word {
    private String wordTarget;
    private String wordExpland;
    private String wordPronun;
    private String wordAudioLink;

    /** default initialize for Word.*/
    public Word(String wordTarget, String wordExpland) {
        wordTarget = wordTarget.toLowerCase();

        this.wordTarget = wordTarget;
        this.wordExpland = wordExpland;
    }

    /** extended initialize. */
    public Word(String wordTarget, String wordExpland, String wordPronun, String wordAudioLink) {
        wordTarget = wordTarget.toLowerCase();

        this.wordTarget = wordTarget;

        if (!wordExpland.equals("")) {
            this.wordExpland = wordExpland;
        } else {
            this.wordExpland = null;
        }
        if (!wordAudioLink.equals("")) {
            this.wordAudioLink = wordAudioLink;
        } else {
            this.wordAudioLink = null;
        }
        if (!wordPronun.equals("")) {
            this.wordPronun = wordPronun;
        } else {
            this.wordPronun = null;
        }
    }

    /** one of the set function. */
    public void setWordTarget(String wordTarget) {
        wordTarget = wordTarget.toLowerCase();
        this.wordTarget = wordTarget;
    }

    /** one of the set function. */
    public void setWordAudioLink(String wordAudioLink) {
        this.wordAudioLink = wordAudioLink;
    }

    /** one of the set function. */
    public void setWordExpland(String wordExpland) {
        this.wordExpland = wordExpland;
    }

    /** one of the set function. */
    public void setWordPronun(String wordPronun) {
        this.wordPronun = wordPronun;
    }

    /** one of the get function. */
    public String getWordAudioLink() {
        return wordAudioLink;
    }

    /** one of the get function. */
    public String getWordExpland() {
        return wordExpland;
    }

    /** one of the get function. */
    public String getWordPronun() {
        return wordPronun;
    }

    /** one of the get function. */
    public String getWordTarget() {
        return wordTarget;
    }

    /** C'mon, you know how this function worl.*/
    public String toString() {
        return wordTarget;
    }
}