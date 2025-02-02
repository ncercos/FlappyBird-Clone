package inputs;

import game.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class MouseInputs implements MouseListener, MouseMotionListener {

	private final Game game;

	public MouseInputs(Game game) {
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(game.getCurrentState() == null)return;
		game.getCurrentState().mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(game.getCurrentState() == null)return;
		game.getCurrentState().mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(game.getCurrentState() == null)return;
		game.getCurrentState().mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(game.getCurrentState() == null)return;
		game.getCurrentState().mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(game.getCurrentState() == null)return;
		game.getCurrentState().mouseMoved(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
