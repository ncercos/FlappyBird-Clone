package game.states;

import game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class MenuState extends State {

	private final BufferedImage logoImg;
	private int bgFloorOffset;


	private double logoHeight = (Game.GAME_HEIGHT / 4.0);
	private double logoVelocity = 0.5;
	private final int LOGO_BOUNCE_HEIGHT = (int) (logoHeight * 2);
	private final int LOGO_FLOOR_HEIGHT = (int) (logoHeight / 2);

	public MenuState(Game game) {
		super(game);
		logoImg = Game.loadSprite("logo.png");
	}

	@Override
	public void update() {
		bgFloorOffset += (int) (5 * Game.GAME_SCALE);
		logoHeight += logoVelocity;

		if(logoHeight >= LOGO_FLOOR_HEIGHT || logoHeight <= LOGO_BOUNCE_HEIGHT)
			logoVelocity = -logoVelocity;
	}

	@Override
	public void draw(Graphics g) {
		game.getWorldManager().drawBackground(g, bgFloorOffset);
		g.drawImage(logoImg, (int) (21 * Game.GAME_SCALE), (int) logoHeight, (int) (((float) logoImg.getWidth() / 3) * Game.GAME_SCALE),
				(int) (((float) logoImg.getHeight() / 3) * Game.GAME_SCALE), null);
	}

	@Override
	public void lostFocus() {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}
}
