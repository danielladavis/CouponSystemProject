package com.CouponSystem.springboot.myCouponSystem.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

// Table
@SuppressWarnings("serial")
@Entity
@Table(name = "companies")
public class Company implements Serializable {

	// Columns

	@Id
	@GeneratedValue
	@Column(name = "company_id")
	private long companyID;

	@Column(name = "company_name", unique = true, nullable = false, length = 40)
	private String companyName;

	@Column(name = "company_email", unique = true, nullable = true, length = 30)
	private String companyEmail;

	// Hibernate mapping

	@OneToMany(mappedBy = "company", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Coupon> companyCoupons;

	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UserLoginDetails> companyEmployees;

	// Constructors

	/**
	 * Public no-argument constructor. Constructs a new {@code Company} object with
	 * no params.
	 */
	public Company() {
	}

	/**
	 * Public constructor. Constructs a new {@code Customer} object with the
	 * following params.
	 * 
	 * @param companyName  The {@code Company} object name.
	 * @param companyEmail The {@code Company} object e-mail.
	 */
	public Company(String companyName, String companyEmail) {
		this.companyName = companyName;
		this.companyEmail = companyEmail;
	}

	// Getters & Setters

	/**
	 * Gets the {@code Company}'s ID.
	 * 
	 * @return A long representing the {@code Company}'s ID.
	 */
	public long getCompanyID() {
		return this.companyID;
	}

	/**
	 * Sets the {@code Company}'s ID.
	 * 
	 * @param companyID A long containing the {@code Company}'s ID.
	 */
	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	/**
	 * Gets the {@code Company}'s name.
	 * 
	 * @return A String representing the {@code Company}'s name.
	 */
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * Sets the {@code Company}'s name.
	 * 
	 * @param companyName A String containing the {@code Company}'s name.
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * Gets the {@code Company}'s e-mail.
	 * 
	 * @return A String representing the {@code Company}'s e-mail.
	 */
	public String getCompanyEmail() {
		return this.companyEmail;
	}

	/**
	 * Sets the {@code Company}'s e-mail.
	 * 
	 * @param companyEmail A String containing the {@code Company}'s e-mail.
	 */
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	/**
	 * Gets the {@code Company}'s coupons.
	 * 
	 * @return A List of {@code Coupon} objects representing the {@code Company}'s
	 *         coupons.
	 */
	public List<Coupon> getCompanyCoupons() {
		return this.companyCoupons;
	}

	/**
	 * Sets the {@code Company}'s coupons.
	 * 
	 * @param companyCoupons A List of {@code Coupon} objects containing the
	 *                       {@code Company}'s coupons.
	 */
	public void setCompanyCoupons(List<Coupon> companyCoupons) {
		this.companyCoupons = companyCoupons;
	}

	/**
	 * Gets the {@code Company}'s employees.
	 * 
	 * @return A List of {@code UserLoginDetails} objects representing the
	 *         {@code Company}'s employees.
	 */
	public List<UserLoginDetails> getCompanyEmployees() {
		return this.companyEmployees;
	}

	/**
	 * Sets the {@code Company}'s employees.
	 * 
	 * @param companyUsers A List of {@code Coupon} objects containing the
	 *                     {@code Company}'s employees.
	 */
	public void setCompanyEmployees(List<UserLoginDetails> companyEmployees) {
		this.companyEmployees = companyEmployees;
	}

}
