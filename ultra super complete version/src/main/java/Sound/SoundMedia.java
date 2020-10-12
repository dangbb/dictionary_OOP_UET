package Sound;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class SoundMedia {

    private static boolean isLaunch = false;

    public static void Play(String address) {
        try {
            Media hit = new Media(new File(address).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Sound Media Exception " + e);
        }
    }

    public static void main(String[] args) {
        JFXPanel f = new JFXPanel();
        Play("src/data/a cappella.mp3");
    }
}