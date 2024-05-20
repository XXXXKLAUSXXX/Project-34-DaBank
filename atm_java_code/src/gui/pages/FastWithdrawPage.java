package gui.pages;

import gui.buttons.pagebuttons.BackButton;
import gui.buttons.pagebuttons.MainPageButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.dialogs.FastWithdrawDialog;
import gui.language.Languages;

public class FastWithdrawPage extends ServerCommPage {
    public static final String KEY = "FASTWITHDRAWPAGE";
    public FastWithdrawPage() {
        super();

        serverCommDialog = new FastWithdrawDialog();

        page.add(title);
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(WithdrawPage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(serverCommDialog.getDisplayText());
    }

    @Override
    public void langUpdate() {
        title.setText(Languages.getLang().getFast_withdraw_name());
    }
}
