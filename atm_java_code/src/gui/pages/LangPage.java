package gui.pages;

import gui.BasePage;
import gui.buttons.pagebuttons.BackButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.language.Languages;

public class LangPage extends BasePage {
	public static final String KEY = "LANGPAGE";
    public LangPage() {
        super();

        page.add(title);
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
    }

    @Override
    public void langUpdate() {
        title.setText(Languages.getLang().getChoice_name());
    }
}
