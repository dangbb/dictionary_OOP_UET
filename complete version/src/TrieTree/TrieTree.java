package TrieTree;

import DataType.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class TrieTree {
    private TrieTree[] descendant = new TrieTree[29];
    private static TrieTree root = new TrieTree();
    private Word word;
    private int descendantNum = 0;
    private static final int limitWord = 1000;
    private static final String defaultPath = "src/data/dict.txt";

    private static int charOrder(char c) {
        if (c == ' ' || c == '"') {
            return 0;
        } else if ('a' <= c && c <= 'z'){
            return c - 'a' + 1;
        } else {
            return 'z' - 'a' + 2;
        }
    }

    public static void addWord(Word a) {
        try {
            TrieTree currentNode = root;
            for (int i = 0; i < a.getWordTarget().length(); i++) {
                int order = charOrder(a.getWordTarget().charAt(i));

                if (currentNode.descendant[order] == null) {
                    currentNode.descendant[order] = new TrieTree();
                }
                currentNode.descendantNum++;
                currentNode = currentNode.descendant[order];
            }
            currentNode.descendantNum++;
            currentNode.word = a;
        } catch (Exception e) {
            System.out.println("Add DataType.Word exception as " + e + " for word " + a.getWordTarget());
        }
    }

    public static void addWord(String wordTarget, String wordExpland, String wordPronun, String audioLink) {
        Word word = new Word(wordTarget, wordExpland, wordPronun, audioLink);

        addWord(word);
    }

    public static boolean eraseWord(String s) {
        TrieTree currentNode = root;
        for (int i = 0; i < s.length(); i++) {

            int order = charOrder(s.charAt(i));

            if (currentNode.descendant[order] == null || currentNode.descendant[order].descendantNum == 0) {
                return false;
            } else {
                currentNode = currentNode.descendant[order];
            }
        }

        if (currentNode.word == null) {
            return false;
        }

        currentNode.descendantNum--;
        currentNode.word = null;
        return true;
    }

    public static boolean isExisted(String s) {
        TrieTree currentNode = root;
        for (int i = 0; i < s.length(); i++) {

            int order = charOrder(s.charAt(i));

            if (currentNode.descendant[order] == null || currentNode.descendant[order].descendantNum == 0) {
                return false;
            } else {
                currentNode = currentNode.descendant[order];
            }
        }

        if (currentNode.word == null) {
            return false;
        }
        return true;
    }

    public static boolean fixWord(String s, String newMeaning) {
        TrieTree currentNode = root;
        for (int i = 0; i < s.length(); i++) {
            int order = charOrder(s.charAt(i));

            if (currentNode.descendant[order] == null || currentNode.descendant[order].descendantNum == 0) {
                return false;
            } else {
                currentNode = currentNode.descendant[order];
            }
        }

        if (currentNode.word == null) {
            return false;
        }

        currentNode.word.setWordExpland(newMeaning);
        return true;
    }

    private void search(ArrayList<Word> similarWord) {
        if (this.word != null)
        {
            similarWord.add(this.word);
        }

        if (similarWord.size() >= limitWord) {
            return;
        }

        for (int i = 0; i <= 'z' - 'a' + 2; i++) {
            if (this.descendant[i] != null && this.descendant[i].descendantNum > 0) {
                this.descendant[i].search(similarWord);
            }
        }
    }

    public static ArrayList<Word> searchWord(String a) {
        ArrayList<Word> similarWord = new ArrayList<>();

        TrieTree currentNode = root;

        boolean isValid = true;

        for (int i = 0; i < a.length(); i++) {
            int order = charOrder(a.charAt(i));

            if (currentNode.descendant[order] == null || currentNode.descendant[order].descendantNum == 0) {
                isValid = false;
                break;
            } else {
                currentNode = currentNode.descendant[order];
            }
        }

        if (isValid) {
            currentNode.search(similarWord);
        }

        return similarWord;
    }

    public static boolean exportToFile(String path) {
        return true;
    }

    public static boolean importFromFile(String path) {
        if (path.equals("")) {
            path = defaultPath;
        }
        try {
            BufferedReader fr = new BufferedReader(new FileReader(path));
            LineNumberReader reader = new LineNumberReader(new FileReader(path));
            while((reader.readLine()) != null);
            reader.close();

            int lines = reader.getLineNumber();
            int count = 0;

            String line = "";

            String word = "";
            String pronun = "";
            String definition = "";
            String audioLink = "";

            while ((line = fr.readLine()) != null) {
                if (line.length() == 0 || line.equals("???")) {
                    addWord(word, definition, pronun, audioLink);
                    word = "";
                } else if (line.charAt(0) == '@') {
                    int lastPost = line.length() - 1;

                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '/') {
                            lastPost = i;
                            break;
                        }
                    }

                    word = line.substring(1, lastPost - 1);
                    if (lastPost + 1 <= line.length() - 2) {
                        pronun = line.substring(lastPost + 1, line.length() - 2);
                    } else {
                        pronun = "";
                    }
                    definition = "";
                    audioLink = "";
                } else {
                    definition = definition + "\n" + line;
                }
            }

            if (!word.equals("")) {
                addWord(word, definition, pronun, audioLink);
            }
            fr.close();
            return true;
        } catch (Exception e) {
            System.out.println("Setup/SQLSetup Exception: " + e);
            return false;
        }
    }
}
