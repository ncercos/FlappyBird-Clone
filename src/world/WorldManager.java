package world;

import game.Game;
import utils.Hitbox;

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
	private boolean daytime;
	private final int GROUND_WIDTH, GROUND_HEIGHT, GROUND_DRAW_HEIGHT;
	private int groundX, groundSpeed;
	private final List<Pipe> pipes;

	private final Hitbox groundHB;

	public WorldManager(Game game) {
		this.game = game;
		calcDayOrNight();
		loadSprites();

		GROUND_WIDTH = Game.scale(groundImg.getWidth());
		GROUND_HEIGHT = Game.scale(groundImg.getHeight());
		GROUND_DRAW_HEIGHT = Game.GAME_HEIGHT - GROUND_HEIGHT;
		groundX = 0;
		groundSpeed = Game.scale(1.75);
		groundHB = new Hitbox(0, Game.scale(200), 144, 60);

		pipes = new ArrayList<>();
	}

	/**
	 * Randomly choose day or night for background visual.
	 */
	public void calcDayOrNight() {
		daytime = ThreadLocalRandom.current().nextBoolean();
	}

	public void reset() {
		calcDayOrNight();
		loadBackgroundSprite();
		pipes.clear();
		groundX = 0;
	}

	/**
	 * Draws the background scene.
	 *
	 * @param g The graphics context.
	 */
	public void drawBackground(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		if(game.getPlayingState() == null || (!game.getPlayingState().isGameOver() && !game.getPlayingState().isPaused())) {
			groundX -= groundSpeed;
			if (groundX <= -GROUND_WIDTH) groundX = 0;
		}
		if(game.getPlayingState() != null && game.getPlayingState().isReady()) drawPipes(g);
		g.drawImage(groundImg, groundX, GROUND_DRAW_HEIGHT, GROUND_WIDTH, GROUND_HEIGHT, null);
		g.drawImage(groundImg, groundX + GROUND_WIDTH, GROUND_DRAW_HEIGHT, GROUND_WIDTH, GROUND_HEIGHT, null);
		groundHB.draw(g);
	}

	/**
	 * Place top and bottom pipes at random heights but equal intervals.
	 */
	public void placePipes() {
		// Top Pipe
		Pipe topPipe = new Pipe(topPipeImg, groundSpeed);
		int randomY = (int) (topPipe.getY() - topPipe.getHeight() / 4.0 - Math.random() * (topPipe.getHeight() / 2.0));
		topPipe.setY(randomY);
		pipes.add(topPipe);

		// Bottom Pipe
		Pipe bottomPipe = new Pipe(bottomPipeImg, groundSpeed);
		int openingSpace = Game.GAME_HEIGHT / 5;
		bottomPipe.setY(topPipe.getY() + bottomPipe.getHeight() + openingSpace);
		pipes.add(bottomPipe);
		System.out.println("placing pipe");
	}

	/**
	 * Move all pipes down the world.
	 */
	public boolean movePipes() {
		for(int i = 0; i < pipes.size(); i++) {
			Pipe pipe = pipes.get(i);
			pipe.move();

			if(pipe.overlaps(game.getBird())) return true;
			if(!pipe.hasPassed() && game.getBird().getX() > (pipe.getX() + pipe.getWidth() / 2.0)) {
				pipe.passed();
				game.getScore().increase();
			}
		}
		return false;
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
	 * Loads the background based on world time.
	 */
	private void loadBackgroundSprite() {
		backgroundImg = Game.loadSprite("background/sky/" + (daytime ? "day" : "night") + ".png");
	}

	/**
	 * Loads ground and pipe sprites.
	 */
	private void loadSprites() {
		final String PATH = "pipes/";
		topPipeImg = Game.loadSprite(PATH + "top.png");
		bottomPipeImg = Game.loadSprite(PATH + "bottom.png");
		groundImg = Game.loadSprite("background/ground.png");
		loadBackgroundSprite();
	}

	/**
	 * Updates ground speed value. (Default = 5)
	 *
	 * @param groundSpeed The speed the ground should move.
	 */
	public void setGroundSpeed(int groundSpeed) {
		this.groundSpeed = groundSpeed;
	}

	public Hitbox getGroundHB() {
		return groundHB;
	}
}
