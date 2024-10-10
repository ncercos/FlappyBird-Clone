package world;

import game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class WorldManager {

	private final Game game;

	private final boolean daytime;
	private final int GROUND_WIDTH, GROUND_HEIGHT, GROUND_DRAW_HEIGHT;
	private int groundX, groundSpeed;

	private BufferedImage backgroundImg;
	private BufferedImage groundImg;

	public WorldManager(Game game) {
		this.game = game;
		daytime = ThreadLocalRandom.current().nextBoolean();
		loadSprites();

		GROUND_WIDTH = Game.scale(groundImg.getWidth());
		GROUND_HEIGHT = Game.scale(groundImg.getHeight());
		GROUND_DRAW_HEIGHT = Game.GAME_HEIGHT - GROUND_HEIGHT;
		groundX = 0;
		groundSpeed = 5;
	}

	/**
	 * Draws the background scene.
	 *
	 * @param g The graphics context.
	 */
	public void drawBackground(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		groundX -= groundSpeed;
		if(groundX <= -GROUND_WIDTH) groundX = 0;
		g.drawImage(groundImg, groundX, GROUND_DRAW_HEIGHT, GROUND_WIDTH, GROUND_HEIGHT, null);
		g.drawImage(groundImg, groundX + GROUND_WIDTH, GROUND_DRAW_HEIGHT, GROUND_WIDTH, GROUND_HEIGHT, null);
	}

	/**
	 * Loads the sky and ground sprites to build a complete background.
	 */
	private void loadSprites() {
		final String PATH = "background/";
		backgroundImg = Game.loadSprite(PATH + "sky/" + (daytime ? "day" : "night") + ".png");
		groundImg = Game.loadSprite(PATH + "ground.png");
	}

	/**
	 * Updates ground speed value. (Default = 5)
	 *
	 * @param groundSpeed The speed the ground should move.
	 */
	public void setGroundSpeed(int groundSpeed) {
		this.groundSpeed = groundSpeed;
	}
}
