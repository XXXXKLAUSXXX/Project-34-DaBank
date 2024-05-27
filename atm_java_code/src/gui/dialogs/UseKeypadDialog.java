package gui.dialogs;

import gui.BaseDialog;
import gui.language.Languages;

public class UseKeypadDialog extends BaseDialog {
    public UseKeypadDialog() {
        super(GUI_WIDTH/2-250, GUI_HEIGHT/4, 500, 100);
    }

    public void langUpdate() {
        getDisplayText().setText("<html>" + Languages.getLang().getKeypad_please() + "</html>");
    }
}
