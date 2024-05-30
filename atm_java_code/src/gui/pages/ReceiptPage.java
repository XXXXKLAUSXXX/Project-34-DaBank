package gui.pages;

import gui.BaseButton;
import gui.BasePage;
import gui.GUI;
import gui.dialogs.ReceiptDialog;
import gui.language.Languages;
import hardware.serial.ArduinoSerial;

import java.time.ZonedDateTime;

public class ReceiptPage extends BasePage {
	public static final String KEY = "RECEIPTPAGE";
	public static final ReceiptPage RECEIPT_PAGE = new ReceiptPage();
	private final Y_N_Button yes = new Y_N_Button(BaseButton.RIGHT,BaseButton.Y_POS(2));
	private final Y_N_Button no = new Y_N_Button(BaseButton.LEFT,BaseButton.Y_POS(2));
	private final ReceiptDialog dialog = new ReceiptDialog();
	private String receiptInfo;
	public ReceiptPage() {
		super();

		page.add(title);
		page.add(yes.getButton());
		page.add(no.getButton());
		page.add(dialog.getDisplayText());

		yes.getButton().addActionListener(e -> printReceipt());
		no.getButton().addActionListener(e -> GUI.gotoPage(EndPage.KEY));
	}

	public static void setReceiptInfo(int amount, String iban) {
		String idIban = iban.substring(0,8);
		String uNumLast3 = iban.substring(15);
		iban = idIban + "#######" + uNumLast3;
		RECEIPT_PAGE.receiptInfo = "P" + Languages.getLang().getLang() + "|" + amount + "|" + iban + "|" + formatDateTime(ZonedDateTime.now());
	}

	private static String formatDateTime(ZonedDateTime now) {
		String nowSting = now.toString();
		String[] dateParts = nowSting.substring(0, 10).split("-");
		StringBuilder date = new StringBuilder();
		for (int i = dateParts.length; i > 0; i--) {
			date.append(dateParts[i-1]);
			if (i-1 != 0) date.append("-");
		}

		String time = nowSting.substring(11,20);
		return date + "|" + time;
	}

	private void printReceipt() {
		byte[] filler = {'P',1,1,1,1,127};
		ArduinoSerial.sendSerial(filler);
		ArduinoSerial.sendSerial(receiptInfo.getBytes());
		GUI.gotoPage(EndPage.KEY);
	}

	@Override
	public void langUpdate() {
		title.setText(Languages.getLang().getReceipt_name());
		yes.langUpdate(Languages.getLang().getReceipt_aye());
		no.langUpdate(Languages.getLang().getReceipt_nay());
		dialog.langUpdate();
	}

	private static final class Y_N_Button extends BaseButton {
		private Y_N_Button(int x, int y) {
			super();

			button.setLocation(x, y);
		}

		private void langUpdate(String text) {
			button.setText(text);
		}
	}
}
