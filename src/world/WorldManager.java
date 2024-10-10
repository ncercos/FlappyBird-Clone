package world;

import game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class WorldManager {

	private final Game game;
	private BufferedImage backgroundImg, groundImg, topPipeImg, bottomPipeImg;
	private final boolean daytime;
	private final int GROUND_WIDTH, GROUND_HEIGHT, GROUND_DRAW_HEIGHT;
	private int groundX, groundSpeed;
	private final List<Pipe> pipes;

	public WorldManager(Game game) {
		this.game = game;
		daytime = ThreadLocalRandom.current().nextBoolean();
		loadSprites();

		GROUND_WIDTH = Game.scale(groundImg.getWidth());
		GROUND_HEIGHT = Game.scale(groundImg.getHeight());
		GROUND_DRAW_HEIGHT = Game.GAME_HEIGHT - GROUND_HEIGHT;
		groundX = 0;
		groundSpeed = 5;

		pipes = new ArrayList<>();
	}

	/**
	 * Draws the background scene.
	 *
	 * @param g The graphics context.
	 */
	public void drawBackground(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		if(game.getPlayingState() == null || !game.getPlayingState().isPaused()) {
			groundX -= groundSpeed;
			if (groundX <= -GROUND_WIDTH) groundX = 0;
		}
		if(game.getPlayingState() != null && game.getPlayingState().isReady()) drawPipes(g);
		g.drawImage(groundImg, groundX, GROUND_DRAW_HEIGHT, GROUND_WIDTH, GROUND_HEIGHT, null);
		g.drawImage(groundImg, groundX + GROUND_WIDTH, GROUND_DRAW_HEIGHT, GROUND_WIDTH, GROUND_HEIGHT, null);
	}

	/**
	 * Place top and bottom pipes at random heights but equal intervals.
	 */
	public void placePipes() {
		// Top Pipe
		Pipe topPipe = new Pipe(topPipeImg);
		int randomY = (int) (topPipe.getY() - topPipe.getHeight() / 4.0 - Math.random() * (topPipe.getHeight() / 2.0));
		topPipe.setY(randomY);
		pipes.add(topPipe);

		// Bottom Pipe
		Pipe bottomPipe = new Pipe(bottomPipeImg);
		int openingSpace = Game.GAME_HEIGHT / 4;
		bottomPipe.setY(topPipe.getY() + bottomPipe.getHeight() + openingSpace);
		pipes.add(bottomPipe);
	}

	/**
	 * Move all pipes down the world.
	 */
	public void movePipes() {
		for(int i = 0; i < pipes.size(); i++)
			pipes.get(i).move();
	}

	/**
	 * Draw active pipes to screen.
	 *
	 * @param g The graphics context.
	 */
	private void drawPipes(Graphics g) {
		for(int i = 0; i < pipes.size(); i++)
			pipes.get(i).draw(g);
	}

	/**
	 * Loads the sky and ground sprites to build a complete background.
	 */
	private void loadSprites() {
		String PATH = "background/";
		backgroundImg = Game.loadSprite(PATH + "sky/" + (daytime ? "day" : "night") + ".png");
		groundImg = Game.loadSprite(PATH + "ground.png");
		PATH = "pipes/";
		topPipeImg = Game.loadSprite(PATH + "top.png");
		bottomPipeImg = Game.loadSprite(PATH + "bottom.png");
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
