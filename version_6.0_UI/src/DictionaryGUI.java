import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
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
    private JFormattedTextField formattedTextField1;
    private JPanel panelTop;
    private JPanel panelAlign_1;
    private JButton addButton;
    private JList<Word> listSimilarWord;
    private JTextArea textMeaning;
    private JButton nightMode;
    private JPanel panelTop1;
    private JPanel panelTop2;
    private JPanel panelTop3;
    private JPanel panelTop4;
    private JButton translateDocButton;
    private String currentSearchInput = "";

    public DictionaryGUI() {
        DefaultListModel<Word> listWord = new DefaultListModel<>();
        textMeaning.setEnabled(false);
        textMeaning.setColumns(5);

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
                if (chosenWord == null) {
                    textKeyword.setText("");
                    textMeaning.setText("");
                } else {
                    textKeyword.setText(chosenWord.getWordTarget());
                    textMeaning.setText(chosenWord.getWordExpland());
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddGUI add = new AddGUI();
                add.pack();
                add.setVisible(true);
                add.setLocationRelativeTo(null);
                textFieldSearch.setText("");
                listWord.clear();
                currentSearchInput = "";
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeGUI change = new ChangeGUI();
                change.pack();
                change.setVisible(true);
                change.setLocationRelativeTo(null);
                textFieldSearch.setText("");
                listWord.clear();
                currentSearchInput = "";
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteGUI delete = new DeleteGUI();
                delete.pack();
                delete.setVisible(true);
                delete.setLocationRelativeTo(null);
                textFieldSearch.setText("");
                listWord.clear();
                currentSearchInput = "";
            }
        });
    }

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new DictionaryGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Dictionary");
        frame.setVisible(true);
        frame.setSize(360, 420);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        TrieTree.importFromFile("");
        Word a = new Word("dang dep trai", "dung vay :))");
        TrieTree.addWord(a);
    }
}
