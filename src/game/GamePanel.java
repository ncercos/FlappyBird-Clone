package game;

import javax.swing.*;
import java.awt.*;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public class GamePanel extends JPanel {

	private final Game game;
	private Image scene;
	private Graphics pen;

	public GamePanel(Game game) {
		this.game = game;
		setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		setFocusable(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if(scene == null) {
			scene = createImage(Game.WIDTH, Game.HEIGHT);
			pen = scene.getGraphics();
		}
		pen.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
		game.draw(pen);
		g.drawImage(scene, 0, 0, this);
	}

	public Game getGame() {
		return game;
	}
}
