import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound{
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/music/Beetlejuice.wav");
        soundURL[1] = getClass().getResource("/music/Ughsoundeffect.wav");
        soundURL[2] = getClass().getResource("/music/key.wav");
        soundURL[3] = getClass().getResource("/music/coin.wav");
        soundURL[4] = getClass().getResource("/music/sword.wav");
        soundURL[5] = getClass().getResource("/music/ice.wav");
        soundURL[6] = getClass().getResource("/music/level.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void playMusic(int i) {
        setFile(i);
        play();
        loop();
    }

    public void stopMusic() {
        stop();
    }

    public void playSE(int i) {
        setFile(i);
        play();
    }
}