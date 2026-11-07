package com.blockburst.audio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {

    private static final String SOUND_FOLDER = "assets/sounds/";

    private final Map<String, Clip> clips = new HashMap<>();
    private boolean muted = false;

    public SoundManager() {
        loadClip("select");
        loadClip("place");
        loadClip("clear");
        loadClip("invalid");
    }

    private void loadClip(String name) {
        File file = new File(SOUND_FOLDER + name + ".wav");

        if (!file.exists()) {
            System.err.println("Sound file not found: " + file.getPath());
            return;
        }

        Clip clip = null;
        try (AudioInputStream audio = AudioSystem.getAudioInputStream(file)) {
            clip = AudioSystem.getClip();
            clip.open(audio);
            clips.put(name, clip);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException | IllegalArgumentException e) {
            if (clip != null) {
                clip.close();
            }
            System.err.println("Could not load sound " + name + ": " + e.getMessage());
        }
    }

    public void play(String name) {
        if (muted) {
            return;
        }

        Clip clip = clips.get(name);

        if (clip == null) {
            return;
        }

        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void toggleMute() {
        muted = !muted;

        if (muted) {
            stopAll();
        }
    }

    public boolean isMuted() {
        return muted;
    }

    private void stopAll() {
        for (Clip clip : clips.values()) {
            clip.stop();
        }
    }
}
