package game.states;

import audio.Audio;
import game.Game;
import ui.Medal;
import ui.Score;
import ui.buttons.*;
import utils.*;
import world.Bird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Written by Nicholas Cercos
 * Created on Oct 10 2024
 */
public class PlayingState extends State {

	private boolean ready, paused, gameOver;
	private PauseButton pauseButton, unpauseButton;
	private final Timer pipeTimer, scoreTimer;
	private final Sprite.Bounce readyBounce;
	private final Transition overTransition, resultTransition;
	private final Delay transitionDelay, deathDelay;
	private int currentScore, currentHighScore;
	private final Score score;
	private boolean resultsComplete;
	private Medal medal;
	private final Animation sparkleAni;
	private Location sparkleLoc;

	public PlayingState(Game game) {
		super(game);
		initPauseButton();
		updateButtons();
		pipeTimer = new Timer(1350, _ -> game.getWorldManager().placePipes());
		scoreTimer = new Timer(50, _ -> updateScoreResults());
		readyBounce = new Sprite.Bounce(Game.scale(100));
		overTransition = new Transition(Game.GAME_HEIGHT / 4 - Game.scale(15), Game.GAME_HEIGHT / 4, 3, false);
		resultTransition = new Transition(Game.GAME_HEIGHT, (Game.GAME_HEIGHT / 4) + Game.scale(23), 20, true);
		transitionDelay = new Delay(13);
		deathDelay = new Delay(40);
		score = game.getScore();
		sparkleAni = new Animation("ui/sparkle", 5, 5, 4);
	}

	/**
	 * Update location of medal sparkle.
	 */
	private void updateSparkleLocation() {
		if(sparkleLoc == null || sparkleAni.isCycleComplete()) {
			sparkleAni.restartCycle();
			int x = Game.scale(ThreadLocalRandom.current().nextInt(29, 47));
			int y = Game.scale(ThreadLocalRandom.current().nextInt(108, 125));
			if (sparkleLoc == null) sparkleLoc = new Location(x, y);
			else sparkleLoc.update(x, y);
		}
	}

	/**
	 * Update score when displaying results.
	 */
	private void updateScoreResults() {
		if(resultsComplete)return;
		if(currentScore < score.getCurrent())
			currentScore++;

		if(currentHighScore < score.getAllTimeHighest())
			currentHighScore++;

		if((currentScore == score.getCurrent()) && (currentHighScore == score.getAllTimeHighest())) {
			resultsComplete = true;
			medal = Medal.getMedal(currentScore);
			updateButtons();
			scoreTimer.stop();
		}
	}

	/**
	 * The actions performed when the user interacts with game.
	 */
	private void handleUserInput() {
		if(gameOver || paused)return;
		if(!ready) {
			ready = true;
			pipeTimer.start();
			return;
		}
		game.getBird().jump();
	}

	/**
	 * Add the ok (menu) and share button to scene.
	 */
	private void displayEndButtons() {
		buttons.add(new OkButton(this, (Game.GAME_WIDTH / 4.0) - Game.scale(15), Game.scale(180)));
		buttons.add(new ShareButton(this, (Game.GAME_WIDTH / 4.0) + Game.scale(47), Game.scale(180)));
	}

	/**
	 * Register pause related buttons for this state.
	 */
	private void initPauseButton() {
		int xy = Game.scale(10);
		pauseButton = new PauseButton(this, xy, xy, true);
		unpauseButton = new PauseButton(this, xy, xy, false);
	}

	/**
	 * Updates buttons to display proper type based on game status.
	 */
	private void updateButtons() {
		if(paused) {
			buttons.remove(pauseButton);
			buttons.add(unpauseButton);
		} else if(resultsComplete)
			displayEndButtons();
		else if(gameOver)
			buttons.clear();
		else {
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
		updateButtons();

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
			deathDelay.update();
			transitionDelay.update();
			if(!transitionDelay.isComplete())return;
			overTransition.update();
			resultTransition.update();

			if(deathDelay.isComplete() && deathDelay.isActive()) {
				game.getAudioManager().playSound(Audio.DIE);
				deathDelay.deactivate();
			}

			if(resultTransition.isComplete() && !scoreTimer.isRunning())
				scoreTimer.start();
			return;
		}

		if(game.getWorldManager().movePipes() || bird.isDead()) {
			game.getAudioManager().playSound(Audio.HIT);
			gameOver = true;
			score.updateAllTimeHighest();
			updateButtons();
			pipeTimer.stop();
			if(!score.isRecordBroken()) currentHighScore = score.getAllTimeHighest();
		}
	}

	@Override
	public void onDraw(Graphics g) {
		game.getWorldManager().drawBackground(g);

		if(!gameOver) score.draw(g);

		if(!ready) {
			g.drawImage(game.getReadyTextSprite().getImg(), (Game.GAME_WIDTH / 2) - (game.getReadyTextSprite().getWidth() / 2),
					(Game.GAME_HEIGHT / 4), game.getReadyTextSprite().getWidth(), game.getReadyTextSprite().getHeight(), null);
			g.drawImage(game.getInstructionSprite().getImg(), (Game.GAME_WIDTH / 2) - (game.getInstructionSprite().getWidth() / 2) + Game.scale(8),
					(Game.GAME_HEIGHT / 4) + Game.scale(49), game.getInstructionSprite().getWidth(), game.getInstructionSprite().getHeight(), null);
		}

		game.getBird().draw(g);

		if(gameOver && transitionDelay.isComplete()) {
			g.drawImage(game.getOverTextSprite().getImg(), (Game.GAME_WIDTH / 2) - (game.getOverTextSprite().getWidth() / 2), overTransition.getY(),
					game.getOverTextSprite().getWidth(), game.getOverTextSprite().getHeight(), null);
			if(overTransition.isComplete()) {
				g.drawImage(game.getResultSprite().getImg(), (Game.GAME_WIDTH / 2) - (game.getResultSprite().getWidth() / 2), resultTransition.getY(),
						game.getResultSprite().getWidth(), game.getResultSprite().getHeight(), null);

				int scoreX = Game.scale(118);
				score.draw(g, currentScore, new Location(scoreX - score.getDrawWidth(currentScore), resultTransition.getY() + Game.scale(17)));
				score.draw(g, currentHighScore, new Location(scoreX - score.getDrawWidth(currentHighScore),
						resultTransition.getY() + Game.scale(38)));

				if(resultsComplete) {
					if (score.isRecordBroken())
						g.drawImage(score.getNewLabel().getImg(), Game.scale(83), Game.scale(116), score.getNewLabel().getWidth(), score.getNewLabel().getHeight(), null);
					if(medal != null) {
						g.drawImage(medal.getSprite().getImg(), Game.scale(28.5), Game.scale(108), medal.getSprite().getWidth(), medal.getSprite().getHeight(), null);
						updateSparkleLocation();
						Image sparkle = sparkleAni.getCurrentImage(this, true);
						g.drawImage(sparkle, (int) sparkleLoc.getX(), (int) sparkleLoc.getY(), Game.scale(sparkle.getWidth(null)), Game.scale(sparkle.getHeight(null)), null);
					}
				}
			}
		}
	}

	@Override
	public void lostFocus() {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() != KeyEvent.VK_SPACE)return;
		handleUserInput();
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	protected void onMousePress(MouseEvent e) {
		handleUserInput();
	}

	@Override
	protected void onMouseRelease(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}
}
