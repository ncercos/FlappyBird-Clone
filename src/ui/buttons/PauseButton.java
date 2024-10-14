package ui.buttons;

import audio.Audio;
import game.states.PlayingState;
import ui.Button;

/*
 * Written by Nicholas Cercos
 * Created on Oct 10 2024
 */
public class PauseButton extends Button {

	private final PlayingState state;

	public PauseButton(PlayingState state, double x, double y, boolean pauses) {
		super(state, pauses ? "pause" : "unpause", x, y, 13, 14);
		this.state = state;
	}

	@Override
	protected void onPress() {}

	@Override
	protected void onRelease() {
		state.setPaused(!state.isPaused());
		state.getGame().getAudioManager().playSound(Audio.SWOOSH);
	}
}
