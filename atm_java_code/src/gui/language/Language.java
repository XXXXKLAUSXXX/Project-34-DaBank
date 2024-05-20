package gui.language;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Language {
	private String lang; // language identifier (nl_NL, etc)
	public String getLang() {
		return lang;
	}

	// fields voor het opslaan van de vertalingen
	// pagina namen
	private String choice_name;
	public String getChoice_name() {return choice_name;}

	private String balance_name;
	public String getBalance_name() {return balance_name;}

	private String withdraw_menu_name;
	public String getWithdraw_menu_name() {return withdraw_menu_name;}

	private String custom_withdraw_name;
	public String getCustom_withdraw_name() {return custom_withdraw_name;}

	private String fast_withdraw_name;
	public String getFast_withdraw_name() {return fast_withdraw_name;}

	// knop tekst
	private String home_start;
	public String getHome_start() {return home_start;}

	private String home_button;
	public String getHome_button() {return home_button;}

	private String back_button;
	public String getBack_button() {return back_button;}

	private String main_menu_button;
	public String getMain_menu_button() {return main_menu_button;}

	private String balance_button;
	public String getBalance_button() {return balance_button;}

	private String withdraw_menu_button;
	public String getWithdraw_menu_button() {return withdraw_menu_button;}

	private String custom_withdraw_button;
	public String getCustom_withdraw_button() {return custom_withdraw_button;}

	private String fast_withdraw_button;
	public String getFast_withdraw_button() {return fast_withdraw_button;}

	private String lang_button;
	public String getLang_button() {return lang_button;}

	// dialoog teksten
	private String receipt_query;
	public String getReceipt_query() {return receipt_query;}

	private String receipt_aye;
	public String getReceipt_aye() {return receipt_aye;}

	private String receipt_nay;
	public String getReceipt_nay() {return receipt_nay;}

	private String amount_not_valid;
	public String getAmount_not_valid() {return amount_not_valid;}

	private String amount_withdraw;
	public String getAmount_withdraw() {return amount_withdraw;}

	private String balance_info;
	public String getBalance_info() {return balance_info;}

	// processor dialoog texten
	private String amount_query;
	public String getAmount_query() {return amount_query;}

	private String[] bills_query;
	public String[] getBills_query() {return bills_query;}

	private String keycard_query;
	public String getKeycard_query() {return keycard_query;}

	private String pin_query;
	public String getPin_query() {return pin_query;}

	// server responses
	private String ok;
	public String getOk() {return ok;}

	private String internal_error;
	public String getInternal_error() {return internal_error;}

	private String wrong_pin;
	public String getWrong_pin() {return wrong_pin;}

	private String blocked_account;
	public String getBlocked_account() {return blocked_account;}

	private String not_found;
	public String getNot_found() {return not_found;}

	private String no_balance;
	public String getNo_balance() {return no_balance;}

	private String server_error;
	public String getServer_error() {return server_error;}

	public static Language makeLanguage(Path langPath) throws IOException {
		try {
			String inputJson = Files.readString(langPath);
			Gson gson = new Gson();
			return gson.fromJson(inputJson, Language.class);
		}
		catch (IOException e) {
			System.out.println("File" + langPath + "not found!");
			throw new IOException("Can't create Language " + langPath);
		}
	}
}
