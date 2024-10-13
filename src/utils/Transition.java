package utils;

import game.Game;

/*
 * Written by Nicholas Cercos
 * Created on Oct 12 2024
 */
public class Transition {

	private int y;
	private final int endY, speed;
	private final boolean ascending;

	public Transition(int startY, int endY, int speed, boolean ascending) {
		this.endY = endY;
		this.speed = Game.scale(speed);
		this.ascending = ascending;
		y = startY;
	}

	/**
	 * Update the location for the transition.
	 */
	public void update() {
		if(ascending) y -= speed;
		else y += speed;
		if(isComplete()) y = endY;
	}

	/**
	 * @return True if the transition has reached the end.
	 */
	public boolean isComplete() {
		return ascending ? y <= endY : y >= endY;
	}

	public int getY() {
		return y;
	}
}
