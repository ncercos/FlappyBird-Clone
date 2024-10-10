package utils;

import game.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
 * Written by Nicholas Cercos
 * Created on Oct 09 2024
 */
public class Animation {

	private Image[] images;
	private final int spriteWidth, spriteHeight, duration;
	private int current, delay;

	public Animation(String name, int spriteWidth, int spriteHeight, int duration) {
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.duration = duration;
		reset();

		try {
			images = loadImages(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Start animation from the beginning.
	 */
	public void reset(boolean updateCurrent) {
		if(updateCurrent) current = 0;
		delay = duration;
	}

	public void reset() {
		reset(true);
	}

	/**
	 * Loads all the frames for an animation.
	 *
	 * @param name The filePath for the animation sprite.
	 * @throws IOException If the file cannot be accessed/found.
	 */
	public Image[] loadImages(String name) throws IOException {
		BufferedImage sprite = ImageIO.read(new File(Game.RESOURCE_URL + name + ".png"));
		final int WIDTH = sprite.getWidth() / spriteWidth;
		Image[] images = new Image[WIDTH];

		for(int i = 0; i < WIDTH; i++)
			images[i] = sprite.getSubimage(i * spriteWidth, 0, spriteWidth, spriteHeight);
		return images;
	}

	/**
	 * @return The current image within the animation.
	 */
	public Image getCurrentImage() {
		if(images == null) return null;
		delay--;
		if(delay == 0) {
			current++;
			reset(current == images.length);
		}
		return images[current];
	}

	/**
	 * @return The default sprite for still frame.
	 */
	public Image getStaticImage() {
		return images != null ? images[0] : null;
	}
}
