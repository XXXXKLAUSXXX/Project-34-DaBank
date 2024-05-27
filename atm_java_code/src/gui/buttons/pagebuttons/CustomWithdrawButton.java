package gui.buttons.pagebuttons;

import gui.GUI;
import gui.buttons.PageBaseButton;
import gui.language.Languages;
import gui.pages.CustomWithdrawPage;

public class CustomWithdrawButton extends PageBaseButton {
    public CustomWithdrawButton() {
        super();

        button.setLocation(RIGHT,Y_POS(2));
        button.addActionListener(e -> GUI.gotoPage(CustomWithdrawPage.KEY));
    }

    @Override
    public void langUpdate() {
        button.setText(Languages.getLang().getCustom_withdraw_button());
    }
}
