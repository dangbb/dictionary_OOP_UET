import DataType.Word;

import javax.swing.*;
import java.awt.event.*;

public class AddGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField wordTarget;
    private JTextField definition;
    private JTextArea isOK;

    private String word = "";
    private boolean isDubplicate = false;

    public AddGUI() {
        setTitle("Add DataType.Word");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.setEnabled(false);

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

        wordTarget.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                updateTarget(e);
            }
        });
        definition.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        });
    }

    private void onOK() {
        Word newWord = new Word(word, definition.getText());
        TrieTree.addWord(newWord);

        wordTarget.setText("");
        definition.setText("");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void updateTarget(KeyEvent e) {
        if (32 <= e.getKeyCode() && e.getKeyCode() <= 126) {
            if (e.getKeyCode() == 32) {
                word = word + " ";
            } else {
                if ('A' <= e.getKeyCode() && e.getKeyCode() <= 'Z') {
                    word = word + (char) (e.getKeyCode() - 'A' + 'a');
                } else {
                    word = word + e.getKeyChar();
                }
            }
        } else if (e.getKeyCode() == 8) {
            if (!word.isEmpty()) {
                word = word.substring(0, word.length() - 1);
            }
        }

        isDubplicate = TrieTree.isExisted(word);

        if (isDubplicate) {
            isOK.setText("Invalid word: Dubplicated !");
            buttonOK.setEnabled(false);

        } else if (word.equals("")) {
            isOK.setText("Invalid word: Blank !");
            buttonOK.setEnabled(false);
        } else {
            isOK.setText("Valid DataType.Word !");
            buttonOK.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        Word word = new Word("dang", "deptrai");
        TrieTree.addWord(word);

        AddGUI dialog = new AddGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
