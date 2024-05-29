package gui;

import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import gui.pages.*;
import hardware.serial.ArduinoSerial;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class GUI {
    private static JFrame frame;
    protected static ImageIcon bankImg;
    private static final HashMap<String, BasePage> pages = new HashMap<>();
    protected static int width;
    protected static int height;
    private static Timer timeOut = new Timer(60000, e -> timeoutAction());
    private static String currentPage;

    public static void makeGUI() {

        Toolkit tk = Toolkit.getDefaultToolkit();
        width = tk.getScreenSize().width;
        height = tk.getScreenSize().height;

        try {
            bankImg = new ImageIcon("resources/textures/DABANKCOLLOR.png");
        }catch (Exception e) {
            System.out.println("Image not found");
        }

        pages.put(HomePage.KEY,new HomePage());
        pages.put(ChoicePage.KEY,new ChoicePage());
        pages.put(BalancePage.KEY,new BalancePage());
        pages.put(FastWithdrawPage.KEY,new FastWithdrawPage());
        pages.put(CustomWithdrawPage.KEY,new CustomWithdrawPage());
        pages.put(WithdrawPage.KEY,new WithdrawPage());
        pages.put(LangPage.KEY, new LangPage());
        pages.put(ReceiptPage.KEY, ReceiptPage.RECEIPT_PAGE);
        pages.put(EndPage.KEY, new EndPage());

        BufferedImage cursorImg = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
        Cursor bankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg,new Point(0,0),"blank cursor");

        frame = new JFrame("DaBank");
        frame.setIconImage(bankImg.getImage());
        for (BasePage page : pages.values()) {
            frame.add(page.getPage());
            page.setVisible(false);
            page.langUpdate();
        }
        pages.get(HomePage.KEY).setVisible(true);
        currentPage = HomePage.KEY;
        frame.getContentPane().setBackground(new Color(205,14,14));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setCursor(bankCursor); //TODO
        frame.setVisible(true);

        timeOut.stop();
        timeOut.setRepeats(false);
    }
    private static void timeoutAction() {
        gotoPage(HomePage.KEY);
    }
    public static void timerPing() {
        timeOut.restart();
    }
    public static void gotoPage(String key) {
        if (key.equals(HomePage.KEY)) {
            timeOut.stop();
        }
        else {
            timerPing();
        }
        pages.get(currentPage).setVisible(false);
        currentPage = key;
        pages.get(key).setVisible(true);
    }
    public static void updateLanguage() {
        for (BasePage page : pages.values()) {
            page.langUpdate();
        }
    }
}
