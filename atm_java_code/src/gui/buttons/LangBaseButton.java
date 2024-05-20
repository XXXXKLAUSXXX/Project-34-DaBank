package gui.buttons;

import gui.BaseButton;
import gui.GUI;
import gui.pages.ChoicePage;

public abstract class LangBaseButton extends BaseButton {
	protected void buttonAction() {
		GUI.gotoPage(ChoicePage.KEY);
	}
}
