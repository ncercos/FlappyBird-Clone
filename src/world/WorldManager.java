package world;

import game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class WorldManager {

	private final Game game;
	private BufferedImage bgSkyImg;
	private BufferedImage bgFloorImg;

	public WorldManager(Game game) {
		this.game = game;
		loadBackgroundSprites();
	}

	/**
	 * Draws the background scene.
	 *
	 * @param g       The graphics context.
	 * @param offsetX The offset to determine floor location (mimics scrolling).
	 */
	public void drawBackground(Graphics g, int offsetX) {
		final int SKY_HEIGHT = (int) (bgSkyImg.getHeight() * Game.GAME_SCALE);
		final int FLOOR_WIDTH = (int) (bgFloorImg.getWidth() * Game.GAME_SCALE);

		g.drawImage(bgSkyImg, 0, 0, (int) (bgSkyImg.getWidth() * Game.GAME_SCALE), SKY_HEIGHT, null);

		for (int i = 0; i <= 3; i++) {
			int xPosition = (i * FLOOR_WIDTH - offsetX) % (3 * FLOOR_WIDTH); // X-Pos for each floor tile
			g.drawImage(bgFloorImg, xPosition, SKY_HEIGHT, FLOOR_WIDTH, (int) (bgFloorImg.getHeight() * Game.GAME_SCALE), null);

			// Draw an additional floor tile to fill any gap that might appear
			if (xPosition + FLOOR_WIDTH < Game.WIDTH)
				g.drawImage(bgFloorImg, xPosition + 3 * FLOOR_WIDTH, SKY_HEIGHT, FLOOR_WIDTH, (int) (bgFloorImg.getHeight() * Game.GAME_SCALE), null);
		}
	}

	/**
	 * Loads the top (sky) and bottom (floor) of the background separately.
	 */
	private void loadBackgroundSprites() {
		final String PATH = "background/";
		bgSkyImg = Game.loadSprite(PATH + "sky.png");
		bgFloorImg = Game.loadSprite(PATH + "floor.png");
	}
}
