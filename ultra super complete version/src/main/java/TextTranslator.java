import javax.swing.*;
import java.awt.event.*;

import TtfUtils.ttf;
import TranslateAPI.Translate;

public class TextTranslator extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea TextInput;
    private JTextArea MeaningOutput;
    private JButton soundButton;
    private ttf textToSpeech = new ttf();

    public TextTranslator() {
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
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = TextInput.getText();

                textToSpeech.sayWords(input);
            }
        });
    }

    private void onOK() {
        // add your code here
        String text = TextInput.getText();
        while (text.charAt(text.length() - 1) == '\n') {
            text = text.substring(0, text.length() - 1);
        }
        try {
            MeaningOutput.setText(Translate.translatetext(text));
        } catch (Exception e) {
            System.out.println("Unable to Connect to the intenet");
            JOptionPane.showMessageDialog(null, "Unable to connect to the Internet");
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        TextTranslator dialog = new TextTranslator();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
