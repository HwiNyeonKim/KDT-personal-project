package com.example.cccoffeebackendfromatoz.utils;

import java.util.regex.Pattern;

public class ValidationUtils {
	public static boolean validateEmail(String email) {
		return Pattern.matches("\\b[\\w.-]+@[\\w.-]+\\.\\w{2,4}\\b", email);
	}
}
