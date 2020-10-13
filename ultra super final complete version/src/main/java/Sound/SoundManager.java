package Sound;

import DataType.Word;
import TrieTree.TrieTree;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class SoundManager {
    private static final String defaultPath = "C:\\Users\\Phong Vu\\IdeaProjects\\dataset\\BritishAudio";
    private static String currentAudio = null;
    private boolean isInvoke = false;

    /** change sound path. */
    public static void updateSoundPath(String path) {
        if (path.equals("")) {
            path = defaultPath;
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            File folder = new File(path + "\\" + Character.toString(c));
            File[] files = folder.listFiles();

            assert files != null;
            for (File f : files) {
                String name = f.getName().toLowerCase();
                name = name.substring(0, name.length() - 4);

                ArrayList<Word> words = TrieTree.searchWord(name);

                if (words.size() == 0) {
                    continue;
                } else if (words.get(0).getWordTarget().length() == name.length()) {
                    words.get(0).setWordAudioLink(f.getAbsolutePath());
                }
            }
        }
    }

    /** play audio. */
    public static void Play(String audio) {
        try {
            if (audio == null || audio.equals("")) {
                JOptionPane.showMessageDialog(null, "Unable to find corresponding sound file !");
                return;
            }
            SoundMedia.Play(audio);
        } catch (Exception e) {
            System.out.println("Sound Manager Exception " + e);
        }
    }

    /** main. */
    public static void main(String[] args) {
        updateSoundPath("");
    }
}
