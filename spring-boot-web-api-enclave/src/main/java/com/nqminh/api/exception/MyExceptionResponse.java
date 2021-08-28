package com.nqminh.api.exception;

import java.util.Map;

public class MyExceptionResponse {

	private Map<String, String> errors;

	public MyExceptionResponse(Map<String, String> errors) {
		this.errors = errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
