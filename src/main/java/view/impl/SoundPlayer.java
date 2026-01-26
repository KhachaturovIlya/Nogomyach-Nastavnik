package view.impl;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundPlayer {
    private final Map<String, Clip> sounds = new HashMap<>();
    private final Path audioPath;
    private float volume = -20.f;

    public SoundPlayer(Path audioPath) {
        this.audioPath = audioPath;
    }

    @SuppressWarnings("resource")
    private void loadSound(String name) {
        File file = audioPath.resolve(name + ".wav").toFile();
        if (!file.exists()) {
            sounds.put(name, null);
            System.err.println("Sound file not found: " + file.getAbsolutePath());
            return;
        }

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                control.setValue(volume);
            }

            sounds.put(name, clip);
        } catch (Exception e) {
            sounds.put(name, null);
            System.err.println("Error loading [" + name + ".wav]");
        }
    }

    public void play(String name) {
        Clip clip = sounds.get(name);
        if (clip == null) {
            loadSound(name);
            clip = sounds.get(name);
            if (clip == null) {
                return;
            }
        }

        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void setVolume(float volume) {
        this.volume = volume;

        for (Clip clip : sounds.values()) {
            if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                control.setValue(volume);
            }
        }
    }

    public void close() {
        for (Clip clip : sounds.values()) {
            if (clip.isOpen()) {
                clip.close();
            }
        }
        sounds.clear();
    }
}
