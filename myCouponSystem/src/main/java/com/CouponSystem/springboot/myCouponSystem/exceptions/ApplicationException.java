package com.CouponSystem.springboot.myCouponSystem.exceptions;

import com.CouponSystem.springboot.myCouponSystem.enums.ErrorType;

@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	// Attribute

	private ErrorType errorType;

	//

	// This is used when the exception is intentionally thrown
	public ApplicationException(ErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;
	}

	public ApplicationException(ErrorType errorType, String message, Exception e) {
		super(message, e);
		this.errorType = errorType;
	}

	// Getters

	public ErrorType getErrorType() {
		return errorType;
	}

}
