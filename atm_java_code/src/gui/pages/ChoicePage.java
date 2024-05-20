package gui.pages;

import gui.*;
import gui.buttons.PageBaseButton;
import gui.buttons.pagebuttons.BalanceButton;
import gui.buttons.pagebuttons.LangButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.buttons.pagebuttons.WithdrawButton;
import gui.language.Languages;

public class ChoicePage extends BasePage{
    public static final String KEY = "CHOICEPAGE";
    private final PageBaseButton[] buttons = {
            new StopTransactionButton(),
            new LangButton(),
            new WithdrawButton(),
            new BalanceButton()
    };
    public ChoicePage() {
        super();

        page.add(title);
        for (PageBaseButton button : buttons) {
            page.add(button.getButton());
        }
    }

    @Override
    public void langUpdate() {
        title.setText(Languages.getLang().getChoice_name());
        for (PageBaseButton button : buttons) {
            button.langUpdate();
        }
    }
}
