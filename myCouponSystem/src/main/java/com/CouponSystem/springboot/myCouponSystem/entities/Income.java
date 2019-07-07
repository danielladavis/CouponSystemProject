package com.CouponSystem.springboot.myCouponSystem.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.CouponSystem.springboot.myCouponSystem.enums.IncomeDescription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Table
@SuppressWarnings("serial")
@Entity
@Table(name = "incomes")
public class Income implements Serializable {

	// Columns

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long incomeID;

	@Column(nullable = false, length = 40)
	private String operatingName;

	@Column(length = 40)
	private String companyName;

	@Column(nullable = false)
	private Date executionDate;

	@Column(nullable = false, length = 100)
	private IncomeDescription incomeDescription;

	@Column(nullable = false)
	private double incomePrice;

	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Company company;

	// Constructors

	/**
	 * Public no-argument constructor. Constructs a new {@code Income} object with
	 * no params.
	 */
	public Income() {
	}

	public Income(String operatingName, Date executionDate, IncomeDescription incomeDescription, double incomePrice,
			Company company) {
		this.operatingName = operatingName;
		this.executionDate = executionDate;
		this.incomeDescription = incomeDescription;
		this.incomePrice = incomePrice;
		this.company = company;
	}

	// Getters & Setters

	/**
	 * Gets the {@code Income}'s ID.
	 * 
	 * @return A long representing the {@code Income}'s ID.
	 */
	public long getIncomeID() {
		return this.incomeID;
	}

	/**
	 * Sets the {@code Income}'s ID.
	 * 
	 * @param incomeID A long containing the {@code Income}'s ID.
	 */
	public void setIncomeID(long incomeID) {
		this.incomeID = incomeID;
	}

	/**
	 * Gets the {@code Income}'s operating name.
	 * 
	 * @return A String representing the {@code Income}'s operating name.
	 */
	public String getOperatingName() {
		return this.operatingName;
	}

	/**
	 * Sets the {@code Income}'s operating name.
	 * 
	 * @param operatingName A String containing the {@code Income}'s operating name.
	 */
	public void setOperatingName(String operatingName) {
		this.operatingName = operatingName;
	}

	/**
	 * Gets the {@code Income}'s execution date.
	 * 
	 * @return A Date representing the {@code Income}'s execution date.
	 */
	public Date getExecutionDate() {
		return this.executionDate;
	}

	/**
	 * Sets the {@code Income}'s execution date.
	 * 
	 * @param executionDate A Date containing the {@code Income}'s execution date.
	 */
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	/**
	 * Gets the {@code Income}'s description.
	 * 
	 * @return An enum representing the {@code Income}'s description.
	 */
	public IncomeDescription getIncomeDescription() {
		return this.incomeDescription;
	}

	/**
	 * Sets the {@code Income}'s description.
	 * 
	 * @param incomeDescription An enum containing the {@code Income}'s description.
	 */
	public void setIncomeDescription(IncomeDescription incomeDescription) {
		this.incomeDescription = incomeDescription;
	}

	/**
	 * Gets the {@code Income}'s price.
	 * 
	 * @return A double representing the {@code Income}'s price.
	 */
	public double getIncomePrice() {
		return this.incomePrice;
	}

	public void setIncomePrice(double incomePrice) {
		this.incomePrice = incomePrice;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * Sets the {@code Income}'s price.
	 * 
	 * @param incomePrice A double containing the {@code Income}'s price.
	 */
	public void setIncomePricee(double incomePrice) {
		this.incomePrice = incomePrice;
	}

	/**
	 * Gets the {@code Income}'s company.
	 * 
	 * @return A Company representing the {@code Income}'s company.
	 */
	public Company getCompany() {
		return this.company;
	}

	/**
	 * Sets the {@code Income}'s coupon.
	 * 
	 * @param coupon A Coupon containing the {@code Income}'s coupon.
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

}
