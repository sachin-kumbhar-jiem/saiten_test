package com.saiten.exception;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class SaitenRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -1L;

	private String errorCode;

	private String message;

	/**
	 * @param errorCode
	 */
	public SaitenRuntimeException(String errorCode) {
		this(errorCode, null, null);
	}

	/**
	 * @param errorCode
	 * @param throwable
	 */
	public SaitenRuntimeException(String errorCode, Throwable throwable) {
		this(errorCode, null, throwable);
	}

	/**
	 * @param errorCode
	 * @param message
	 */
	public SaitenRuntimeException(String errorCode, String message) {
		this(errorCode, message, null);
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public SaitenRuntimeException(String errorCode, String message,
			Throwable throwable) {
		super(throwable);
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
