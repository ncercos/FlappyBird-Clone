package ui;

import utils.Sprite;

/*
 * Written by Nicholas Cercos
 * Created on Oct 13 2024
 */
public enum Medal {

	BRONZE, SILVER, GOLD, PLATINUM;

	private final Sprite sprite;
	private final int minimumScore;

	Medal() {
		sprite = new Sprite("ui/medals/" + name().toLowerCase() + ".png");
		minimumScore = (ordinal() + 1) * 10;
	}

	/**
	 * Get the medal for a given score.
	 *
	 * @param score The current score.
	 * @return A medal, if deserved. (Score must reach minimum)
	 */
	public static Medal getMedal(int score) {
		Medal medal = null;
		for(Medal m : values()) {
			if(score >= m.getMinimumScore())
				medal = m;
		}
		return medal;
	}

	public int getMinimumScore() {
		return minimumScore;
	}

	public Sprite getSprite() {
		return sprite;
	}
}
