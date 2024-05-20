package gui.language;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public abstract class Languages {
	private static Language currentLanguage;
	private static final HashMap<String,Language> languages = new HashMap<>();
	static {
		Language nl_NL,en_US;
		try {
			nl_NL = Language.makeLanguage(Paths.get("resources/lang/nl_NL.json"));
			en_US = Language.makeLanguage(Paths.get("resources/lang/en_US.json"));
		} catch (IOException e) {
			nl_NL = new Language();
			en_US = new Language();
		}
		currentLanguage = nl_NL;
		languages.putIfAbsent(nl_NL.getLang(),nl_NL);
		languages.putIfAbsent(en_US.getLang(),en_US);
	}

	public static Language getLang() {
		return currentLanguage;
	}

	public static void setLang(String langId) {
		currentLanguage = languages.get(langId);
	}
}
