package de.milchreis.jcopy;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {

	private ResourceBundle messages;
	private static Language instance;
	
	private Language() {
		messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
	}
	
	public static Language getInstance() {
		if(instance == null) {
			instance = new Language();
		}
		return instance;
	}
	
	public String get(String key) {
		try {
			return messages.getString(key);
		} catch (Exception e) {
			return key;
		}
	}
	
	public void changeLocale(Locale locale) {
		try {
			messages = ResourceBundle.getBundle("MessagesBundle", locale);
		} catch (Exception e) {
			messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
		}
	}
	
}
