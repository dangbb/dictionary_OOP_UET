import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.*;
import java.util.ArrayList;

public class TrieTree {
    private TrieTree[] descendant = new TrieTree[28];
    private static TrieTree root = new TrieTree();
    private Word word;
    private int descendantNum = 0;
    private static int limitWord = 50;

    private static int charOrder(char c) {
        if (c == ' ' || c == '"') {
            return 0;
        } else if ('a' <= c && c <= 'z'){
            return c - 'a' + 1;
        } else {
            return 'z' - 'a' + 2;
        }
    }

    TrieTree() {
        word = null;
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
            System.out.println("Add Word exception as " + e + " for word " + a.getWordTarget());
        }
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

        for (int i = 0; i < 'z' - 'a' + 1; i++) {
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

    public static boolean LoadFromFile(String path) {
        return true;
    }
    public static boolean SaveToFile(String path) {
        return true;
    }

    public static boolean exportToFile(String path) {
        try {
            FileWriter fil = new FileWriter(path);

            /// implement later

            fil.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean importFromFile(String path) {
        if (path.equals("")) {
            /*path = "src/DictionaryInCsv";

            File f = new File(path);
            File[] files = f.listFiles();

            System.out.println(files);

            if (files != null) {
                for (File file : files) {
                    System.out.println(file.getPath());
                }
            }
            return true;*/

            try {
                path = "src/DictionaryInCsv/Y.csv";
                BufferedReader buf = new BufferedReader(new FileReader(path));
                String line = "";

                while ((line = buf.readLine()) != null) {
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == ' ') {
                            String word_target = line.substring(0, i);
                            String word_expland = line.substring(i);
                            addWord(new Word(word_target, word_expland));
                            break;
                        }
                    }
                }
                return true;
            } catch (Exception e) {
                System.out.println("IO Exception as " + e);
                return false;
            }

        }
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
                        DictionaryUtils.dict.addWord(newWord);
                    }
                }
            }
            buf.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
