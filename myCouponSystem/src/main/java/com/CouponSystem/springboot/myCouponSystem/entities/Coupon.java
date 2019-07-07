package com.CouponSystem.springboot.myCouponSystem.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.CouponSystem.springboot.myCouponSystem.enums.CouponCategoryType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Table
@SuppressWarnings("serial")
@Entity
@Table(name = "coupons")
public class Coupon implements Serializable {

	// Columns

	@Id
	@GeneratedValue
	@Column(name = "coupon_id")
	private long couponID;

	@Column(name = "coupon_title", unique = true, nullable = false, length = 30)
	private String couponTitle;

	@Column(name = "coupon_start_date", nullable = false)
	private Date startDate;

	@Column(name = "coupon_end_date", nullable = false)
	private Date endDate;

	@Column(name = "coupon_units_in_stock", nullable = false)
	private int unitsInStock;

	@Column(name = "coupon_category_type", nullable = false, length = 20)
	private CouponCategoryType categoryType;

	@Column(name = "coupon_message", nullable = true)
	private String couponMessage;

	@Column(name = "coupon_price", nullable = false)
	private double couponPrice;

	// Hibernate mapping
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Image image;

	@ManyToOne
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Company company;

	@OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Purchase> couponPurchases;

	// Constructors

	/**
	 * Public no-argument constructor. Constructs a new {@code Coupon} object with
	 * no params.
	 */
	public Coupon() {
	}

	/**
	 * Public constructor. Constructs a new {@code Coupon} object with the following
	 * params.
	 * 
	 * @param couponTitle   The {@code Coupon} object title.
	 * @param startDate     The {@code Coupon} object start date.
	 * @param endDate       The {@code Coupon} object end date.
	 * @param unitsInStock  The {@code Coupon} object units in stock.
	 * @param categoryType  The {@code Coupon} object category type.
	 * @param couponMessage The {@code Coupon} object message.
	 * @param couponPrice   The {@code Coupon} object price.
	 * @param couponImage   The {@code Coupon} object image.
	 */
	public Coupon(String couponTitle, Date startDate, Date endDate, int unitsInStock, CouponCategoryType categoryType,
			String couponMessage, double couponPrice) {
		this.couponTitle = couponTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.unitsInStock = unitsInStock;
		this.categoryType = categoryType;
		this.couponMessage = couponMessage;
		this.couponPrice = couponPrice;
	}

	// Getters & Setters

	/**
	 * Gets the {@code Coupon}'s ID.
	 * 
	 * @return A long representing the {@code Coupon}'s ID.
	 */
	public long getCouponID() {
		return this.couponID;
	}

	/**
	 * Sets the {@code Coupon}'s ID.
	 * 
	 * @param couponID A long containing the {@code Coupon}'s ID.
	 */
	public void setCouponID(long couponID) {
		this.couponID = couponID;
	}

	/**
	 * Gets the {@code Coupon}'s title.
	 * 
	 * @return A String representing the {@code Coupon}'s title.
	 */
	public String getCouponTitle() {
		return this.couponTitle;
	}
	
	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	/**
	 * Sets the {@code Coupon}'s title.
	 * 
	 * @param couponTitle A String containing the {@code Coupon}'s title.
	 */
	public void setCouponTitlee(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	/**
	 * Gets the {@code Coupon}'s start date.
	 * 
	 * @return A Date representing the {@code Coupon}'s start date.
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * Sets the {@code Coupon}'s start date.
	 * 
	 * @param startDate A Date containing the {@code Coupon}'s start date.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the {@code Coupon}'s end date.
	 * 
	 * @return A Date representing the {@code Coupon}'s end date.
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * Sets the {@code Coupon}'s end date.
	 * 
	 * @param endDate A Date containing the {@code Coupon}'s end date.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the {@code Coupon}'s units in stock.
	 * 
	 * @return An int representing the {@code Coupon}'s units in stock.
	 */
	public int getUnitsInStock() {
		return this.unitsInStock;
	}

	/**
	 * Sets the {@code Coupon}'s units in stock.
	 * 
	 * @param unitsInStock An int containing the {@code Coupon}'s units in stock.
	 */
	public void setUnitsInStock(int unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	/**
	 * Gets the {@code Coupon}'s category type.
	 * 
	 * @return An enum representing the {@code Coupon}'s category type.
	 */
	public CouponCategoryType getCategoryType() {
		return this.categoryType;
	}

	/**
	 * Sets the {@code Coupon}'s category type.
	 * 
	 * @param categoryType An enum containing the {@code Coupon}'s category type.
	 */
	public void setCategoryType(CouponCategoryType categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * Gets the {@code Coupon}'s message.
	 * 
	 * @return A String representing the {@code Coupon}'s message.
	 */
	public String getCouponMessage() {
		return this.couponMessage;
	}

	/**
	 * Sets the {@code Coupon}'s message.
	 * 
	 * @param couponMessage A String containing the {@code Coupon}'s message.
	 */
	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}

	/**
	 * Gets the {@code Coupon}'s price.
	 * 
	 * @return A double representing the {@code Coupon}'s price.
	 */
	public double getCouponPrice() {
		return this.couponPrice;
	}

	/**
	 * Sets the {@code Coupon}'s price.
	 * 
	 * @param couponPrice A double containing the {@code Coupon}'s price.
	 */
	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}

	/**
	 * Gets the {@code Coupon}'s company.
	 * 
	 * @return A String representing the {@code Coupon}'s company.
	 */
	public Company getCompany() {
		return this.company;
	}

	/**
	 * Sets the {@code Coupon}'s company.
	 * 
	 * @param company A Company containing the {@code Coupon}'s company.
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the {@code Coupon}'s purchases.
	 * 
	 * @return A List representing the {@code Coupon}'s purchases.
	 */
	public List<Purchase> getCouponPurchases() {
		return this.couponPurchases;
	}

	/**
	 * Sets the {@code Coupon}'s purchases.
	 * 
	 * @param couponPurchases A List containing the {@code Coupon}'s purchases.
	 */
	public void setCouponPurchases(List<Purchase> couponPurchases) {
		this.couponPurchases = couponPurchases;
	}

	// Methods

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
