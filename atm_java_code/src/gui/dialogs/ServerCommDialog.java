package gui.dialogs;

import com.google.gson.Gson;
import gui.BaseDialog;
import gui.dialogs.prosessors.AmountProcessor;
import gui.dialogs.prosessors.CustomBillsProcessor;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.KeyCardProcessor;
import server.BankingData;
import server.GetInfo;

public abstract class ServerCommDialog extends BaseDialog {
    protected static final String BANK_IP = "http://145.24.223.74:8100/api/";
    public ServerCommDialog() {
        super((GUI_WIDTH/2-250),GUI_HEIGHT/2-100,500,200);
    }
    public ServerCommDialog(int height) {
        super((GUI_WIDTH/2-250),GUI_HEIGHT/2-(height/2),500,height);
    }
    public void startTransaction() {
        Thread keypad = new Thread(new CreateDialog());
        keypad.start();
	}
    public void stopTransaction() {
        KeyCardProcessor.stopRfidScanner();
        PinProcessor.stopKeypad();
        AmountProcessor.stopKeypad();
        CustomBillsProcessor.stopKeypad();
    }
    protected abstract void startUp();
    protected class CreateDialog implements Runnable {
        @Override
        public void run() {
            startUp();
        }
    }
    protected void handleServerResponseNotOK(String db) {
        int status = GetInfo.getStatus();
        switch (status) {
            case GetInfo.BAD_REQUEST:
                getDisplayText().setText("<html>Er ging iets fout.<br>Excuses voor het ongemak.</html>");
                break;
            case GetInfo.UNAUTHORISED:
                if (db.contains("noob-token")) {
                    getDisplayText().setText("<html>Er ging iets fout.<br>Excuses voor het ongemak.</html>");
                    return;
                }
                getDisplayText().setText("<html>Foute pincode<br>Pogingen resterend:"
                    + getAttempts(db) + "</html>");
                break;
            case GetInfo.FORBIDDEN:
                getDisplayText().setText("Bankaccount geblokkeerd.");
                break;
            case GetInfo.NOT_FOUND:
                getDisplayText().setText("<html>Bank of rekening<br>bestaat niet.</html>");
                break;
            case GetInfo.NO_BALLANCE:
                getDisplayText().setText("Onvoldoende saldo");
                break;
            case GetInfo.SERVER_ERROR:
                getDisplayText().setText("<html>Servers niet beschikbaar<br/>Excuses voor het ongemak</html>");
                break;
            default:
                System.out.println("A new responsecode just dropped!");
        }
    }
    protected int getAttempts(String json) {
        Gson gson = new Gson();
        BankingData a = gson.fromJson(json, BankingData.class);
        return a.getAttempts_remaining();
    }
    protected final class KeyCard {
        private final String uid;
        private final String iban;
        KeyCard(String keyCard) {
            this.uid = keyCard.substring(0,8);
            System.out.println(uid);
            this.iban = keyCard.substring(8);
            System.out.println(iban);
        }
        public String getUid() {
            return uid;
        }
        public String getIban() {
            return iban;
        }
    }
}
