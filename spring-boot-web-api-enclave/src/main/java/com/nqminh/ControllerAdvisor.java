package com.nqminh;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nqminh.api.exception.MyBadRequestException;
import com.nqminh.api.exception.MyExceptionResponse;
import com.nqminh.api.exception.MyNoDataException;
import com.nqminh.api.exception.MyNotFoundException;
import com.nqminh.api.responsebody.BaseResponse;
import com.nqminh.util.RestUtils;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	// initBinder to trim input strings for all controllers
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// handle exceptions

	@Autowired
	private RestUtils restUtils;

	@ExceptionHandler(MyNoDataException.class)
	public ResponseEntity<Object> handleNoDataException(MyNoDataException ex, WebRequest request) {

		BaseResponse response = new BaseResponse(204, "No data", new Object());

		return new ResponseEntity<Object>(
				restUtils.getHeaders(response),
				HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(MyNotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {

		BaseResponse response = new BaseResponse(404, ex.getMessage());

		return new ResponseEntity<Object>(
				restUtils.getHeaders(response),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MyBadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(MyBadRequestException ex, WebRequest request) {

		BaseResponse response = new BaseResponse(400, ex.getMessage());

		return new ResponseEntity<Object>(
				restUtils.getHeaders(response),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, WebRequest request) {

		//		Map<String, String> errors = new HashMap<>();
		//
		String errorMessage = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		//
		//		errors.put("type mismatch", errorMessage);
		//		errors.put("localized message", ex.getLocalizedMessage());

		//		MyExceptionResponse responseBody = new MyExceptionResponse(errors);

		return new ResponseEntity<Object>(
				restUtils.getHeaders(400, errorMessage),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllOtherExceptions(Exception ex, WebRequest request) {

		System.out.println(getClass() + ": Handle all other requests...");

		BaseResponse response = new BaseResponse(400, ex.getMessage());

		return new ResponseEntity<Object>(
				restUtils.getHeaders(response),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {

			String field = ((FieldError) error).getField();
			String message = error.getDefaultMessage();

			errors.put(field, message);
		}

		MyExceptionResponse responseBody = new MyExceptionResponse(errors);

		return new ResponseEntity<Object>(
				responseBody,
				restUtils.getHeaders(400, restUtils.INVALID_DATA),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		StringBuilder errorMessage = new StringBuilder();

		errorMessage.append(ex.getMethod());
		errorMessage.append(" method is not supported at this Url. Supported methods: ");

		for (HttpMethod method : ex.getSupportedHttpMethods()) {

			errorMessage.append(method).append(" ");
		}

		return new ResponseEntity<Object>(
				restUtils.getHeaders(405, errorMessage.toString()),
				HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String errorMessage = "No mapping for: " + ex.getHttpMethod() + " " + ex.getRequestURL();

		return new ResponseEntity<Object>(
				restUtils.getHeaders(404, errorMessage),
				HttpStatus.NOT_FOUND);
	}

}
