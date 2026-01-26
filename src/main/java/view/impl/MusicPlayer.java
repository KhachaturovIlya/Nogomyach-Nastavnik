package view.impl;

import javax.sound.sampled.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class MusicPlayer {
    private Thread musicPlayerThread;

    private final Path musicPath;
    private volatile boolean playing = false;
    private float volume = -30.f;

    public MusicPlayer(Path musicPath) {
        this.musicPath = musicPath;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void play(String name) {
        stop();
        playing = true;

        musicPlayerThread = new Thread(() -> {
            File file = musicPath.resolve(name + ".wav").toFile();
            while (playing) {
                try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {
                    AudioFormat audioFormat = audioInputStream.getFormat();
                    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
                    SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

                    sourceDataLine.open(audioFormat);
                    sourceDataLine.start();

                    updateVolume(sourceDataLine);

                    byte[] buffer = new byte[8192];
                    int n;
                    while (playing && (n = audioInputStream.read(buffer)) != -1) {
                        sourceDataLine.write(buffer, 0, n);
                        updateVolume(sourceDataLine);
                    }

                    sourceDataLine.drain();
                    sourceDataLine.close();
                } catch (Exception e) {
                    System.err.println("Music Player Error: " + e.getMessage());
                    playing = false;
                    break;
                }
            }

        });

        musicPlayerThread.start();
    }

    private void updateVolume(SourceDataLine sourceDataLine) {
        if (sourceDataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl control = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(volume);
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void stop() {
        playing = false;
        if (musicPlayerThread != null) {
            musicPlayerThread.interrupt();
        }
    }
}
