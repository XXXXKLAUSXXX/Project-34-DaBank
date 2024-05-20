package gui.pages;

import gui.buttons.pagebuttons.BackButton;
import gui.buttons.pagebuttons.MainPageButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.dialogs.BalanceDialog;
import gui.dialogs.ReceiptDialog;
import gui.language.Languages;

public class BalancePage extends ServerCommPage {
    public static final String KEY = "BALANCEPAGE";
    private final ReceiptDialog receiptDialog;
    public BalancePage() {
        super();
        receiptDialog = new ReceiptDialog(getPage());

        serverCommDialog = new BalanceDialog(receiptDialog);

        page.add(title);
        page.add(new StopTransactionButton().getButton());
        page.add(new BackButton(ChoicePage.KEY).getButton());
        page.add(new MainPageButton().getButton());
        page.add(serverCommDialog.getDisplayText());
    }
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        receiptDialog.setVisible(false);
    }

    @Override
    public void langUpdate() {
        title.setText(Languages.getLang().getBalance_name());
    }
}
