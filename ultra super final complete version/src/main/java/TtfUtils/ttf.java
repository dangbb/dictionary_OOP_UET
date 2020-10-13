package TtfUtils;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ttf {
    Voice freettsVoice;

    /** text to speech, that's all :) .*/
    public ttf() {
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            VoiceManager manager = VoiceManager.getInstance();
            freettsVoice = manager.getVoice("kevin16");
            freettsVoice.allocate();
        } catch (Exception e) {
            System.out.println("Cannot init TTS. Error: " + e.toString());
        }
    }

    /** MAKE IT SPEAK!! .*/
    public void sayWords(String words) {
        // Make her speak!
        freettsVoice.speak(words);
    }
}
