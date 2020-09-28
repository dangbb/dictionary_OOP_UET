import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

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

    public DictionaryGUI() {
        DefaultListModel<Word> listWord = new DefaultListModel<>();
        textMeaning.setEnabled(false);
        textMeaning.setColumns(5);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showInputDialog(null, "Hello World");
            }
        });

        textFieldSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                String text = textFieldSearch.getText();
                textFieldSearch.setText(text);

                DictionaryUtils.dict.Update(e, listWord);
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
                JOptionPane option = new JOptionPane();
                JOptionPane.showInputDialog(null, "Hello");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new DictionaryGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Dictionary");
        frame.setVisible(true);
        frame.setSize(360, 420);
        frame.pack();
        frame.setResizable(false);

        TrieTree.importFromFile("");
        Word a = new Word("anhyeuemnhatma", "dieu");
        TrieTree.addWord(a);
    }
}
