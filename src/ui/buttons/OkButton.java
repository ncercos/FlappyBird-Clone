package ui.buttons;

import audio.Audio;
import game.GameState;
import game.states.PlayingState;
import ui.Button;

/*
 * Written by Nicholas Cercos
 * Created on Oct 14 2024
 */
public class OkButton extends Button {

	private final PlayingState state;

	public OkButton(PlayingState state, double x, double y) {
		super(state, "ok", x, y, 40, 14);
		this.state = state;
	}

	@Override
	protected void onPress() {}

	@Override
	protected void onRelease() {
		state.getGame().reset();
		state.getGame().getAudioManager().playSound(Audio.SWOOSH);
		state.setState(GameState.MENU);
	}
}
