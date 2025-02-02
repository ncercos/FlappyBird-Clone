package utils;

/*
 * Written by Nicholas Cercos
 * Created on Oct 09 2024
 */
public class Location {

	private double x, y;

	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Update x and y coordinates.
	 *
	 * @param x The new x-location.
	 * @param y The new y-location.
	 */
	public void update(double x, double y) {
		setX(x);
		setY(y);
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
}
