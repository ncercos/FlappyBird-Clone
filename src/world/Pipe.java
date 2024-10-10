package world;

import game.Game;
import utils.Hitbox;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
 * Written by Nicholas Cercos
 * Created on Oct 10 2024
 */
public class Pipe extends Hitbox {

	private final BufferedImage img;
	private final int vx;
	private boolean passed;

	public Pipe(BufferedImage img, int groundSpeed) {
		super(Game.GAME_WIDTH, 0, 26, 135);
		this.img = img;
		vx = -groundSpeed;
	}

	/**
	 * Draw pipe to screen.
	 *
	 * @param g The scene graphics context.
	 */
	public void draw(Graphics g) {
		g.drawImage(img, (int) x, (int) y, width, height, null);
	}

	/**
	 * Moves the pipe to the left (follows ground speed).
	 */
	public void move() {
		x += vx;
	}

	/**
	 * @return True if the bird has passed this pipe.
	 */
	public boolean hasPassed() {
		return passed;
	}

	/**
	 * Passed the pipe!
	 */
	public void passed() {
		passed = true;
	}
}
