package gui.pages;

import gui.buttons.pagebuttons.BackButton;
import gui.buttons.pagebuttons.MainPageButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.dialogs.CustomWithdrawDialog;
import gui.language.Languages;

public class CustomWithdrawPage extends ServerCommPage {
    public static final String KEY = "CUSTOMWITHDRAWPAGE"; // Correct key for WithdrawPage
    private final BackButton backButton = new BackButton(WithdrawPage.KEY);
    public CustomWithdrawPage() {
        super();

        serverCommDialog = new CustomWithdrawDialog();

        page.add(title);
        page.add(backButton.getButton());
        page.add(serverCommDialog.getDisplayText());
        page.add(serverCommDialog.getUseKeypad().getDisplayText());
    }

    @Override
    public void langUpdate() {
        super.langUpdate();
        title.setText(Languages.getLang().getCustom_withdraw_name());
        backButton.langUpdate();
        serverCommDialog.getUseKeypad().langUpdate();
    }
}