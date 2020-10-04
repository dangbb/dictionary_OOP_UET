public class Word {
    private String word_target;
    private String word_expland;
    private Word post;
    private Word pre;

    Word(String word_target, String word_expland) {

        word_target = word_target.toLowerCase();

        if (word_target.charAt(0) == '"') {
            word_target = word_target.substring(1);
        }

        this.word_expland = word_expland;
        this.word_target = word_target;
        this.post = null;
        this.pre = null;
    }

    public void setPost(Word e) {
        this.post = e;
        e.pre = this;
    }

    public void setWordTarget(String word_target) {
        this.word_target = this.word_target.toLowerCase();
        this.word_target = word_target;
    }

    public void setWordExpland(String word_expland) {
        this.word_expland = word_expland;
    }

    public String getWordTarget() {
        return word_target;
    }

    public String getWordExpland() {
        return word_expland;
    }

    public String toString() {
        return word_target;
    }

    public static void Destroy(Word a) {
        Word f = a.pre;
        Word s = a.post;

        if (f != null)
        {
            f.post = s;
        }
        if (s != null)
        {
            s.pre = f;
        }
    }
}