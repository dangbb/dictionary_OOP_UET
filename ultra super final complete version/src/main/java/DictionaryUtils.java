import DataType.Word;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

public class DictionaryUtils {
    private Word[] words = new Word[1000];
    private int word_num = 0;

    public static DictionaryUtils dict = new DictionaryUtils();

    /** like statement. */
    public void addWord(Word word) {
        words[word_num] = word;
        ++word_num;
    }

    /** like statement. */
    public void showWord() {
        System.out.println("No    | English                | Vietnamese");
        for (int i = 0; i < word_num; i++) {
            System.out.println(Integer.toString(i + 1) + "    " + words[i].getWordTarget() + "             " + words[i].getWordExpland());
        }
    }

    /** like statement. */
    public Word findWord(String keyword) {
        for (int i = 0; i < word_num; i++) {
            if (words[i].getWordTarget().equals(keyword)) {
                return words[i];
            }
        }
        return null;
    }

    /** like statement. */
    public void eraseWord(String keyword) {
        for (int i = 0; i < word_num; i++) {
            if(words[i].getWordTarget().equals(keyword)) {
                if (word_num - 1 - i >= 0) {
                    System.arraycopy(words, i + 1, words, i, word_num - 1 - i);
                }
                break;
            }
        }
    }

    /** like statement. */
    public List<Word> findSimilarWord(String keyword) {
        List<Word> similarWord = new ArrayList<Word>();

        for (int i = 0; i < word_num; i++) {
            String wordTarget = words[i].getWordTarget();

            if (isPrefix(keyword, wordTarget)) {
                similarWord.add(words[i]);
            }
        }

        return similarWord;
    }

    /** like statement. */
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

    /** like statement. */
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

    /** like statement. */
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