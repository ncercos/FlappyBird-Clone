package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class Game implements ActionListener {

	// Dimensions
	public final static float SCALE = 1f;
	public final static int WIDTH = (int) (360 * SCALE);
	public final static int HEIGHT = (int) (640 * SCALE);

	// Utils
	public static final String RESOURCE_URL = "./res/";

	// Screen
	private final GameWindow window;
	private final GamePanel panel;
	private final Timer timer;

	public Game() {
		this.panel = new GamePanel(this);
		this.window = new GameWindow(panel);
		timer = new Timer(1000/60, this);
		panel.requestFocus();
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

	}

	/**
	 * Paints the scene.
	 *
	 * @param g The graphics context.
	 */
	public void draw(Graphics g) {

	}

	/**
	 * The game loop.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		panel.repaint();
	}

	public GameWindow getWindow() {
		return window;
	}

	public GamePanel getPanel() {
		return panel;
	}
}
