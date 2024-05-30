package gui.dialogs;


import gui.dialogs.prosessors.CustomBillsProcessor;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.KeyCardProcessor;
import gui.language.Languages;
import gui.pages.ReceiptPage;
import hardware.Bills;
import hardware.serial.ArduinoSerial;
import server.GetInfo;

import java.io.IOException;
import java.util.Arrays;

public class CustomWithdrawDialog extends WithdrawDialog {
    public CustomWithdrawDialog() {
        super(300);
    }
    @Override
    protected void startUp() {

        getUseKeypad().getDisplayText().setVisible(true);
        // amount
        int[] amounts;
        CustomBillsProcessor customBillsProcessor = new CustomBillsProcessor(getDisplayText());
        amounts = customBillsProcessor.getAmounts();
        if (Arrays.stream(amounts).allMatch(i -> i == 0)) {
            System.out.println("Couldn't get amount.");
            getDisplayText().setText(Languages.getLang().getAmount_not_valid());
            return;
        }
        int amount = getTotal(amounts);
        getDisplayText().setText(Languages.getLang().getAmount_withdraw() + amount);
        System.out.println(amount);
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

        byte[] outputByte = new byte[amounts.length + 2];
        outputByte[0] = 'B';
        for (int i = 0; i < amounts.length; i++) {
            outputByte[i+1] = (byte)amounts[i];
        outputByte[i+2] = 127;
        }
        System.out.println(Arrays.toString(outputByte));
        if (GetInfo.getStatus() == 200) ArduinoSerial.sendSerial(outputByte);
    }
    private int getTotal(int[] amounts) {
        int total = 0;
        for (int i = 0; i < 4; i++) {
            total += amounts[i] * Bills.getMultiplier()[i];
        }
        return total;
    }
}
