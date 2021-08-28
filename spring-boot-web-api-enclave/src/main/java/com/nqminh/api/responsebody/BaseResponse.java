package com.nqminh.api.responsebody;

public class BaseResponse {

	private int statusCode;

	private String statusMessage;

	private Object body;

	public BaseResponse() {
	}

	public BaseResponse(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public BaseResponse(int statusCode, String statusMessage, Object body) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.body = body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	// -------------------------------------------------------------------------
	//
	//	public class Header {
	//
	//		private int statusCode;
	//		private String statusMessage;
	//
	//		public Header() {
	//		}
	//
	//		public Header(int statusCode, String statusMessage) {
	//			this.statusCode = statusCode;
	//			this.statusMessage = statusMessage;
	//		}
	//
	//		public int getStatusCode() {
	//			return statusCode;
	//		}
	//
	//		public void setStatusCode(int statusCode) {
	//			this.statusCode = statusCode;
	//		}
	//
	//		public String getStatusMessage() {
	//			return statusMessage;
	//		}
	//
	//		public void setStatusMessage(String statusMessage) {
	//			this.statusMessage = statusMessage;
	//		}
	//
	//	}

}
