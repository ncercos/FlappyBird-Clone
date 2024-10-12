package game.states;

import game.Game;
import ui.buttons.PauseButton;
import ui.buttons.UnpauseButton;
import utils.Location;
import utils.Sprite;
import utils.Transition;
import world.Bird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/*
 * Written by Nicholas Cercos
 * Created on Oct 10 2024
 */
public class PlayingState extends State {

	private Sprite readyTextSprite, overTextSprite, instructionSprite, resultSprite;
	private boolean ready, paused, gameOver;
	private PauseButton pauseButton;
	private UnpauseButton unpauseButton;
	private final Timer pipeTimer;
	private final Sprite.Bounce readyBounce;
	private final Transition overTransition, resultTransition;
	private int transitionWaitTime;

	public PlayingState(Game game) {
		super(game);
		loadTextSprites();
		initializeButtons();
		updatePauseButton();
		pipeTimer = new Timer(Game.scale(437), _ -> game.getWorldManager().placePipes());
		readyBounce = new Sprite.Bounce(Game.scale(100));
		overTransition = new Transition(Game.GAME_HEIGHT / 4 - Game.scale(15), Game.GAME_HEIGHT / 4, 3, false);
		resultTransition = new Transition(Game.GAME_HEIGHT, (Game.GAME_HEIGHT / 4) + Game.scale(23), 20, true);
		transitionWaitTime = Game.scale(13);
	}

	/**
	 * Loads all user-interface menu-related texts.
	 */
	private void loadTextSprites() {
		readyTextSprite = new Sprite("ui/text/get_ready.png");
		overTextSprite = new Sprite("ui/text/game_over.png");
		instructionSprite = new Sprite("ui/instruction.png");
		resultSprite = new Sprite("ui/results.png");
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

	/**
	 * Toggle game pause.
	 *
	 * @param paused Should the game freeze/unfreeze?
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
		updatePauseButton();

		if(!ready)return;
		if(paused) pipeTimer.stop();
		else pipeTimer.start();
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isReady() {
		return ready;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	@Override
	public void update() {
		if(paused)return;
		Bird bird = game.getBird();

		if(!ready) {
			readyBounce.update();
			bird.teleport(new Location(Game.scale(35), readyBounce.getBounceY()));
			return;
		}

		bird.move();

		if(gameOver) {
			transitionWaitTime -= Game.scale(1);
			if(transitionWaitTime > 0)return;
			overTransition.update();
			resultTransition.update();
			return;
		}


		if(game.getWorldManager().movePipes() || bird.isDead())
			gameOver = true;
	}

	@Override
	public void onDraw(Graphics g) {
		game.getWorldManager().drawBackground(g);

		if(!ready) {
			g.drawImage(readyTextSprite.getImg(), (Game.GAME_WIDTH / 2) - (readyTextSprite.getWidth() / 2),
					(Game.GAME_HEIGHT / 4), readyTextSprite.getWidth(), readyTextSprite.getHeight(), null);
			g.drawImage(instructionSprite.getImg(), (Game.GAME_WIDTH / 2) - (instructionSprite.getWidth() / 2) + Game.scale(8),
					(Game.GAME_HEIGHT / 4) + Game.scale(49), instructionSprite.getWidth(), instructionSprite.getHeight(), null);
		}

		game.getBird().draw(g);

		if(gameOver && transitionWaitTime < 0) {
			g.drawImage(overTextSprite.getImg(), (Game.GAME_WIDTH / 2) - (overTextSprite.getWidth() / 2), overTransition.getY(),
					overTextSprite.getWidth(), overTextSprite.getHeight(), null);
			if(overTransition.isComplete()) g.drawImage(resultSprite.getImg(), (Game.GAME_WIDTH / 2) - (resultSprite.getWidth() / 2), resultTransition.getY(),
					resultSprite.getWidth(), resultSprite.getHeight(), null);
		}
	}

	@Override
	public void lostFocus() {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() != KeyEvent.VK_SPACE || gameOver || paused)return;
		if(!ready) {
			ready = true;
			pipeTimer.start();
			return;
		}
		game.getBird().jump();
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
