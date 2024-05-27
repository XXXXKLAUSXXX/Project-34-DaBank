package gui.buttons.pagebuttons;

import gui.GUI;
import gui.buttons.PageBaseButton;
import gui.language.Languages;
import gui.pages.HomePage;

import javax.swing.*;

public class StopTransactionButton extends PageBaseButton {
    public StopTransactionButton() {
        super();

        button.setLocation(LEFT,Y_POS(1));
        button.setIcon(new ImageIcon("resources/textures/escape.png"));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(20);
        button.addActionListener(e -> GUI.gotoPage(HomePage.KEY));
    }

    @Override
    public void langUpdate() {
        button.setText(Languages.getLang().getHome_button());
    }
}
