package game;

import game.states.MenuState;
import game.states.State;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class Game extends JPanel implements ActionListener {

	// Utils
	public final static float GAME_SCALE = 1f;
	public final static int GAME_WIDTH = (int) (360 * GAME_SCALE);
	public final static int GAME_HEIGHT = (int) (640 * GAME_SCALE);
	public static final String RESOURCE_URL = "./res/";

	// States
	private final Timer timer;
	private final MenuState menuState;

	// Screen
	private Image scene;
	private Graphics pen;

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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(RESOURCE_URL + "bird.png"));
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
		menuState = new MenuState(this);
		timer = new Timer(1000/60, this);
		requestFocus();
		init();
	}

	/**
	 * Initializes the game loop.
	 */
	private void init() {
		timer.start();
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
	public void draw(Graphics g) {
		getCurrentState().draw(g);
	}

	/**
	 * Action performed when game window is not in focus.
	 */
	public void lostFocus() {
		getCurrentState().lostFocus();
	}

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
	public void actionPerformed(ActionEvent e) {
		update();
		repaint();
	}

	public State getCurrentState() {
		switch (GameState.current) {
			case MENU -> { return menuState; }
		}
		return null;
	}
}
