import java.util.Scanner;

class Word {
    private String word_target;
    private String word_expland;

    Word(String word_target, String word_expland) {
        this.word_expland = word_expland;
        this.word_target = word_target;
    }

    public void setWordTarget(String word_target) {
        this.word_target = word_target;
    }

    public void setWordExpland(String word_expland) {
        this.word_expland = word_expland;
    }

    public String getWordTarget()
    {
        return word_target;
    }

    public String getWordExpland() 
    {
        return word_expland;
    }
}

class Dictionary {
    private Word[] words = new Word[1000];
    private int word_num = 0;

    public static Dictionary dict = new Dictionary();

    public void addWord(Word word) {
        words[word_num] = word;
        ++word_num;
    }

    public void showWord() {
        System.out.println("No    | English                | Vietnamese");
        for (int i = 0; i < word_num; i++) {
            System.out.println(Integer.toString(i + 1) + "    " + words[i].getWordTarget() + "             " + words[i].getWordExpland());
        }
    }
}

class DictionaryManagement {

    public static void insertFromCommandLine() {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            String word_target = sc.nextLine();
            String word_expland = sc.nextLine();

            Word newWord = new Word(word_target, word_expland);
            Dictionary.dict.addWord(newWord);
        }
    }
}

class DictionaryCommandLine {
    public static void showAllWords() {
        Dictionary.dict.showWord();
    }

    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandLine();
        showAllWords();
    }
}

public class TestDrive {
    public static void main(String[] args) {
        DictionaryCommandLine.dictionaryBasic();
    }
}
