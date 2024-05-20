package gui.dialogs;

import gui.dialogs.prosessors.AmountProcessor;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.KeyCardProcessor;
import server.GetInfo;

import java.io.IOException;

public class FastWithdrawDialog extends ServerCommDialog {
    private static final String API_ENDPOINT = "withdraw";
    protected void comm(String keyCard, String code, int amount) {
        if (amount > 1) {
            String db = "";
            KeyCard card = new KeyCard(keyCard);
            try {
                db = GetInfo.post(BANK_IP + API_ENDPOINT,
                        "{\"amount\": " + amount + ",\"target\": " + card.getIban() + ",\"pincode\":" + code + ",\"uid\": \"" + card.getUid() + "\"}");
            } catch (IOException e) {
                System.out.println("Pinrequest went wrong");
            }
            if (GetInfo.getStatus() == 200) {
                getDisplayText().setText("Transactie succes");
                System.out.println("OK");
            }
            else handleServerResponseNotOK(db);
        }
    }
    @Override
    protected void startUp() {

        // amount
        int amount;
        AmountProcessor amountProcessor = new AmountProcessor(getDisplayText());
        amount = amountProcessor.getAmount();
        amount = round(amount);
        if (amount < 1) {
            System.out.println("Couldn't get amount.");
            getDisplayText().setText("Bedrag is te klein");
            return;
        }
        getDisplayText().setText("Afgerond: " + amount);
        System.out.println(amount);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Couldn't buy you time");
        }

        // keycard reader
        String keyCard;
        KeyCardProcessor keyCardProcessor = new KeyCardProcessor(getDisplayText());
        keyCard = keyCardProcessor.getRfid();
        if (keyCard != null) System.out.println(keyCard);
        else {
            System.out.println("Could not get keycard.");
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
        comm(keyCard, pin, amount);
    }
    private static int round(int amount) {
        if (amount % 5 < 3) amount -= (amount % 5);
        else amount += 5 - (amount % 5);
        return amount;
    }
}
