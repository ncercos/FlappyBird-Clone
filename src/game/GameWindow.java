package game;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class GameWindow {

	private final JFrame frame;

	public GameWindow(GamePanel panel) {
		frame = new JFrame();
		frame.setTitle("Flappy Bird");
		//frame.setIconImage(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// TODO: Pause game on focus loss
		frame.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {

			}

			@Override
			public void windowLostFocus(WindowEvent e) {

			}
		});
	}

	public JFrame getFrame() {
		return frame;
	}
}
