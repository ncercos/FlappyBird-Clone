package game.states;

import game.Game;
import ui.buttons.PauseButton;
import ui.buttons.ScoreButton;
import ui.buttons.StartButton;
import ui.buttons.UnpauseButton;
import utils.Location;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/*
 * Written by Nicholas Cercos
 * Created on Oct 10 2024
 */
public class PlayingState extends State {

	private BufferedImage readyImg, overImg, instructionImg;
	private final int READY_WIDTH, READY_HEIGHT, INSTRUCTION_WIDTH, INSTRUCTION_HEIGHT;
	private boolean ready, paused;
	private PauseButton pauseButton;
	private UnpauseButton unpauseButton;

	public PlayingState(Game game) {
		super(game);
		loadTextSprites();
		initializeButtons();
		updatePauseButton();

		READY_WIDTH = Game.scale(readyImg.getWidth());
		READY_HEIGHT = Game.scale(readyImg.getHeight());
		INSTRUCTION_WIDTH = Game.scale(instructionImg.getWidth());
		INSTRUCTION_HEIGHT = Game.scale(instructionImg.getHeight());
	}

	/**
	 * Loads all user-interface menu-related texts.
	 */
	private void loadTextSprites() {
		final String PATH = "ui/text/";
		readyImg = Game.loadSprite(PATH + "get_ready.png");
		//overImg = Game.loadSprite(PATH + "game_over.png");
		instructionImg = Game.loadSprite("ui/instruction.png");
	}

	/**
	 * Register all buttons for this state.
	 */
	private void initializeButtons() {
		int xy = Game.scale(10);
		pauseButton = new PauseButton(this, xy, xy);
		unpauseButton = new UnpauseButton(this, xy, xy);
	}

	/**
	 * Updates pause button to display proper type based on game status.
	 */
	private void updatePauseButton() {
		if(paused) {
			buttons.remove(pauseButton);
			buttons.add(unpauseButton);
		} else {
			buttons.remove(unpauseButton);
			buttons.add(pauseButton);
		}
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
		updatePauseButton();
	}

	@Override
	public void update() {
		if(ready)return;
		game.getBird().teleport(new Location(Game.scale(35), Game.scale(100)));
	}

	@Override
	public void onDraw(Graphics g) {
		game.getWorldManager().drawBackground(g);
		if(!ready) {
			g.drawImage(readyImg, (Game.GAME_WIDTH / 2) - (READY_WIDTH / 2), (Game.GAME_HEIGHT / 4), READY_WIDTH, READY_HEIGHT, null);
			g.drawImage(instructionImg, (Game.GAME_WIDTH / 2) - (INSTRUCTION_WIDTH / 2) + Game.scale(8), (Game.GAME_HEIGHT / 4) + Game.scale(49), INSTRUCTION_WIDTH, INSTRUCTION_HEIGHT, null);
		}
		game.getBird().draw(g);
	}

	@Override
	public void lostFocus() {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() != KeyEvent.VK_SPACE)return;
		if(!ready) ready = true;
	}

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
