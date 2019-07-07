package com.CouponSystem.springboot.myCouponSystem.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Table
@SuppressWarnings("serial")
@Entity
@Table(name = "customers")
public class Customer implements Serializable {

	// Columns

	@Id
	private long customerID;

	@Column(name = "customer_name", unique = true, nullable = false, length = 40)
	private String customerName;

	// Hibernate mapping

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn
	@MapsId
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private UserLoginDetails user;

	// Constructor

	/**
	 * Public no-argument constructor. Constructs a new {@code Customer} object with
	 * no params.
	 */
	public Customer() {
	}

	/**
	 * Public constructor. Constructs a new {@code Customer} object with the
	 * following params.
	 * 
	 * @param customerName The {@code Customer} object name.
	 */
	public Customer(String customerName) {
		this.customerName = customerName;
	}

	// Getters & Setters

	/**
	 * Gets the {@code Customer}'s name.
	 * 
	 * @return A String representing the {@code Customer}'s name.
	 */
	public String getCustomerName() {
		return this.customerName;
	}

	/**
	 * Sets the {@code Customer}'s name.
	 * 
	 * @param customerName A String containing the {@code Customer}'s name.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the {@code Customer}'s user.
	 * 
	 * @return A UserLoginDetails representing the {@code Customer}'s user.
	 */
	public UserLoginDetails getUser() {
		return this.user;
	}

	/**
	 * Sets the {@code Customer}'s user.
	 * 
	 * @param user A UserLoginDetails containing the {@code Customer}'s user.
	 */
	public void setUser(UserLoginDetails user) {
		this.user = user;
	}

}
