package gui.buttons.pagebuttons;

import gui.BaseButton;
import gui.GUI;
import gui.buttons.PageBaseButton;
import gui.language.Languages;
import gui.pages.BalancePage;

public class BalanceButton extends PageBaseButton {
    public BalanceButton() {
        super();

        button.setLocation(BaseButton.RIGHT,BaseButton.Y_POS(2));
        button.addActionListener(e -> GUI.gotoPage(BalancePage.KEY));
    }

    @Override
    public void langUpdate() {
        button.setText(Languages.getLang().getBalance_button());
    }
}
