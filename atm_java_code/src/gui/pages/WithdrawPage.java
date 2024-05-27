package gui.pages;

import gui.BaseButton;
import gui.BasePage;
import gui.buttons.PageBaseButton;
import gui.buttons.pagebuttons.*;
import gui.language.Languages;

public class WithdrawPage extends BasePage {
    public static final String KEY = "WITHDRAWPAGE"; // Correct key for WithdrawPage
    private final PageBaseButton[] buttons = {
            new StopTransactionButton(),
            new BackButton(ChoicePage.KEY),
            new MainPageButton(),
            new FastWithdrawButton(),
            new CustomWithdrawButton()
    };
    public WithdrawPage() {
        super();

        page.add(title);
        for (BaseButton button : buttons) {
            page.add(button.getButton());
        }


    }
    @Override
    public void langUpdate() {
        title.setText(Languages.getLang().getWithdraw_menu_name());
        for (PageBaseButton button : buttons) {
            button.langUpdate();
        }
    }
}