package gui.pages;

import gui.buttons.pagebuttons.BackButton;
import gui.buttons.pagebuttons.MainPageButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.dialogs.CustomWithdrawDialog;
import gui.language.Languages;

public class CustomWithdrawPage extends ServerCommPage {
    public static final String KEY = "CUSTOMWITHDRAWPAGE"; // Correct key for WithdrawPage
    public CustomWithdrawPage() {
        super();

        serverCommDialog = new CustomWithdrawDialog();

        page.add(title);
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(WithdrawPage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(serverCommDialog.getDisplayText());
    }

    @Override
    public void langUpdate() {
        title.setText(Languages.getLang().getCustom_withdraw_name());
    }
}