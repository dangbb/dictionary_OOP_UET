import DataType.Word;
import TrieTree.TrieTree;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;

public class ChangeGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea newMeaning;
    private JList<Word> similarList;
    private JTextField wordTarget;
    private JTextArea oldMeaning;
    private JLabel wordName;

    private String currentTargetWord = "";
    private DefaultListModel<Word> wordModel = new DefaultListModel<>();
    private Word chosenWord = null;

    /** init. */
    public ChangeGUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        similarList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                chosenWord = similarList.getSelectedValue();
                wordName.setText(chosenWord.getWordTarget());
                oldMeaning.setText(chosenWord.getWordExpland());
            }
        });
        wordTarget.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                updateTarget(e);
            }
        });
    }

    /** for real-time suggest dictionary. */
    private void updateTarget(KeyEvent e) {
        if (32 <= e.getKeyCode() && e.getKeyCode() <= 126) {
            if (e.getKeyCode() == 32) {
                currentTargetWord = currentTargetWord + " ";
            } else {
                if ('A' <= e.getKeyCode() && e.getKeyCode() <= 'Z') {
                    currentTargetWord = currentTargetWord + (char) (e.getKeyCode() - 'A' + 'a');
                } else {
                    currentTargetWord = currentTargetWord + e.getKeyChar();
                }
            }
        } else if (e.getKeyCode() == 8) {
            if (!currentTargetWord.isEmpty()) {
                currentTargetWord = currentTargetWord.substring(0, currentTargetWord.length() - 1);
            }
        }

        wordModel.clear();
        ArrayList<Word> wordlist = TrieTree.searchWord(currentTargetWord);

        for (Word word : wordlist) {
            wordModel.add(wordModel.size(), word);
        }

        similarList.setModel(wordModel);
    }

    /** click ok. */
    private void onOK() {
        // add your code here
        if (newMeaning.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Save word without meaning !");
            dispose();
            return;
        }
        System.out.println(newMeaning.getText());
        chosenWord.setWordExpland(newMeaning.getText());
        oldMeaning.setText(chosenWord.getWordExpland());
        newMeaning.setText("");
    }

    /** click not ok. */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /** another main method. */
    public static void main(String[] args) {
        Word word = new Word("dang", "deptrai");
        TrieTree.addWord(word);
        ChangeGUI dialog = new ChangeGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
