package gui.pages;

import gui.*;
import gui.language.Languages;

import javax.swing.*;
import java.awt.*;

public class HomePage extends BasePage{
    public static final String KEY = "HOMEPAGE";
    private final JLabel startText = new JLabel();
    public HomePage() {
        super();

        startText.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        startText.setForeground(Color.BLACK);

        JPanel display = new JPanel();
        display.setBounds(0,(GUI_HEIGHT-100), GUI_WIDTH,50);
        display.setOpaque(false);
        display.add(startText);
        page.add(display);

        JButton start = new JButton();
        start.setSize(GUI_WIDTH, GUI_HEIGHT);
        start.setOpaque(false);
        start.setFocusable(false);
        start.setContentAreaFilled(false);
        start.setBorderPainted(false);
        start.addActionListener(e -> GUI.gotoPage(ChoicePage.KEY));
        page.add(start);
    }

    @Override
    public void langUpdate() {
        startText.setText(Languages.getLang().getHome_start());
    }
}
