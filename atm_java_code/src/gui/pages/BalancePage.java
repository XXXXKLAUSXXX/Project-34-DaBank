package gui.pages;

import gui.buttons.pagebuttons.BackButton;
import gui.buttons.pagebuttons.MainPageButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.dialogs.BalanceDialog;
import gui.dialogs.ReceiptDialog;
import gui.language.Languages;

public class BalancePage extends ServerCommPage {
    public static final String KEY = "BALANCEPAGE";
    private final BackButton backButton = new BackButton(ChoicePage.KEY);
    public BalancePage() {
        super();

        serverCommDialog = new BalanceDialog();

        page.add(title);
        page.add(backButton.getButton());
        page.add(serverCommDialog.getDisplayText());
        page.add(serverCommDialog.getUseKeypad().getDisplayText());
    }
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void langUpdate() {
        super.langUpdate();
        title.setText(Languages.getLang().getBalance_name());
        backButton.langUpdate();
        serverCommDialog.getUseKeypad().langUpdate();
    }
}
