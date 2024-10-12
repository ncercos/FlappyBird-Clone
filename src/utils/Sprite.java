package utils;

import game.Game;

import java.awt.image.BufferedImage;

/*
 * Written by Nicholas Cercos
 * Created on Oct 12 2024
 */
public class Sprite {

	private final BufferedImage img;
	private final int width, height;

	public Sprite(String path) {
		img = Game.loadSprite(path);
		assert img != null;
		width = Game.scale(img.getWidth());
		height = Game.scale(img.getHeight());
	}

	public BufferedImage getImg() {
		return img;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public static class Bounce {

		private final double y, amplitude, frequency;
		private double time;

		public Bounce(double y, double amplitude, double frequency) {
			this.y = y;
			this.amplitude = amplitude;
			this.frequency = frequency;
			time = 0;
		}

		/**
		 * Generic bounce used for logo and ready state.
		 */
		public Bounce(int y) {
			this.y = y;
			amplitude = 15;
			frequency = 0.125;
			time = 0;
		}

		/**
		 * Track bounce via time.
		 */
		public void update() {
			time += frequency;
		}

		/**
		 * @return The height of the bounce at the current time.
		 */
		public int getBounceY() {
			return (int) (y + (int) (Math.sin(time) * amplitude));
		}
	}
}
