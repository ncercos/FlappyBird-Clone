package audio;

/*
 * Written by Nicholas Cercos
 * Created on Oct 13 2024
 */
public enum Audio {

	DIE,
	HIT,
	SCORE,
	SWOOSH,
	WING;

	/**
	 * @return The file name of the audio track.
	 */
	public String getFileName() {
		return name().toLowerCase();
	}
}
