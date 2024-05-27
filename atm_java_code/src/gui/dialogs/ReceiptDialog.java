package gui.dialogs;

import gui.BaseButton;
import gui.BaseDialog;
import gui.GUI;
import gui.language.Languages;
import gui.pages.HomePage;

import javax.swing.*;
import java.awt.*;
import java.time.ZonedDateTime;

public class ReceiptDialog extends BaseDialog {
    public ReceiptDialog() {
        super((GUI_WIDTH/2-250), GUI_HEIGHT/2-100, 500, 200);

        langUpdate();
    }
    public void setVisible(boolean visible) {
        getDisplayText().setVisible(visible);
    }

    public void langUpdate() {
        getDisplayText().setText(Languages.getLang().getReceipt_query());
    }
}
