package ui.buttons;

import game.states.State;
import ui.Button;

/*
 * Written by Nicholas Cercos
 * Created on Oct 10 2024
 */
public class ShareButton extends Button {

	public ShareButton(State state, double x, double y) {
		super(state, "share", x, y, 40, 14);
	}

	@Override
	public void onPress() {}

	@Override
	public void onRelease() {}
}
