package com.CouponSystem.springboot.myCouponSystem.enums;

public enum ErrorType {

	INCOME_DOES_NOT_EXIST(false, 601, "Income does not exist"),
	COMPANY_ALREADY_EXISTS(false, 602, "Company already exists"),
	COMPANY_DOES_NOT_EXIST(false, 603, "Company does not exist"),
	COUPON_ALREADY_EXIST(false, 604, "Coupon already exists"),
	COUPON_DOES_NOT_EXIST(false, 605, "Coupon does not exist"),
	CUSTOMER_ALREADY_EXIST(false, 606, "Customer already exists"),
	CUSTOMER_DOES_NOT_EXIST(false, 607, "Customer does not exists"),
	USER_DOES_NOT_EXIST(false, 608, "User does not exists"),
	ACCOUNT_ALREADY_EXIST(false, 609, "Account already exists"),
	ACCOUNT_DOES_NOT_EXIST(false, 610, "Account does not exist"),
	PURCHASE_DOES_NOT_EXIST(false, 611, "Purchase does not exist"),
	INCORRECT_PASSWORD(false, 612, "Incorrect password"),
	NOT_ENOUGH_UNITS_IN_STOCK(false, 613, "Not enough units in stock"),
	COUPON_EXPIRED(false, 614, "This coupon has EXPIRED"),
	PURCHASING_COUPONS_ARE_FOR_CUSTOMERS_ONLY(false, 615, "Purchasing coupons are for customers only"),
	IMAGE_NAME_INVALID(false, 616, "Image name contains invalid path sequence"),
	IMAGE_FILE_NOT_STORED(false, 617, "Could not store image file"),
	IMAGE_DOES_NOT_EXIST(false, 618, "Image does not exist"),
	GENERAL_ERROR(true, 700, "General error");

	// Attribute

	private int internalErrorCode;
	private String externalMessage;
	private boolean isPrintStackTrace;

	// private Constructor

	private ErrorType(boolean isPrintStackTrace, int internalErrorCode, String internalMessage) {
		this.isPrintStackTrace = isPrintStackTrace;
		this.internalErrorCode = internalErrorCode;
		this.externalMessage = internalMessage;

	}

	// Getters

	public boolean isPrintStackTrace() {
		return isPrintStackTrace;
	}

	public int getInternalErrorCode() {
		return internalErrorCode;
	}

	public String getExternalMessage() {
		return externalMessage;
	}

}
