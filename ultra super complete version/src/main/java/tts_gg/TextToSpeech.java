package tts_gg;

import com.google.cloud.texttospeech.v1.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TextToSpeech {
    public static void speak(String sentences, String langCode) throws Exception {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = SynthesisInput.newBuilder().setText(sentences).build();

            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode(langCode)
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .build();

            AudioConfig audioConfig = AudioConfig
                    .newBuilder()
                    .setAudioEncoding(AudioEncoding.LINEAR16)
                    .build();

            SynthesizeSpeechResponse response = textToSpeechClient
                    .synthesizeSpeech(input, voice, audioConfig);

            InputStream stream = new ByteArrayInputStream(response.getAudioContent().toByteArray());
            AudioInputStream sound = AudioSystem.getAudioInputStream(stream);

            Clip clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
        }
    }

    public static void main(String[] args) {
        try {
            speak("Hello, Yenes loves Dung", "en");
        } catch (Exception e) {
            System.out.println("text to Speech " + e);
        }
    }
}
