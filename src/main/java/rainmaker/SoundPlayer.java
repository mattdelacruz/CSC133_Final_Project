package rainmaker;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {
    Media sound;
    MediaPlayer soundPlayer;

    public SoundPlayer(String soundLoc, int cycleCount) {
        sound = new Media(soundLoc);
        soundPlayer = new MediaPlayer(sound);
        soundPlayer.setCycleCount(cycleCount);
        soundPlayer.setVolume(0.1);
    }

    public void play() {
        soundPlayer.play();
    }

    public void stop() {
        soundPlayer.stop();
    }

    public void setVolume(double v) {
        soundPlayer.setVolume(v);
    }
}
