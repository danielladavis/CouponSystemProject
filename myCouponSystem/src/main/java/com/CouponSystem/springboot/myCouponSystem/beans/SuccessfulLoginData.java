package com.CouponSystem.springboot.myCouponSystem.beans;

import org.springframework.stereotype.Component;

import com.CouponSystem.springboot.myCouponSystem.enums.ClientType;

@Component
public class SuccessfulLoginData {

	String userName;
	String token;
	ClientType clientType;

	public SuccessfulLoginData(String userName, String token, ClientType clientType) {
		this.userName = userName;
		this.token = token;
		this.clientType = clientType;
	}

	public SuccessfulLoginData() {
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ClientType getClientType() {
		return this.clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

}
