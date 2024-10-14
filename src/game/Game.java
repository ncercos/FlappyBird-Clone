package game;

import audio.AudioManager;
import game.states.MenuState;
import game.states.PlayingState;
import game.states.State;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import ui.Score;
import utils.Sprite;
import world.Bird;
import world.WorldManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class Game extends JPanel implements Runnable {

	// Utils
	public final static float GAME_SCALE = 4f;
	public final static int GAME_WIDTH = scale(144);
	public final static int GAME_HEIGHT = scale(256);
	public static final String RESOURCE_URL = "./res/";

	// Screen
	private Image scene;
	private Graphics pen;

	// States
	private final MenuState menuState;
	private PlayingState playingState;

	// World
	private final Bird bird;
	private final Score score;
	private final WorldManager worldManager;
	private final AudioManager audioManager;

	// Sprites
	private Sprite readyTextSprite, overTextSprite, instructionSprite, resultSprite;

	public Game() {
		// Create game panel
		MouseInputs mouseInputs = new MouseInputs(this);
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
		setFocusable(true);

		// Create game window
		JFrame frame = new JFrame();
		frame.setTitle("Flappy Bird");
		frame.setIconImage(loadSprite("ui/icon.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {}

			@Override
			public void windowLostFocus(WindowEvent e) {
				if(getCurrentState() == null)return;
				getCurrentState().lostFocus();
			}
		});

		// Initialize game
		worldManager = new WorldManager(this);
		audioManager = new AudioManager();
		bird = new Bird(this);
		score = new Score(this);
		menuState = new MenuState(this);
		playingState = new PlayingState(this);
		loadUserInterfaceSprites();
		requestFocus();
		init();
	}

	/**
	 * Initializes the game loop.
	 */
	private void init() {
		new Thread(this).start();
	}

	/**
	 * Loads all UI menu-related sprites.
	 */
	private void loadUserInterfaceSprites() {
		readyTextSprite = new Sprite("ui/text/get_ready.png");
		overTextSprite = new Sprite("ui/text/game_over.png");
		instructionSprite = new Sprite("ui/instruction.png");
		resultSprite = new Sprite("ui/results.png");
	}

	/**
	 * Resets the game for a new round.
	 */
	public void reset() {
		score.reset();
		bird.reset();
		worldManager.reset();
		playingState = new PlayingState(this);
	}

	/**
	 * Everything that will be modified per tick.
	 */
	private void update() {
		State state = getCurrentState();
		if(state == null) {
			System.exit(0);
			return;
		}
		state.update();
	}

	/**
	 * Paints the scene.
	 *
	 * @param g The graphics context.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if(scene == null) {
			scene = createImage(GAME_WIDTH, GAME_HEIGHT);
			pen = scene.getGraphics();
		}
		pen.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		if(getCurrentState() != null) getCurrentState().draw(pen);
		g.drawImage(scene, 0, 0, this);
	}

	/**
	 * The game loop.
	 */
	@Override
	public void run() {
		while(true) {

			update();
			repaint();

			try {
				Thread.sleep(15);
			} catch (InterruptedException ignored) {}
		}
	}

	/**
	 * @return The game's current state.
	 */
	public State getCurrentState() {
		switch (GameState.current) {
			case MENU -> { return menuState; }
			case PLAYING -> { return playingState; }
		}
		return null;
	}

	/**
	 * Loads any sprite within the game's resource directory.
	 *
	 * @param path The path starting from the res folder.
	 * @return An image, if it exists, otherwise null.
	 */
	public static BufferedImage loadSprite(String path) {
		try {
			return ImageIO.read(new File(Game.RESOURCE_URL + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Scales any value to the proper size based on GAME_SCALE.
	 *
	 * @param value The value to be scaled.
	 * @return An integer increased to the game scale.
	 */
	public static int scale(double value) {
		return (int) (value * Game.GAME_SCALE);
	}

	public PlayingState getPlayingState() {
		return playingState;
	}

	public Bird getBird() {
		return bird;
	}

	public Score getScore() {
		return score;
	}

	public WorldManager getWorldManager() {
		return worldManager;
	}

	public AudioManager getAudioManager() {
		return audioManager;
	}

	public Sprite getReadyTextSprite() {
		return readyTextSprite;
	}

	public Sprite getOverTextSprite() {
		return overTextSprite;
	}

	public Sprite getInstructionSprite() {
		return instructionSprite;
	}

	public Sprite getResultSprite() {
		return resultSprite;
	}
}
