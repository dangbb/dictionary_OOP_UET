import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
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

    public Word findWord(String keyword) {
        for (int i = 0; i < word_num; i++) {
            if (words[i].getWordTarget().equals(keyword) == true) {
                return words[i];
            }
        }
        return null;
    }

    public void eraseWord(String keyword) {
        for (int i = 0; i < word_num; i++) {
            if(words[i].getWordTarget().equals(keyword) == true) {
                for (int j = i; j < word_num - 1; j++) {
                    words[j] = words[j + 1];
                }
                break;
            }
        }
    }

    public List<Word> findSimilarWord(String keyword) {
        List<Word> similarWord = new ArrayList<Word>();

        for (int i = 0; i < word_num; i++) {
            String wordTarget = words[i].getWordTarget();

            if (isPrefix(keyword, wordTarget) == true) {
                similarWord.add(words[i]);
            }
        }

        return similarWord;
    }

    public static boolean isPrefix(String pre, String word) {
        if (word.length() < pre.length()) {
            return false;
        }

        for (int i = 0; i < pre.length(); i++) {
            if (pre.charAt(i) != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public void exportToFile(String path) {
        try {
            FileWriter fil = new FileWriter(path);
        
            for (int i = 0; i < word_num; i++) {
                fil.write(words[i].getWordTarget() + "\t" + words[i].getWordExpland() + "\n");    
            }

            fil.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importFromFile(String path) {
        try {
            BufferedReader buf = new BufferedReader(new FileReader(path));
            String newLine = null;

            while (true) {
                newLine = buf.readLine();
                if (newLine == null) {
                    break;
                }
                else 
                {
                    String[] wordsArray = newLine.split("\t");

                    for (int i = 0; i < wordsArray.length; i += 2)
                    {
                        System.out.println(wordsArray[i] + " " + wordsArray[i + 1]);
                        Word newWord = new Word(wordsArray[i], wordsArray[i + 1]);
                        dict.addWord(newWord);
                    }
                }
            }
            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}

class DictionaryManagement {

    public static String path = "dictionaries.txt";
    public static String out_path = "dictionaries.out";

    public static void insertFromCommandLine() {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());

        System.out.println(n);

        for (int i = 0; i < n; i++) {
            String word_target = sc.nextLine();
            String word_expland = sc.nextLine();

            Word newWord = new Word(word_target, word_expland);
            Dictionary.dict.addWord(newWord);
        }
        sc.close();
    }

    public static void dictionaryLookup() {
        System.out.print("Type the word you're looking for: ");
        Scanner sc = new Scanner(System.in);

        String keyword = sc.nextLine();

        Word a = Dictionary.dict.findWord(keyword);

        if (a != null)
        {
            System.out.println("The meaning of " + keyword + " is: " + a.getWordExpland());
        }
        else 
        {
            System.out.println("Can't find the word " + keyword + "! ");
        }

        sc.close();
    }

    public static void insertFromFile() {
        Dictionary.dict.importFromFile(path);
    }

    public static void fixWord(String wordTarget, String newWordExpland){
        Word word = Dictionary.dict.findWord(wordTarget);

        word.setWordExpland(newWordExpland);
    }

    public static void eraseWord(String wordTarget){
        Dictionary.dict.eraseWord(wordTarget);
    }

    public static void dictionatyExportToFile() {
        Dictionary.dict.exportToFile(out_path);
    }
}

class DictionaryCommandLine {
    public static void showAllWords() {
        Dictionary.dict.showWord();
    }

    public static void dictionarySearcher() {
        System.out.print("Type a word or prefix: ");
        Scanner sc = new Scanner(System.in);
        String keyword = sc.nextLine();

        List<Word> similarWord = Dictionary.dict.findSimilarWord(keyword);

        System.out.println("Words that similar to what you're looking for: ");
        for (Word w : similarWord) {
            System.out.println(w.getWordTarget());
        }
        sc.close();
    }

    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandLine();
        showAllWords();
    }

    public static void dictionaryAdvanced() {
        DictionaryManagement.insertFromFile();
        showAllWords();
        DictionaryManagement.dictionaryLookup();
    }

    public static void dictionatyTesting() {
        DictionaryManagement.insertFromFile();
        showAllWords();
        DictionaryManagement.fixWord("Dante", "I luv ya");
        DictionaryManagement.eraseWord("Thursday");
        dictionarySearcher();
        DictionaryManagement.dictionatyExportToFile();
    }
}

public class TestDrive {
    public static void main(String[] args) {
        DictionaryCommandLine.dictionatyTesting();
    }
}
