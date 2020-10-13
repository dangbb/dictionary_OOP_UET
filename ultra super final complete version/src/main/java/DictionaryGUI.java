import DataType.Word;
import Sound.SoundManager;
import TrieTree.TrieTree;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;

public class DictionaryGUI {
    private JPanel panel1;
    private JScrollPane scrollPaneSearch;
    private JLabel labelSearch;
    private JLabel labelDefinition;
    private JTextField textFieldSearch;
    private JButton changeButton;
    private JButton deleteButton;
    private JButton soundButton;
    private JTextField textKeyword;
    private JFormattedTextField textPronun;
    private JPanel panelTop;
    private JButton addButton;
    private JList<Word> listSimilarWord;
    private JTextArea textMeaning;
    private JButton nightMode;
    private JButton translateDocButton;
    private JButton loadData;
    private JButton saveDataButton;
    private String currentSearchInput = "";
    private Word currentWord = null;

    private JFXPanel jfxp = new JFXPanel();

    /** init. */
    public DictionaryGUI() {
        DefaultListModel<Word> listWord = new DefaultListModel<>();
        textMeaning.setEnabled(false);
        textMeaning.setColumns(5);
        soundButton.setEnabled(false);

        textFieldSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                String text = textFieldSearch.getText();
                textFieldSearch.setText(text);

                Update(e, listWord);
                listSimilarWord.setModel(listWord);
            }
        });


        listSimilarWord.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Word chosenWord = listSimilarWord.getSelectedValue();
                currentWord = chosenWord;
                if (chosenWord != null && (chosenWord.getWordAudioLink() == null || chosenWord.getWordAudioLink().equals(""))) {
                    soundButton.setEnabled(false);
                } else {
                    soundButton.setEnabled(true);
                }
                if (chosenWord == null) {
                    textKeyword.setText("");
                    textMeaning.setText("");
                } else {
                    textKeyword.setText(chosenWord.getWordTarget());
                    textMeaning.setText(chosenWord.getWordPronun() + "\n" + chosenWord.getWordExpland());
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddGUI add = new AddGUI();
                add.pack();
                add.setLocation(500, 300);
                add.setVisible(true);
                textFieldSearch.setText("");
                listWord.clear();
                currentSearchInput = "";
                currentWord = null;
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeGUI change = new ChangeGUI();
                change.pack();
                change.setLocation(500, 300);
                change.setVisible(true);
                textFieldSearch.setText("");
                listWord.clear();
                currentSearchInput = "";
                currentWord = null;
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteGUI delete = new DeleteGUI();
                delete.pack();
                delete.setLocation(500, 300);
                delete.setVisible(true);
                textFieldSearch.setText("");
                listWord.clear();
                currentSearchInput = "";
                currentWord = null;
            }
        });
        translateDocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextTranslator tt = new TextTranslator();
                tt.setTitle("Text Translator");
                tt.pack();
                tt.setLocation(400, 400);
                tt.setVisible(true);
            }
        });
        nightMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nightMode nm = new nightMode();
                nm.setTitle("Night Mode");
                nm.pack();
                nm.setLocation(500, 500);
                nm.setVisible(true);
            }
        });
        loadData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData ld = new loadData();
                ld.setTitle("Load Data");
                ld.pack();
                ld.setLocation(500, 500);
                ld.setVisible(true);
            }
        });
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentWord == null) {
                    return;
                } else {
                    SoundManager.Play(currentWord.getWordAudioLink());
                }
            }
        });
        saveDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData sd = new saveData();
                sd.setTitle("Save Data");
                sd.pack();
                sd.setLocation(500, 500);
                sd.setVisible(true);
            }
        });
    }

    /** for real-time suggest dictionary. */
    public void Update(KeyEvent e, DefaultListModel<Word> listWord) {
        if (32 <= e.getKeyCode() && e.getKeyCode() <= 126) {
            if (e.getKeyCode() == 32) {
                currentSearchInput = currentSearchInput + " ";
            } else {
                if ('A' <= e.getKeyCode() && e.getKeyCode() <= 'Z')
                {
                    currentSearchInput = currentSearchInput + (char) (e.getKeyCode() - 'A' + 'a');
                }
                else
                {
                    currentSearchInput = currentSearchInput + e.getKeyChar();
                }
            }
        } else if (e.getKeyCode() == 8) {
            if (!currentSearchInput.isEmpty()) {
                currentSearchInput = currentSearchInput.substring(0, currentSearchInput.length() - 1);
            }
        }

        listWord.clear();

        if (!currentSearchInput.equals("")) {
            ArrayList<Word> similarWord = TrieTree.searchWord(currentSearchInput);

            for (Word word : similarWord) {
                listWord.add(listWord.getSize(), word);
            }
        }
    }

    /** run this class to execute the Dictionary. */
    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new DictionaryGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Dictionary");
        TrieTree.importFromFile("");
        SoundManager.updateSoundPath("");
        frame.setVisible(true);
        frame.setSize(360, 420);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
}
