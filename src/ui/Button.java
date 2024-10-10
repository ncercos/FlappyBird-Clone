package ui;

import game.Game;
import game.states.State;
import utils.Hitbox;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/*
 * Written by Nicholas Cercos
 * Created on Oct 10 2024
 */
public abstract class Button extends Hitbox {

	protected State state;
	protected String name;
	protected BufferedImage img;
	private boolean pressed;
	private final int PRESS_DEPTH;

	public Button(State state, String fileName, double x, double y, int width, int height) {
		super(x, y, width, height);
		this.state = state;
		name = fileName;
		img = Game.loadSprite("ui/buttons/" + name + ".png");
		PRESS_DEPTH = 2;
	}

	public Button(String fileName, double x, double y, int width, int height) {
		this(null, fileName, x, y, width, height);
	}

	protected abstract void onPress();
	protected abstract void onRelease();

	/**
	 * Handles button press.
	 *
	 * @param e The mouse event.
	 */
	public void onMousePress(MouseEvent e) {
		if(contains(e.getX(), e.getY())) {
			pressed = true;
			y += PRESS_DEPTH;
			onPress();
		}
	}

	/**
	 * Handles mouse release.
	 *
	 * @param e The mouse event.
	 */
	public void onMouseRelease(MouseEvent e) {
		if(pressed) {
			pressed = false;
			y -= PRESS_DEPTH;
			onRelease();
		}
	}

	/**
	 * Draws button to the screen.
	 *
	 * @param g The scene graphics context.
	 */
	public void draw(Graphics g) {
		g.drawImage(img, (int) x, (int) y, width, height, null);
	}

	public State getState() {
		return state;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getImg() {
		return img;
	}
}
