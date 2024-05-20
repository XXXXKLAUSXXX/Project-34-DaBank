package gui.dialogs;

import com.google.gson.Gson;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.KeyCardProcessor;
import server.BankingData;
import server.GetInfo;

import java.io.IOException;

public class CheckBalanceDialog extends ServerCommDialog{
    private static final String API_ENDPOINT = "accountinfo";
    private final ReceiptDialog receiptDialog;
    public CheckBalanceDialog(ReceiptDialog receiptDialog) {
        super();
        this.receiptDialog = receiptDialog;
    }

    protected void comm(String keyCard, String code) {
        String db = "";
        KeyCard card = new KeyCard(keyCard);
        try {
            db = GetInfo.post(BANK_IP + API_ENDPOINT,
                    "{\"target\": \"" + card.getIban() + "\",\"pincode\": " + code + ",\"uid\": \"" + card.getUid() + "\"}");
        } catch (IOException e) {
            System.out.println("Balance check went wrong");
        }
        if (GetInfo.getStatus() == 200) {
            getDisplayText().setText(toString(db));
            receiptDialog.setVisible(true);
        }
        else handleServerResponseNotOK(db);

    }
    private String toString(String json) {
        Gson gson = new Gson();
        BankingData a = gson.fromJson(json, BankingData.class);
        return "<html>Naam: " + a.getFirstname() + ' ' + a.getLastname()
                + "<br>Saldo: " + a.getBalance() + "</html>";
    }
    @Override
    protected void startUp() {

        // keycard reader
        String keyCard;
        KeyCardProcessor keyCardProcessor = new KeyCardProcessor(getDisplayText());
        keyCard = keyCardProcessor.getRfid();
        if (keyCard != null) System.out.println(keyCard);
        else {
            System.out.println("Could not get rfid.");
            return;
        }

        // pincode
        String pin;
        PinProcessor pinProcessor = new PinProcessor(getDisplayText());
        pin = pinProcessor.getPinCode();
        if (pin != null) System.out.println(pin);
        else {
            System.out.println("Could not get pin.");
            return;
        }
        comm(keyCard, pin);
    }
}
