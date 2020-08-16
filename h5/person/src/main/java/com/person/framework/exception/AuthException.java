package com.person.framework.exception;

public class AuthException extends RuntimeException {

	private static final long serialVersionUID = 8194358615981600779L;

	public AuthException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public AuthException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public AuthException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public AuthException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	protected String errorCode;

}
