package ui;

import audio.Audio;
import game.Game;
import utils.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/*
 * Written by Nicholas Cercos
 * Created on Oct 13 2024
 */
public class Score {

	private final Game game;
	private final int NUM_SPRITE_WIDTH = 7;
	private final int NUM_GAP = Game.scale(1);
	private final Map<Integer, Sprite> digits;

	private double current;
	private int currentHighest, allTimeHighest;

	public Score(Game game) {
		this.game = game;
		digits = new HashMap<>();
		setDigits(loadImages());
		reset();
	}

	/**
	 * Draws the current score to the top of the screen.
	 *
	 * @param g The graphics context.
	 */
	public void draw(Graphics g) {
		String score = String.valueOf((int)current);
		final int WIDTH = (score.length() * Game.scale(NUM_SPRITE_WIDTH)) + (NUM_GAP * (score.length() - 1));
		char[] chars = score.toCharArray();

		int x = (Game.GAME_WIDTH / 2) - (WIDTH / 2);

		for(char c : chars) {
			int digit = Character.getNumericValue(c);
			Sprite sprite = digits.get(digit);
			g.drawImage(sprite.getImg(), x, Game.scale(10), sprite.getWidth(), sprite.getHeight(), null);
			x += (Game.scale(NUM_SPRITE_WIDTH) + NUM_GAP);
		}
	}

	/**
	 * Increment score by half (two pipes [top/bottom] = whole number).
	 */
	public void increase() {
		current += 0.5;
		updateHighest((int)current);
		playSfx();
	}

	/**
	 * Play the score gain sound effect.
	 */
	public void playSfx() {
		if(current % 1 == 0)
			game.getAudioManager().playSound(Audio.SCORE);
	}

	/**
	 * Updates the highest score value if the current score exceeds limit.
	 *
	 * @param score The current score.
	 */
	public void updateHighest(int score) {
		if(score > currentHighest) currentHighest = score;
	}

	/**
	 * Updates all-time highest to current round highest (if exceeded).
	 */
	public void updateAllTimeHighest() {
		if(currentHighest > allTimeHighest)
			allTimeHighest = currentHighest;
	}

	/**
	 * Resets current score to zero. (Highest remains)
	 */
	public void reset() {
		current = 0;
	}

	/**
	 * Sets the proper image to the corresponding value.
	 *
	 * @param imgs An array of all digit sprites.
	 */
	private void setDigits(BufferedImage[] imgs) {
		for(int i = 0; i < imgs.length; i++) {
			digits.put(i, new Sprite(imgs[i]));
		}
	}

	/**
	 * Loads all digit sprites from 0-9.
	 */
	private BufferedImage[] loadImages() {
		BufferedImage sprite = Game.loadSprite("ui/numbers.png");
		assert sprite != null;
		final int WIDTH = sprite.getWidth() / NUM_SPRITE_WIDTH;
		BufferedImage[] imgs = new BufferedImage[WIDTH];

		for(int i = 0; i < WIDTH; i++)
			imgs[i] = sprite.getSubimage(i * NUM_SPRITE_WIDTH, 0, NUM_SPRITE_WIDTH, 10);
		return imgs;
	}

	public int getCurrentHighest() {
		return currentHighest;
	}

	public int getAllTimeHighest() {
		return allTimeHighest;
	}
}
