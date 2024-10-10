package ui.buttons;

import game.states.PlayingState;
import ui.Button;

/*
 * Written by Nicholas Cercos
 * Created on Oct 10 2024
 */
public class UnpauseButton extends Button {

	private final PlayingState state;

	public UnpauseButton(PlayingState state, double x, double y) {
		super(state, "unpause", x, y, 13, 14);
		this.state = state;
	}

	@Override
	protected void onPress() {}

	@Override
	protected void onRelease() {
		state.setPaused(!state.isPaused());
	}
}
