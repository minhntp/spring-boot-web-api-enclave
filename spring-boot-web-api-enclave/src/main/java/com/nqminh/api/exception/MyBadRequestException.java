package com.nqminh.api.exception;

public class MyBadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898441569563883173L;

	public MyBadRequestException(String message) {
		super(message);
	}
}
