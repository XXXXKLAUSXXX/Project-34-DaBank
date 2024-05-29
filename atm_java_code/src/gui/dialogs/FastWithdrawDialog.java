package gui.dialogs;

import gui.dialogs.prosessors.AmountProcessor;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.KeyCardProcessor;
import gui.language.Languages;
import gui.pages.ReceiptPage;
import hardware.Bills;
import server.GetInfo;

import java.io.IOException;

public class FastWithdrawDialog extends WithdrawDialog {
    @Override
    protected void startUp() {

        getUseKeypad().getDisplayText().setVisible(true);
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
        sleep();

        getUseKeypad().getDisplayText().setVisible(false);
        // keycard reader
        String keyCard;
        KeyCardProcessor keyCardProcessor = new KeyCardProcessor(getDisplayText());
        keyCard = keyCardProcessor.getRfid();
        if (keyCard == null) {
            System.out.println("Could not get keycard.");
            return;
        }

        getUseKeypad().getDisplayText().setVisible(true);
        // pincode
        String pin;
        PinProcessor pinProcessor = new PinProcessor(getDisplayText());
        pin = pinProcessor.getPinCode();
        if (pin == null) {
            System.out.println("Could not get pin.");
            return;
        }
        comm(keyCard, pin, amount);
        spitMoney(toBills(amount));
    }
    private static int round(int amount) {
        if (amount % 5 < 3) amount -= (amount % 5);
        else amount += 5 - (amount % 5);
        return amount;
    }
    private static int[] toBills(int amount) {
        if (amount < 0) return new int[]{0,0,0,0}; // niet minder dan 0

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
