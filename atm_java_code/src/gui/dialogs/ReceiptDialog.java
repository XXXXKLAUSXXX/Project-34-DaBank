package gui.dialogs;

import gui.BaseButton;
import gui.BaseDialog;
import gui.GUI;
import gui.language.Languages;
import gui.pages.HomePage;

import javax.swing.*;
import java.awt.*;

public class ReceiptDialog extends BaseDialog {
    private final YNButtons buttons;
    public ReceiptDialog(JLabel page) {
        super((GUI_WIDTH/2-250), GUI_HEIGHT/2+300, 500, 100);
        this.buttons = new YNButtons();

        langUpdate();
        setVisible(false);

        page.add(getDisplayText());
        page.add(buttons.yesButton);
        page.add(buttons.noButton);
    }
    public void setVisible(boolean visible) {
        getDisplayText().setVisible(visible);
        buttons.yesButton.setVisible(visible);
        buttons.noButton.setVisible(visible);
    }

    public void langUpdate() {
        getDisplayText().setText(Languages.getLang().getReceipt_query());
        buttons.yesButton.setText(Languages.getLang().getReceipt_aye());
        buttons.noButton.setText(Languages.getLang().getReceipt_nay());
    }

    private static class YNButtons {
        protected final JButton yesButton;
        protected final JButton noButton;
        protected YNButtons() {
            yesButton = new JButton();
            yesButton.setBounds(BaseButton.RIGHT,BaseButton.Y_POS(2),500,200);
            yesButton.setFocusable(false);
            yesButton.setFont(STD_FONT);
            yesButton.setForeground(Color.BLACK);
            yesButton.setBackground(Color.LIGHT_GRAY);
            yesButton.setBorderPainted(false);
            yesButton.addActionListener(e -> GUI.gotoPage(HomePage.KEY)); // TODO

            noButton = new JButton();
            noButton.setBounds(BaseButton.LEFT,BaseButton.Y_POS(2),500,200);
            noButton.setFocusable(false);
            noButton.setFont(STD_FONT);
            noButton.setForeground(Color.BLACK);
            noButton.setBackground(Color.LIGHT_GRAY);
            noButton.setBorderPainted(false);
            noButton.addActionListener(e -> GUI.gotoPage(HomePage.KEY)); // TODO
        }
    }

}
