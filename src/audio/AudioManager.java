package audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
		try (InputStream is = Objects.requireNonNull(getClass().getResourceAsStream("/sounds/" + fileName + ".wav"));
				 BufferedInputStream bis = new BufferedInputStream(is)) {

			AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			return clip;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}

}
