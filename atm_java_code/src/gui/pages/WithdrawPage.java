package gui.pages;

import gui.BasePage;
import gui.buttons.pagebuttons.*;
import gui.language.Languages;

public class WithdrawPage extends BasePage {
    public static final String KEY = "WITHDRAWPAGE"; // Correct key for WithdrawPage
    public WithdrawPage() {
        super();

        page.add(title);
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(new FastWithdrawButton().getButton());
        page.add(new CustomWithdrawButton().getButton());

    }
    @Override
    public void langUpdate() {
        title.setText(Languages.getLang().getWithdraw_menu_name());
    }
}