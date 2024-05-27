package gui.buttons.langbuttons;

import gui.GUI;
import gui.buttons.LangBaseButton;
import gui.language.Languages;

public class DutchButton extends LangBaseButton {
	private static final String ID = "nl_NL";
	public DutchButton() {
		super();

		button.setLocation(LEFT,Y_POS(2));
		button.addActionListener(e -> buttonAction());
		button.setText("Nederlands");
	}

	@Override
	protected void buttonAction() {
		Languages.setLang("nl_NL");
		GUI.updateLanguage();
		super.buttonAction();
	}
}
