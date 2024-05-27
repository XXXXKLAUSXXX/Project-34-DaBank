package gui.pages;

import gui.buttons.pagebuttons.BackButton;
import gui.buttons.pagebuttons.MainPageButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.dialogs.FastWithdrawDialog;
import gui.dialogs.ReceiptDialog;
import gui.language.Languages;

public class FastWithdrawPage extends ServerCommPage {
    public static final String KEY = "FASTWITHDRAWPAGE";
    private final BackButton backButton = new BackButton(WithdrawPage.KEY);
    public FastWithdrawPage() {
        super();

        serverCommDialog = new FastWithdrawDialog();

        page.add(title);
        page.add(backButton.getButton());
        page.add(serverCommDialog.getDisplayText());
    }

    @Override
    public void langUpdate() {
        super.langUpdate();
        title.setText(Languages.getLang().getFast_withdraw_name());
        backButton.langUpdate();
    }
}
