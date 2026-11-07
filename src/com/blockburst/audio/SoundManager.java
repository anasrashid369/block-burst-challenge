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

    private void loadClip(String name) {
        File file = new File(SOUND_FOLDER + name + ".wav");

        try (AudioInputStream audio = AudioSystem.getAudioInputStream(file)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clips.put(name, clip);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException | IllegalArgumentException e) {
            System.err.println("Could not load sound " + name + ": " + e.getMessage());
        }
    }
}
