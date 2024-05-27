package gui.buttons.pagebuttons;

import gui.*;
import gui.buttons.PageBaseButton;
import gui.language.Languages;
import gui.pages.ChoicePage;

import javax.swing.*;

public class MainPageButton extends PageBaseButton {
    public MainPageButton() {
        super();

        button.setLocation(RIGHT,Y_POS(3));
        button.setIcon(new ImageIcon("resources/textures/home.png"));
        button.setHorizontalTextPosition(SwingConstants.LEFT);
        button.setIconTextGap(20);
        button.addActionListener(e -> GUI.gotoPage(ChoicePage.KEY));
    }

    @Override
    public void langUpdate() {
        button.setText(Languages.getLang().getMain_menu_button());
    }
}
