package gui.buttons.pagebuttons;

import gui.GUI;
import gui.buttons.PageBaseButton;
import gui.language.Languages;
import gui.pages.LangPage;

import javax.swing.*;

public class LangButton extends PageBaseButton {
	public LangButton() {
		super();

		button.setLocation(RIGHT,Y_POS(1));
		button.setIcon(new ImageIcon("resources/textures/language.png"));
		button.setHorizontalTextPosition(SwingConstants.LEFT);
		button.setIconTextGap(20);
		button.addActionListener(e -> GUI.gotoPage(LangPage.KEY));
	}

	@Override
	public void langUpdate() {
		button.setText(Languages.getLang().getLang_button());
	}
}
