package gui.buttons.pagebuttons;

import gui.GUI;
import gui.buttons.PageBaseButton;
import gui.language.Languages;
import gui.pages.FastWithdrawPage;


public class FastWithdrawButton extends PageBaseButton {
    public FastWithdrawButton() {
        super();

        button.setLocation(LEFT,Y_POS(2));
        button.addActionListener(e -> GUI.gotoPage(FastWithdrawPage.KEY));
    }

    @Override
    public void langUpdate() {
        button.setText(Languages.getLang().getFast_withdraw_button());
    }
}
