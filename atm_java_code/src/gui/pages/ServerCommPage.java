package gui.pages;

import gui.BasePage;
import gui.buttons.pagebuttons.MainPageButton;
import gui.buttons.pagebuttons.StopTransactionButton;
import gui.dialogs.ReceiptDialog;
import gui.dialogs.ServerCommDialog;

public abstract class ServerCommPage extends BasePage {
    protected ServerCommDialog serverCommDialog; // must be initialised in subclasses!
    protected final ReceiptDialog receiptDialog = new ReceiptDialog(getPage());
    private final StopTransactionButton stopTransactionButton = new StopTransactionButton();
    private final MainPageButton mainPageButton = new MainPageButton();
    public ServerCommPage() {
        super();

        page.add(stopTransactionButton.getButton());
        page.add(mainPageButton.getButton());
    }
    @Override
    public void setVisible(boolean visible) {
        page.setVisible(visible);
        if (serverCommDialog != null) {
            if (visible) {
                serverCommDialog.startTransaction();
            } else {
                serverCommDialog.stopTransaction();
            }
        }
    }
    @Override
    public void langUpdate() {
        receiptDialog.langUpdate();
        stopTransactionButton.langUpdate();
        mainPageButton.langUpdate();
    }
}
