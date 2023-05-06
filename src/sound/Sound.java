package sound;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Sound extends JFrame {
    String soundNameFile;
    private Clip clips;

    public Sound(String soundNameFile) {
        this.soundNameFile = soundNameFile;
        try {
            File fileName = new File("res/sound/" + soundNameFile + ".wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(fileName.toURI().toURL());
            clips = AudioSystem.getClip();
            clips.open(audioIn);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        clips.setFramePosition(0);
        clips.start();
    }

    public void loop() {
        clips.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopSound() {
        clips.stop();
    }

    public static Sound main_bgm = new Sound("main_bgm");
    public static Sound bonus_stage = new Sound("bonus_stage");
    public static Sound collect_item = new Sound("collect_item");
    public static Sound dieds = new Sound("died");
    public static Sound ending = new Sound("ending");
    public static Sound explosion = new Sound("explosion");
    public static Sound game_over = new Sound("game_over");
    public static Sound miss = new Sound("miss");
    public static Sound move = new Sound("move");
    public static Sound place_bomb = new Sound("place_bomb");
    public static Sound powerup_get = new Sound("powerup_get");
    public static Sound SFX2 = new Sound("SFX2");
    public static Sound SFX4 = new Sound("SFX4");
    public static Sound SFX7 = new Sound("SFX7");
    public static Sound special_powerup_get = new Sound("special_powerup_get");
    public static Sound stage_clear = new Sound("stage_clear");
    public static Sound stage_start = new Sound("stage_start");
    public static Sound title_screen = new Sound("title_screen");
}
