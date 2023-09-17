package com.tictoc.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

public class Validations {

	public static String bindingError(BindingResult result) {

		Map<String, String> errors = new HashMap<>();
		result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		String errorMsg = "";
		for (String key : errors.keySet()) {
			errorMsg += key + ": " + errors.get(key) + "\n";
		}
		return errorMsg;
	}

}
