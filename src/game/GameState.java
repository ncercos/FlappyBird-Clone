package game;

/*
 * Written by Nicholas Cercos
 * Created on Sep 30 2024
 */
public enum GameState {

	MENU,
	PLAYING,
	QUIT;

	public static GameState current = MENU;
}
