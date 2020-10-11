import DataType.Word;
import TrieTree.TrieTree;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;

public class DeleteGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<Word> similarList;
    private JTextArea definitionField;
    private JTextField wordTextField;
    private JLabel targetField;

    private DefaultListModel<Word> wordModel = new DefaultListModel<>();
    private String currentWordTarget = "";
    private Word chosenWord = null;

    public DeleteGUI() {
        buttonOK.setEnabled(false);
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

        wordTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                update(e, true);
            }
        });


        similarList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                chosenWord = similarList.getSelectedValue();
                if (chosenWord != null) {
                    targetField.setText(chosenWord.getWordTarget());
                    definitionField.setText(chosenWord.getWordExpland());
                    buttonOK.setEnabled(true);
                }
            }
        });
    }

    private void update(KeyEvent e, boolean updateScrollList) {
        if (32 <= e.getKeyCode() && e.getKeyCode() <= 126) {
            if (e.getKeyCode() == 32) {
                currentWordTarget = currentWordTarget + " ";
            } else {
                if ('A' <= e.getKeyCode() && e.getKeyCode() <= 'Z') {
                    currentWordTarget = currentWordTarget + (char) (e.getKeyCode() - 'A' + 'a');
                } else {
                    currentWordTarget = currentWordTarget + e.getKeyChar();
                }
            }
        } else if (e.getKeyCode() == 8) {
            if (!currentWordTarget.isEmpty()) {
                currentWordTarget = currentWordTarget.substring(0, currentWordTarget.length() - 1);
            }
        }

        if (!currentWordTarget.equals("") && updateScrollList) {
            wordModel.clear();
            ArrayList<Word> wordlist = TrieTree.searchWord(currentWordTarget);

            for (Word word : wordlist) {
                wordModel.add(wordModel.getSize(), word);
            }

            similarList.setModel(wordModel);
        }
    }

    private void onOK() {
        // add your code here
        if (chosenWord != null) {
            TrieTree.eraseWord(chosenWord.getWordTarget());
            wordModel.remove(similarList.getSelectedIndex());
            similarList.setModel(wordModel);

            targetField.setText("");
            definitionField.setText("");
            chosenWord = null;
        }

        buttonOK.setEnabled(false);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        DeleteGUI dialog = new DeleteGUI();
        dialog.pack();
        dialog.setVisible(true);
    }
}
