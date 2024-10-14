package utils;

import game.Game;

/*
 * Written by Nicholas Cercos
 * Created on Oct 13 2024
 */
public class Delay {

	private final int duration, speed;
	private int current;
	private boolean active;

	public Delay(int duration) {
		this.duration = Game.scale(duration);
		speed = Game.scale(1);
		current = duration;
		active = true;
	}

	/**
	 * Decrement the delay overtime.
	 */
	public void update() {
		current -= speed;
	}

	/**
	 * Reset the delay timer.
	 */
	public void reset() {
		current = duration;
		active = true;
	}

	/**
	 * @return True if the delay is no longer active.
	 */
	public boolean isComplete() {
		return current < 0;
	}

	/**
	 * @return True if the delay can still be called upon.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return True if the delay timer has started.
	 */
	public boolean hasStarted() {
		return current < duration;
	}

	/**
	 * Disable this delay from being used.
	 */
	public void deactivate() {
		active = false;
	}
}
