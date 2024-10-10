package utils;

import game.Game;

import java.awt.*;

/*
 * Written by Nicholas Cercos
 * Created on Oct 09 2024
 */
public class Hitbox {

	protected double x, y;
	protected final int width, height;
	protected boolean debug;

	public Hitbox(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = Game.scale(width);
		this.height = Game.scale(height);
	}

	/**
	 * Render hitbox for debugging purposes.
	 *
	 * @param g The scene graphics context.
	 */
	public void draw(Graphics g) {
		if(!debug)return;
		g.setColor(Color.PINK);
		g.drawRect((int) x, (int) y, width, height);
	}

	/**
	 * Determines if two hitboxes are overlapping.
	 *
	 * @param hb The other hitbox in question.
	 * @return True if hitboxes are colliding.
	 */
	public boolean overlaps(Hitbox hb) {
		return (x <= hb.x + hb.width)  &&
				   (x + width >= hb.x)     &&
				   (y <= hb.y + hb.height) &&
				   (y + height >= hb.y);
	}

	/**
	 * Checks if the mouse collides with hitbox.
	 *
	 * @param mx The x-coordinate.
	 * @param my The y-coordinate.
	 * @return True if the point is within hitbox.
	 */
	public boolean contains(int mx, int my) {
		return (mx >= x  )       &&
				   (mx <= x + width) &&
				   (my >= y  )       &&
				   (my <= y + height);
	}

	/**
	 * Teleport hitbox to a specific location.
	 *
	 * @param location The location to teleport to.
	 */
	public void teleport(Location location) {
		if(location == null)return;
		x = location.getX();
		y = location.getY();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
