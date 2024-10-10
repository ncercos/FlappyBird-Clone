package world;

import game.Game;
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

	private final Game game;
	private final Animation animation;
	private double vy;

	public Bird(Game game, double x, double y) {
		super(x, y, BIRD_WIDTH / 2, BIRD_HEIGHT/ 2);
		this.game = game;
		animation = new Animation("birds/" + getBirdColor(), BIRD_WIDTH, BIRD_HEIGHT, 4);
	}

	public Bird(Game game) {
		this(game, 0, 0);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);

		g.drawImage(animation.getCurrentImage(game.getPlayingState()), (int) x, (int) y, width, height, null);
	}

	public void move() {
		vy += GRAVITY;
		y += vy;
		y = Math.max(y, 0);
	}

	public void jump() {
		vy += -9 * Game.GAME_SCALE;
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

	public Animation getAnimation() {
		return animation;
	}
}
