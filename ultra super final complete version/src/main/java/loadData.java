import Sound.SoundManager;
import TrieTree.TrieTree;

import javax.swing.*;
import java.awt.event.*;

public class loadData extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dictPath;
    private JTextField audioPath;

    /** like name. */
    public loadData() {
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
    }

    /** ok. */
    private void onOK() {
        // add your code here
        String dataPath = dictPath.getText();

        TrieTree.importFromFile(dataPath);

        String soundPath = audioPath.getText();

        SoundManager.updateSoundPath(soundPath);

        dispose();
    }

    /** not ok. */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /** main. */
    public static void main(String[] args) {
        loadData dialog = new loadData();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
