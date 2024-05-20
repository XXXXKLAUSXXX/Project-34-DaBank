package gui.pages;

import gui.BasePage;
import gui.buttons.PageBaseButton;
import gui.buttons.langbuttons.DutchButton;
import gui.buttons.langbuttons.USEngButton;
import gui.buttons.pagebuttons.BackButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.language.Languages;

public class LangPage extends BasePage {
	public static final String KEY = "LANGPAGE";
    private final PageBaseButton[] buttons = {
            new StopTransactionButton(),
            new BackButton(ChoicePage.KEY),
    };
    public LangPage() {
        super();

        page.add(title);
        for (PageBaseButton button : buttons) {
            page.add(button.getButton());
        }
        page.add(new DutchButton().getButton());
        page.add(new USEngButton().getButton());
    }

    @Override
    public void langUpdate() {
        title.setText(Languages.getLang().getChoice_name());
        for (PageBaseButton button : buttons) {
            button.langUpdate();
        }
    }
}
