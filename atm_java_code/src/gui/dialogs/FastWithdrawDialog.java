package gui.dialogs;

import gui.dialogs.prosessors.AmountProcessor;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.KeyCardProcessor;
import gui.language.Languages;
import hardware.Bills;
import server.GetInfo;

import java.io.IOException;

public class FastWithdrawDialog extends ServerCommDialog {
    private static final String API_ENDPOINT = "withdraw";

    public FastWithdrawDialog(ReceiptDialog receiptDialog) {
        super(receiptDialog);
    }

    protected void comm(String keyCard, String code, int amount) {
        if (amount > 1) {
            String db = "";
            KeyCard card = new KeyCard(keyCard);
            try {
                db = GetInfo.post(BANK_IP + API_ENDPOINT + "?target=" + card.getIban(),
                        "{\"amount\": " + amount + ",\"pincode\":" + code + ",\"uid\": \"" + card.getUid() + "\"}");
            } catch (IOException e) {
                System.out.println("Pinrequest went wrong");
            }
            if (GetInfo.getStatus() == 200) {
                getDisplayText().setText(Languages.getLang().getOk());
                receiptDialog.setVisible(true);
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
            getDisplayText().setText(Languages.getLang().getAmount_not_valid());
            return;
        }
        getDisplayText().setText(Languages.getLang().getAmount_withdraw() + amount);
        System.out.println(amount);
        sleep();

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
    private static int[] toBills(int amount) {
        int[] bills = new int[4];
        for (int i = 3; i >= 0; i--) {
            int tmpAmount = amount / Bills.getMultiplier()[i];
            if (Bills.check(i) < tmpAmount) {
                bills[i] = Bills.check(i);
                amount -= Bills.getMultiplier()[i] * Bills.check(i);
            }
            else {
                bills[i] = tmpAmount;
                amount %= Bills.getMultiplier()[i];
            }
        }
        return bills;
    }
}
