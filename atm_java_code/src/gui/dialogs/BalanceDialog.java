package gui.dialogs;

import com.google.gson.Gson;
import gui.GUI;
import gui.dialogs.prosessors.PinProcessor;
import gui.dialogs.prosessors.KeyCardProcessor;
import gui.language.Languages;
import gui.pages.HomePage;
import server.BankingData;
import server.GetInfo;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

public class BalanceDialog extends ServerCommDialog{
    private static final String API_ENDPOINT = "accountinfo";

    protected void comm(String keyCard, String code) {
        String db = "";
        KeyCard card = new KeyCard(keyCard);
        try {
            db = GetInfo.post(BANK_IP + API_ENDPOINT + "?target=" + card.getIban(),
                    "{\"pincode\": \"" + code + "\",\"uid\": \"" + card.getUid() + "\"}");
        } catch (IOException e) {
            System.out.println("Balance check went wrong");
        }
        if (GetInfo.getStatus() == 200) {
            getDisplayText().setText(toString(db));
            delay(40000);
            GUI.gotoPage(HomePage.KEY);
        }
        else handleServerResponseNotOK(db);

    }
    private String toString(String json) {
        Gson gson = new Gson();
        BankingData a = gson.fromJson(json.replaceAll("[<>]",""), BankingData.class);
        String toReturn = Languages.getLang().getBalance_info();
        String firstName = a.getFirstname().substring(0, Math.min(a.getFirstname().length(), 30));
        String lastName = a.getLastname().substring(0, Math.min(a.getLastname().length(), 30));
        toReturn = toReturn.replace("%n",firstName + ' ' + lastName);
        String amount = String.format("%.2f",(double) a.getBalance() * 0.008);
        toReturn = toReturn.replace("%b","£" + amount);
        return "<html>" + toReturn + "</html>";
    }
    @Override
    protected void startUp() {

        getUseKeypad().getDisplayText().setVisible(false);
        // keycard reader
        String keyCard;
        KeyCardProcessor keyCardProcessor = new KeyCardProcessor(getDisplayText());
        keyCard = keyCardProcessor.getRfid();
        if (keyCard == null) {
            System.out.println("Could not get rfid.");
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
        comm(keyCard, pin);
    }
}
