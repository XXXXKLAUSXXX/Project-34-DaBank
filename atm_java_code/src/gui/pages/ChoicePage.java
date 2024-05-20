package gui.pages;

import gui.*;
import gui.buttons.pagebuttons.BalanceButton;
import gui.buttons.pagebuttons.LangButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.buttons.pagebuttons.WithdrawButton;
import gui.language.Languages;

public class ChoicePage extends BasePage{
    public static final String KEY = "CHOICEPAGE";
    public ChoicePage() {
        super();

        page.add(title);
        page.add(new StopTransactionButton().getButton());
        page.add(new LangButton().getButton());
        page.add(new WithdrawButton().getButton());
        page.add(new BalanceButton().getButton());
    }

    @Override
    public void langUpdate() {
        title.setText(Languages.getLang().getChoice_name());
    }
}
