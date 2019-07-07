package com.CouponSystem.springboot.myCouponSystem.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CouponSystem.springboot.myCouponSystem.entities.Coupon;
import com.CouponSystem.springboot.myCouponSystem.entities.Customer;
import com.CouponSystem.springboot.myCouponSystem.entities.Income;
import com.CouponSystem.springboot.myCouponSystem.entities.Purchase;
import com.CouponSystem.springboot.myCouponSystem.entities.UserLoginDetails;
import com.CouponSystem.springboot.myCouponSystem.enums.ClientType;
import com.CouponSystem.springboot.myCouponSystem.enums.CouponCategoryType;
import com.CouponSystem.springboot.myCouponSystem.enums.ErrorType;
import com.CouponSystem.springboot.myCouponSystem.enums.IncomeDescription;
import com.CouponSystem.springboot.myCouponSystem.enums.RepoType;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.repositories.PurchaseRepository;
import com.CouponSystem.springboot.myCouponSystem.utils.JpaUtils;

@Service
public class PurchaseService {

	// Attribute

	@Autowired
	private PurchaseRepository purchaseRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private JpaUtils jpaUtils;

	// Constructor

	/**
	 * Public constructor. Constructs a new {@code ProductService} object.
	 */
	public PurchaseService() {

	}

	// Methods

	@Transactional
	public void purchase(Purchase purchase, long userID, long couponID) throws ApplicationException {

//			boolean purchaseCoupon = true;

		// Not a customer!
		UserLoginDetails user = this.userService.getUserById(userID);
		if (user.getUserClientType() != ClientType.CUSTOMER) {
//						purchaseCoupon = false;
			throw new ApplicationException(ErrorType.PURCHASING_COUPONS_ARE_FOR_CUSTOMERS_ONLY,
					ErrorType.PURCHASING_COUPONS_ARE_FOR_CUSTOMERS_ONLY.getExternalMessage());
		}

		// SOLD-OUT!
		if (!isUnitsInStock(purchase.getPurchaseAmount(), couponID)) {
//				purchaseCoupon = false;
			throw new ApplicationException(ErrorType.NOT_ENOUGH_UNITS_IN_STOCK,
					ErrorType.NOT_ENOUGH_UNITS_IN_STOCK.getExternalMessage());
		}

		// Expired!
		if (this.couponService.checkIfExpired(couponID)) {
			System.out.println("This coupon has EXPIRED!");
//				purchaseCoupon = false;
			throw new ApplicationException(ErrorType.COUPON_EXPIRED, ErrorType.COUPON_EXPIRED.getExternalMessage());
		}

//			if (purchaseCoupon) {
		purchase.setUser(this.userService.getUserById(userID));

		// Decrease units in stock!
		Coupon couopn = this.couponService.getCouponById(couponID);
		couopn.setUnitsInStock(couopn.getUnitsInStock() - purchase.getPurchaseAmount());
		purchase.setCoupon(couopn);

		// Set purchase date to current time!
		purchase.setPurchaseDate(Date.valueOf(LocalDate.now()));

		this.purchaseRepo.save(purchase);
		Customer customer = this.customerService.getCustomerById(purchase.getUser().getUserID());
		Income income = new Income(customer.getCustomerName(), Date.valueOf(LocalDate.now()),
				IncomeDescription.CUSTOMER_PURCHASE, 10 * purchase.getPurchaseAmount(), couopn.getCompany());
		this.incomeService.createIncome(income);
		System.out.println("Purchase was successful");
//			} 
	}

	@Transactional
	public void removePurchase(long purchaseID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.purchaseRepo, purchaseID, RepoType.PURCHASE);
		this.purchaseRepo.deleteById(purchaseID);
		System.out.println("Purchase was removed successfuly");
	}

	@Transactional
	public void removeCustomerPurchases(long userID) throws ApplicationException {
		this.purchaseRepo.findByUser(this.userService.getUserById(userID))
				.forEach(purchase -> this.purchaseRepo.delete(purchase));
		System.out.println("Company coupons were removed successfuly");
	}

	public Purchase getPurchaseById(long purchaseID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.purchaseRepo, purchaseID, RepoType.PURCHASE);
		return this.purchaseRepo.findById(purchaseID).get();
	}

	public List<Purchase> getAllPurchase() throws ApplicationException {
		List<Purchase> purchaseList = new ArrayList<>();
		this.purchaseRepo.findAll().forEach(purchase -> purchaseList.add(purchase));
		return purchaseList;
	}

	@Transactional
	public List<Purchase> getCustomerPurchases(long userID) throws ApplicationException {
		List<Purchase> purchaseList = new ArrayList<>();
		this.purchaseRepo.findByUser(this.userService.getUserById(userID))
				.forEach(purchase -> purchaseList.add(purchase));
		return purchaseList;
	}

	@Transactional
	public List<Purchase> getCompanyCustomerPurchases(long companyID, long userID) throws ApplicationException {
		List<Purchase> purchaseList = new ArrayList<>();
		this.purchaseRepo.getCompanyCustomerPurchases(this.companyService.getCompanyById(companyID),
				this.userService.getUserById(userID)).forEach(purchase -> purchaseList.add(purchase));
		return purchaseList;
	}

	@Transactional
	public List<Purchase> getCompanyPurchases(long companyID) throws ApplicationException {
		List<Purchase> purchaseList = new ArrayList<>();
		this.purchaseRepo.getCompanyPurchases(this.companyService.getCompanyById(companyID))
				.forEach(purchase -> purchaseList.add(purchase));
		return purchaseList;
	}

	@Transactional
	public List<Purchase> getByCategoryType(CouponCategoryType categoryType) throws ApplicationException {
		List<Purchase> purchaseList = new ArrayList<>();
		this.purchaseRepo.getByCategoryType(categoryType).forEach(purchase -> purchaseList.add(purchase));
		return purchaseList;
	}

	@Transactional
	public List<Purchase> getByMaxPrice(double couponPrice) throws ApplicationException {
		List<Purchase> purchaseList = new ArrayList<>();
		this.purchaseRepo.getByMaxPrice(couponPrice).forEach(purchase -> purchaseList.add(purchase));
		return purchaseList;
	}

	public List<Purchase> getUpToCertainDate(Date endDate) throws ApplicationException {
		List<Purchase> purchaseList = new ArrayList<>();
		this.purchaseRepo.getByEndDate(endDate).forEach(purchase -> purchaseList.add(purchase));
		return purchaseList;
	}

	public boolean isUnitsInStock(int purchaseAmount, long couponID) throws ApplicationException {

		Coupon coupon = this.couponService.getCouponById(couponID);

		if (purchaseAmount <= coupon.getUnitsInStock()) {

			return true;
		} else {
			System.out.println(
					"There is not enough units in stock.\nCurrent units in stock: " + coupon.getUnitsInStock() + ".");
			return false;
		}
	}
}
