package gui.dialogs;

import gui.GUI;
import gui.language.Languages;
import gui.pages.ReceiptPage;
import hardware.serial.ArduinoSerial;
import server.GetInfo;

import java.io.IOException;

public abstract class WithdrawDialog extends ServerCommDialog {
	private static final String API_ENDPOINT = "withdraw";

	public WithdrawDialog() {
		super();
	}

	public WithdrawDialog(int height) {
		super(height);
	}

	protected void comm(String keyCard, String code, int amount) {
		if (amount > 1) {
			String db = "";
			KeyCard card = new KeyCard(keyCard);
			try {
				db = GetInfo.post(BANK_IP + API_ENDPOINT + "?target=" + card.getIban(),
						"{\"amount\": " + poundToNoob(amount) + ",\"pincode\": \"" + code + "\",\"uid\": \"" + card.getUid() + "\"}");
			} catch (IOException e) {
				System.out.println("Pinrequest went wrong");
			}
			if (GetInfo.getStatus() == 200) {
				getDisplayText().setText(Languages.getLang().getOk());
				ReceiptPage.setReceiptInfo(amount,card.getIban());
				GUI.gotoPage(ReceiptPage.KEY);
			}
			else handleServerResponseNotOK(db);
		}
	}
	private int poundToNoob(int amount) {
        return (int) ((double) amount / 0.0085);
	}

	protected void spitMoney(int[] amounts) {
		byte[] toSend = {'B',0,0,0,0};
		for (int i = 0; i < 4; i++) {
			toSend[i+1] = (byte) amounts[i];
		}
		ArduinoSerial.sendSerial(toSend);
	}
}
