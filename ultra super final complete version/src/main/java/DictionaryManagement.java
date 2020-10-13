import DataType.Word;

import java.util.Scanner;

public class DictionaryManagement {

    public static String path = "dictionaries.txt";
    public static String out_path = "dictionaries.out";

    /** like statement. */
    public static void insertFromCommandLine() {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());

        System.out.println(n);

        for (int i = 0; i < n; i++) {
            String word_target = sc.nextLine();
            String word_expland = sc.nextLine();

            Word newWord = new Word(word_target, word_expland);
            DictionaryUtils.dict.addWord(newWord);
        }
        sc.close();
    }

    /** like statement. */
    public static void dictionaryLookup() {
        System.out.print("Type the word you're looking for: ");
        Scanner sc = new Scanner(System.in);

        String keyword = sc.nextLine();

        Word a = DictionaryUtils.dict.findWord(keyword);

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

    /** like statement. */
    public static void insertFromFile() {
        DictionaryUtils.dict.importFromFile(path);
    }

    /** like statement. */
    public static void fixWord(String wordTarget, String newWordExpland){
        Word word = DictionaryUtils.dict.findWord(wordTarget);

        word.setWordExpland(newWordExpland);
    }

    /** like statement. */
    public static void eraseWord(String wordTarget){
        DictionaryUtils.dict.eraseWord(wordTarget);
    }

    /** like statement. */
    public static void dictionatyExportToFile() {
        DictionaryUtils.dict.exportToFile(out_path);
    }
}