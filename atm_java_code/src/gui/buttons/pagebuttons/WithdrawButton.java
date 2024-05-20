package gui.buttons.pagebuttons;

import gui.GUI;
import gui.buttons.PageBaseButton;
import gui.language.Languages;
import gui.pages.WithdrawPage;

public class WithdrawButton extends PageBaseButton {
    public WithdrawButton() {
        super();

        button.setLocation(LEFT, Y_POS(2));
        button.addActionListener(e -> GUI.gotoPage(WithdrawPage.KEY));
    }

    @Override
    public void langUpdate() {
        button.setText(Languages.getLang().getWithdraw_menu_button());
    }
}