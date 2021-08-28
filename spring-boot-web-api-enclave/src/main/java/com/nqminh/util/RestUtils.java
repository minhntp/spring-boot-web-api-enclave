package com.nqminh.util;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.nqminh.api.responsebody.BaseResponse;

@Component
public class RestUtils {

	public final String CODE = "statusCode";
	public final String MESSAGE = "statusMessage";
	public final String LOCATION = "Location";
	public final String SUCCESS = "Success";
	public final String CREATED = "Created";
	public final String INVALID_DATA = "Invalid data";
	//	public final String CODE_404 = "Not found";

	public HttpHeaders getHeaders(BaseResponse baseResponse) {

		HttpHeaders headers = new HttpHeaders();

		headers.set(CODE, Integer.toString(baseResponse.getStatusCode()));
		headers.set(MESSAGE, baseResponse.getStatusMessage());

		return headers;
	}

	public HttpHeaders getHeaders(int statusCode, String statusMessage) {

		HttpHeaders headers = new HttpHeaders();

		headers.set(CODE, Integer.toString(statusCode));
		headers.set(MESSAGE, statusMessage);

		return headers;
	}

}
