package game.states;

import game.Game;
import utils.Location;
import world.Bird;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class MenuState extends State {

	private BufferedImage logoImg;
	private BufferedImage authorImg;

	private final int LOGO_WIDTH, LOGO_HEIGHT, AUTHOR_WIDTH, AUTHOR_HEIGHT;
	private double time, amplitude, frequency, logoY;

	private final Bird bird;

	public MenuState(Game game) {
		super(game);
		loadTextSprites();

		LOGO_WIDTH = Game.scale(logoImg.getWidth());
		LOGO_HEIGHT = Game.scale(logoImg.getHeight());
		AUTHOR_WIDTH = Game.scale(authorImg.getWidth());
		AUTHOR_HEIGHT = Game.scale(authorImg.getHeight());

		time = 0;
		amplitude = 15;
		frequency = 0.125;
		logoY = Game.GAME_HEIGHT / 4.0;
		bird = new Bird();
	}

	@Override
	public void draw(Graphics g) {
		game.getWorldManager().drawBackground(g);
		int bounceY = (int) (logoY + (int) (Math.sin(time) * amplitude));
		bird.teleport(new Location(Game.GAME_WIDTH / 2.0 + Game.scale(42), bounceY + 10));
		g.drawImage(logoImg, ((Game.GAME_WIDTH / 2) - (LOGO_WIDTH / 2)) - (Game.scale(10)), bounceY , LOGO_WIDTH, LOGO_HEIGHT, null);
		g.drawImage(authorImg, (Game.GAME_WIDTH / 2) - (AUTHOR_WIDTH / 2), Game.GAME_HEIGHT - Game.scale(47),
				AUTHOR_WIDTH, AUTHOR_HEIGHT, null);
		bird.draw(g);
	}

	/**
	 * Loads all user-interface menu-related texts.
	 */
	private void loadTextSprites() {
		final String PATH = "ui/text/";
		logoImg = Game.loadSprite(PATH + "title.png");
		authorImg = Game.loadSprite(PATH + "author.png");
	}

	@Override
	public void update() {
		time += frequency;
	}

	@Override
	public void lostFocus() {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}
}
