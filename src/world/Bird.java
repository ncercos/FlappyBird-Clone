package world;

import audio.Audio;
import game.Game;
import game.states.PlayingState;
import utils.Animation;
import utils.Hitbox;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Written by Nicholas Cercos
 * Created on Oct 09 2024
 */
public class Bird extends Hitbox {

	private static final int BIRD_WIDTH = 34;
	private static final int BIRD_HEIGHT = 24;
	private static final double GRAVITY = 0.5 * Game.GAME_SCALE;
	private static final double MAX_TILT = Math.toRadians(8) * Game.GAME_SCALE;
	private static final double DEATH_TILT = Math.toRadians(-90);

	private final Game game;
	private Animation animation;
	private double vy;
	private boolean dead;

	public Bird(Game game, double x, double y) {
		super(x, y, BIRD_WIDTH / 2, BIRD_HEIGHT/ 2);
		this.game = game;
		changeColor();
	}

	public Bird(Game game) {
		this(game, 0, 0);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if(animation == null)return;

		PlayingState state = game.getPlayingState();

		if(state != null) {
			Graphics2D g2d = (Graphics2D) g;
			double tilt = Math.max(state.isGameOver() ? -DEATH_TILT : -MAX_TILT, Math.min(dead ? DEATH_TILT : MAX_TILT, vy * 0.1));
			if (state.isGameOver()) animation.setCurrent(1);

			g2d.translate((int) x + width / 2, (int) y + height / 2);
			g2d.rotate(tilt);
			g2d.drawImage(animation.getCurrentImage(state, false), -width / 2, -height / 2, width, height, null);

			g2d.rotate(-tilt);
			g2d.translate(-(int) x - width / 2, -(int) y - height / 2);
		} else g.drawImage(animation.getCurrentImage(null, false), (int) x, (int) y, width, height, null);
	}

	/**
	 * Reset attributes for new round.
	 */
	public void reset() {
		x = 0;
		y = 0;
		vy = 0;
		dead = false;
		changeColor();
	}

	/**
	 * Apply physics, velocity, and ensure bird cannot jump over screen.
	 */
	public void move() {
		if(dead)return;
		vy += GRAVITY;
		y += vy;
		y = Math.max(y, 0);
		if(overlaps(game.getWorldManager().getGroundHB())) {
			dead = true;
			y = game.getWorldManager().getGroundHB().getY() - height;
		}
	}

	/**
	 * Jump upwards.
	 */
	public void jump() {
		vy = Game.scale(-5);
		game.getAudioManager().playSound(Audio.WING);
	}

	/**
	 * Randomly chooses the bird's color: yellow, red, or blue.
	 */
	public void changeColor() {
		animation = new Animation("birds/" + getBirdColor(), BIRD_WIDTH, BIRD_HEIGHT, 4);
	}

	/**
	 * Get the color of the bird randomly.
	 *
	 * @return The name of the colored bird.
	 */
	private String getBirdColor() {
		switch (ThreadLocalRandom.current().nextInt(3) + 1) {
			default -> { return "yellow"; }
			case 2 -> { return "red"; }
			case 3 -> { return "blue"; }
		}
	}

	public Game getGame() {
		return game;
	}

	public boolean isDead() {
		return dead;
	}
}
