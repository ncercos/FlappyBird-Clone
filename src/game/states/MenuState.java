package game.states;

import game.Game;
import ui.buttons.ScoreButton;
import ui.buttons.StartButton;
import utils.Location;
import utils.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class MenuState extends State {

	private Sprite logoSprite, authorSprite;
	private final Sprite.Bounce logoBounce;

	public MenuState(Game game) {
		super(game);
		loadTextSprites();
		registerButtons();
		logoBounce = new Sprite.Bounce(Game.GAME_HEIGHT / 4);
	}

	/**
	 * Loads all user-interface menu-related texts.
	 */
	private void loadTextSprites() {
		final String PATH = "ui/text/";
		logoSprite = new Sprite(PATH + "title.png");
		authorSprite = new Sprite(PATH + "author.png");
	}

	/**
	 * Register all buttons for this state.
	 */
	private void registerButtons() {
		buttons.add(new StartButton(this, (Game.GAME_WIDTH / 4.0) - Game.scale(15), Game.scale(180)));
		buttons.add(new ScoreButton(this, (Game.GAME_WIDTH / 4.0) + Game.scale(47), Game.scale(180)));
	}

	@Override
	public void update() {
		logoBounce.update();
	}

	@Override
	public void onDraw(Graphics g) {
		game.getWorldManager().drawBackground(g);
		game.getBird().teleport(new Location(Game.GAME_WIDTH / 2.0 + Game.scale(42), logoBounce.getBounceY() + Game.scale(2.5)));
		g.drawImage(logoSprite.getImg(), ((Game.GAME_WIDTH / 2) - (logoSprite.getWidth() / 2)) - (Game.scale(10)), logoBounce.getBounceY(),
				logoSprite.getWidth(), logoSprite.getHeight(), null);
		g.drawImage(authorSprite.getImg(), (Game.GAME_WIDTH / 2) - (authorSprite.getWidth() / 2), Game.GAME_HEIGHT - Game.scale(47),
				authorSprite.getWidth(), authorSprite.getHeight(), null);
		game.getBird().draw(g);
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
	protected void onMousePress(MouseEvent e) {}

	@Override
	protected void onMouseRelease(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}
}
