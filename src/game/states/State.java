package game.states;

import game.Game;
import game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public abstract class State {

	protected final Game game;

	public State(Game game) {
		this.game = game;
	}

	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void lostFocus();
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	public abstract void mouseClicked(MouseEvent e);
	public abstract void mousePressed(MouseEvent e);
	public abstract void mouseReleased(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mouseDragged(MouseEvent e);

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
