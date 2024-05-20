package gui.buttons;

import gui.BaseButton;

public abstract class PageBaseButton extends BaseButton {
	public PageBaseButton() {
		super();
		langUpdate();
	}
	public abstract void langUpdate();
}
