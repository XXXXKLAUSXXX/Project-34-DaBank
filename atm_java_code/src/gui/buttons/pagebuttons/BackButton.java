package gui.buttons.pagebuttons;

import gui.*;
import gui.buttons.PageBaseButton;
import gui.language.Languages;

import javax.swing.*;

public class BackButton extends PageBaseButton {
    public BackButton(String keyPrevious) {
        super();

        button.setLocation(RIGHT,Y_POS(1));
        button.setIcon(new ImageIcon("resources/textures/back.png"));
        button.setHorizontalTextPosition(SwingConstants.LEFT);
        button.setIconTextGap(20);
        button.addActionListener(e -> GUI.gotoPage(keyPrevious));
    }

    @Override
    public void langUpdate() {
        button.setText(Languages.getLang().getBack_button());
    }
}
