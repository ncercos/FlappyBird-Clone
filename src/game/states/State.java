package game.states;

import game.Game;
import game.GameState;
import ui.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public abstract class State {

	protected final Game game;
	protected final List<Button> buttons;

	public State(Game game) {
		this.game = game;
		buttons = new ArrayList<>();
	}

	public abstract void update();
	public abstract void onDraw(Graphics g);
	public abstract void lostFocus();
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	public abstract void mouseClicked(MouseEvent e);
	protected abstract void onMousePress(MouseEvent e);
	protected abstract void onMouseRelease(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mouseDragged(MouseEvent e);

	public void draw(Graphics g) {
		onDraw(g);
		buttons.forEach(b -> b.draw(g));
	}

	public void mousePressed(MouseEvent e) {
		for (Button button : buttons) button.onMousePress(e);
		onMousePress(e);
	}

	public void mouseReleased(MouseEvent e) {
		for (Button button : buttons) button.onMouseRelease(e);
		onMouseRelease(e);
	}

	/**
	 * Unregisters a button.
	 *
	 * @param name The (file) name of the button.
	 */
	public void unregisterButton(String name) {
		Iterator<Button> it = buttons.iterator();
		while (it.hasNext()) {
			Button button = it.next();
			if(!button.getName().equalsIgnoreCase(name))continue;
			it.remove();
		}
	}

	/**
	 * Sets the state of the game.
	 *
	 * @param state The new game state.
	 */
	public void setState(GameState state) {
		GameState.current = state;
	}

	public Game getGame() {
		return game;
	}
}
