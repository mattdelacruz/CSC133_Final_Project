package rainmaker;

import javafx.scene.media.AudioClip;

public class AudioClipPlayer {

    AudioClip audioClip;

    public AudioClipPlayer(String soundLoc) {
        audioClip = new AudioClip(soundLoc);
        audioClip.setVolume(0.1);
    }

    public void play() {
        audioClip.play();
    }

    public void stop() {
        audioClip.stop();
    }

    public void setVolume(double v) {
        audioClip.setVolume(v);
    }

}
