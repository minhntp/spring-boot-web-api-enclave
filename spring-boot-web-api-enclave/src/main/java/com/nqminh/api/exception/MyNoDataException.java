package com.nqminh.api.exception;

public class MyNoDataException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8607948792119557579L;

	public MyNoDataException() {
		super("No data");
	}

	public MyNoDataException(String message) {
		super(message);
	}

}
