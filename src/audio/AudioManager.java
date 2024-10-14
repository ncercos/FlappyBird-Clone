package audio;

import game.Game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * Written by Nicholas Cercos
 * Created on Oct 13 2024
 */
public class AudioManager {

	private final Map<Audio, Clip> clips;

	public AudioManager() {
		clips = new HashMap<>();
		loadSoundEffects();
	}

	/**
	 * Plays audio from the beginning.
	 *
	 * @param clip The audio clip to be played.
	 */
	private void playSound(Clip clip) {
		if(clip == null)return;
		clip.stop();
		clip.setMicrosecondPosition(0);
		clip.start();
	}

	public void playSound(Audio audio) {
		playSound(clips.getOrDefault(audio, null));
	}

	/**
	 * Load all sound effects for the game.
	 */
	private void loadSoundEffects() {
		for(Audio audio : Audio.values()) {
			Clip clip = getClip(audio.getFileName());
			if(clip == null)continue;
			clips.put(audio, clip);
		}
	}

	/**
	 * Get an audio clip to be played.
	 *
	 * @param fileName The name of the audio file.
	 * @return A clip that sounds the sound effect.
	 */
	private Clip getClip(String fileName) {
		File file = new File(Game.RESOURCE_URL + "sounds/" + fileName + ".wav");
		AudioInputStream ais;

		try {
			ais = AudioSystem.getAudioInputStream(file);
			Clip c = AudioSystem.getClip();
			c.open(ais);
			return c;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
}
