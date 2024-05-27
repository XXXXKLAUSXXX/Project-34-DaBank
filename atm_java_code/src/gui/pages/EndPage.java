package gui.pages;

import gui.BaseDialog;
import gui.BasePage;
import gui.GUI;
import gui.language.Languages;

import javax.swing.*;
import java.awt.*;

public class EndPage extends BasePage {
    public static final String KEY = "ENDPAGE";
    private final ThanksDialog dialog;
    private final Timer goBack = new Timer(5000, e -> GUI.gotoPage(HomePage.KEY));

    public EndPage() {
        super();

        dialog = new ThanksDialog(GUI_WIDTH/2-400,40,800,200);

        page.add(dialog.getDisplayText());

        goBack.stop();
        goBack.setRepeats(false);
    }

    @Override
    public void langUpdate() {
        dialog.langUpdate();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            goBack.restart();
        }
    }

    private static class ThanksDialog extends BaseDialog {
        public ThanksDialog(int x, int y, int width, int height) {
            super(x, y, width, height);

            getDisplayText().setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            getDisplayText().setForeground(Color.BLACK);
            getDisplayText().setOpaque(true);
            getDisplayText().setBackground(Color.YELLOW);
        }
        public void langUpdate() {
            getDisplayText().setText("<html>" + Languages.getLang().getThank_message() + "</html>");
        }
    }
}