package ui.buttons;

import audio.Audio;
import game.GameState;
import game.states.State;
import ui.Button;

/*
 * Written by Nicholas Cercos
 * Created on Oct 10 2024
 */
public class StartButton extends Button {

	public StartButton(State state, double x, double y) {
		super(state, "start", x, y, 40, 14);
	}

	@Override
	public void onPress() {}

	@Override
	public void onRelease() {
		state.setState(GameState.PLAYING);
		state.getGame().getAudioManager().playSound(Audio.SWOOSH);
	}
}
