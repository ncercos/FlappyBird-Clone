package utils;

import game.Game;

/*
 * Written by Nicholas Cercos
 * Created on Oct 12 2024
 */
public class Transition {

	private int y;
	private final int endY, increment;
	private final boolean ascending;

	public Transition(int startY, int endY, int increment, boolean ascending) {
		this.endY = endY;
		this.increment = Game.scale(increment);
		this.ascending = ascending;
		y = startY;
	}

	/**
	 * Update the location for the transition.
	 */
	public void update() {
		if(ascending) y -= increment;
		else y += increment;
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
