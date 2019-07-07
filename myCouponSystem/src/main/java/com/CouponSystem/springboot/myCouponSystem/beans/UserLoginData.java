package com.CouponSystem.springboot.myCouponSystem.beans;

public class UserLoginData {

	private String userName;
	private String userPassword;

	public UserLoginData() {
	}

	public UserLoginData(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
