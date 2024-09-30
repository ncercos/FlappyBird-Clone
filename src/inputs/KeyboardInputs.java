package inputs;

import game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class KeyboardInputs implements KeyListener {

	private final Game game;

	public KeyboardInputs(Game game) {
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(game.getCurrentState() == null)return;
		game.getCurrentState().keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(game.getCurrentState() == null)return;
		game.getCurrentState().keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
