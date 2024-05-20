package gui.dialogs;


import gui.dialogs.prosessors.CustomBillsProcessor;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.KeyCardProcessor;
import server.GetInfo;

import java.io.IOException;
import java.util.Arrays;

public class CustomWithdrawDialog extends ServerCommDialog {
    private static final String API_ENDPOINT = "withdraw";
    public CustomWithdrawDialog() {
        super(300);
    }
    protected void comm(String keyCard, String code, int amount) {
        if (amount > 1) {
            String db = "";
            KeyCard card = new KeyCard(keyCard);
            try {
                db = GetInfo.post(BANK_IP + API_ENDPOINT,
                        "{\"amount\": " + amount + ",\"target\": \"" + card.getIban() + "\",\"pincode\":" + code + ",\"uid\": \"" + card.getUid() + "\"}");
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
        int[] amounts;
        CustomBillsProcessor customBillsProcessor = new CustomBillsProcessor(getDisplayText());
        amounts = customBillsProcessor.getAmounts();
        if (!Arrays.stream(amounts).allMatch(i -> i == 0)) {
			System.out.println(Arrays.toString(amounts));
		} else {
            System.out.println("Couldn't get amount.");
            return;
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
        comm(keyCard, pin, getTotal(amounts));
    }
    private int getTotal(int[] amounts) {
        int total = 0;
        for (int amount : amounts) {
            total += amount;
        }
        return total;
    }
}
