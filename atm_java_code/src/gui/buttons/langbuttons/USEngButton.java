package gui.buttons.langbuttons;

import gui.GUI;
import gui.buttons.LangBaseButton;
import gui.language.Languages;

public class USEngButton extends LangBaseButton {
	private static final String ID = "nl_NL";
	public USEngButton() {
		super();

		button.setLocation(RIGHT,Y_POS(2));
		button.addActionListener(e -> buttonAction());
		button.setText("English");
	}

	@Override
	protected void buttonAction() {
		Languages.setLang("en_US");
		GUI.updateLanguage();
		super.buttonAction();
	}
}