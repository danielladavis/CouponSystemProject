package com.CouponSystem.springboot.myCouponSystem.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CouponSystem.springboot.myCouponSystem.entities.Company;
import com.CouponSystem.springboot.myCouponSystem.entities.Coupon;
import com.CouponSystem.springboot.myCouponSystem.entities.Income;
import com.CouponSystem.springboot.myCouponSystem.enums.CouponCategoryType;
import com.CouponSystem.springboot.myCouponSystem.enums.ErrorType;
import com.CouponSystem.springboot.myCouponSystem.enums.IncomeDescription;
import com.CouponSystem.springboot.myCouponSystem.enums.RepoType;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.repositories.CouponRepository;
import com.CouponSystem.springboot.myCouponSystem.utils.JpaUtils;

@Service
public class CouponService {

	// Attribute

	@Autowired
	private CouponRepository couponRepo;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ImageService imageService;

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private JpaUtils jpaUtils;

	// Constructor

	/**
	 * Public constructor. Constructs a new {@code CouponService} object.
	 */
	public CouponService() {
	}

	// Methods

	@Transactional(rollbackOn = Exception.class)
	public void createCoupon(Coupon coupon, long companyID, long imageID) throws ApplicationException {
		coupon.setCompany(this.companyService.getCompanyById(companyID));
		coupon.setImage(this.imageService.getImageByID(imageID));
		try {
			this.couponRepo.save(coupon);
			Income income = new Income(coupon.getCompany().getCompanyName(), Date.valueOf(LocalDate.now()),
					IncomeDescription.COMPANY_CREATE, 100, coupon.getCompany());
			this.incomeService.createIncome(income);
			System.out.println("Coupon was created successfuly");
		} catch (Exception e) {
			throw new ApplicationException(ErrorType.COUPON_ALREADY_EXIST,
					ErrorType.COUPON_ALREADY_EXIST.getExternalMessage());
		}
	}

	@Transactional
	public void removeCoupon(long couponID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.couponRepo, couponID, RepoType.COUPON);
		this.couponRepo.deleteById(couponID);
		System.out.println("Coupon was removed successfuly");
	}

	@Transactional
	public void removeCompanyCoupons(long companyID) throws ApplicationException {
		this.couponRepo.findByCompany(this.companyService.getCompanyById(companyID))
				.forEach(coupon -> this.couponRepo.delete(coupon));
		System.out.println("Company coupons were removed successfuly");

	}

	@Transactional
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		Coupon updatedCoupon = getCouponByTitle(coupon.getCouponTitle());
		if (updatedCoupon == null) {
			throw new ApplicationException(ErrorType.COUPON_DOES_NOT_EXIST,
					ErrorType.COUPON_DOES_NOT_EXIST.getExternalMessage());
		} else {
			if (coupon.getStartDate() != updatedCoupon.getStartDate() && coupon.getStartDate() != null) {
				updatedCoupon.setStartDate(coupon.getStartDate());
			}
			if (coupon.getEndDate() != updatedCoupon.getEndDate() && coupon.getEndDate() != null) {
				updatedCoupon.setEndDate(coupon.getEndDate());
			}
			if (coupon.getUnitsInStock() != updatedCoupon.getUnitsInStock() && coupon.getUnitsInStock() > 0) {
				updatedCoupon.setUnitsInStock(coupon.getUnitsInStock());
			}
			if (coupon.getCategoryType() != updatedCoupon.getCategoryType() && coupon.getCategoryType() != null) {
				updatedCoupon.setCategoryType(coupon.getCategoryType());
			}
			if (coupon.getCouponMessage() != updatedCoupon.getCouponMessage()) {
				updatedCoupon.setCouponMessage(coupon.getCouponMessage());
			}
			if (coupon.getCouponPrice() != updatedCoupon.getCouponPrice() && coupon.getCouponPrice() >= 0) {
				updatedCoupon.setCouponPrice(coupon.getCouponPrice());
			}
			
			updatedCoupon.setImage(this.imageService.getImageByID(coupon.getImage().getImageID()));
	
			this.couponRepo.save(updatedCoupon);
			Income income = new Income(updatedCoupon.getCompany().getCompanyName(), Date.valueOf(LocalDate.now()),
					IncomeDescription.COMPANY_UPDATE, 10, updatedCoupon.getCompany());
			this.incomeService.createIncome(income);
			System.out.println("Coupon was updated successfuly");
		}
	}

	public Coupon getCouponById(long couponID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.couponRepo, couponID, RepoType.COUPON);
		return this.couponRepo.findById(couponID).get();
	}

	public Coupon getCouponByTitle(String couponTitle) throws ApplicationException {
		Coupon coupon = this.couponRepo.findByCouponTitle(couponTitle);
		return coupon;
	}

	public Coupon getCompanyCouponByTitle(long companyID, String couponTitle) throws ApplicationException {
		Company company = this.companyService.getCompanyById(companyID);
		Coupon coupon = this.couponRepo.findByCompanyAndCouponTitle(company, couponTitle);
		if (coupon == null) {
			throw new ApplicationException(ErrorType.COUPON_DOES_NOT_EXIST,
					ErrorType.COUPON_DOES_NOT_EXIST.getExternalMessage());
		}
		return coupon;
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {
		List<Coupon> couponList = new ArrayList<>();
		this.couponRepo.findAll().forEach(coupon -> couponList.add(coupon));
		return couponList;
	}

	public List<Coupon> getCompanyCoupons(long companyID) throws ApplicationException {
		List<Coupon> couponList = new ArrayList<>();
		this.couponRepo.findByCompany(this.companyService.getCompanyById(companyID))
				.forEach(coupon -> couponList.add(coupon));
		return couponList;
	}

	public List<Coupon> getCompanyCouponsByCategoryType(long companyID, CouponCategoryType categoryType)
			throws ApplicationException {
		List<Coupon> couponList = new ArrayList<>();
		this.couponRepo.findByCompanyAndCategoryType(this.companyService.getCompanyById(companyID), categoryType)
				.forEach(coupon -> couponList.add(coupon));
		return couponList;
	}

	public List<Coupon> getCouponsByCategoryType(CouponCategoryType categoryType) throws ApplicationException {
		List<Coupon> couponList = new ArrayList<>();
		this.couponRepo.findByCategoryType(categoryType).forEach(coupon -> couponList.add(coupon));
		return couponList;
	}

	public List<Coupon> getCompanyCouponsByMaxPrice(long companyID, double couponPrice) throws ApplicationException {
		List<Coupon> couponList = new ArrayList<>();
		this.couponRepo.getCompanyCouponsByMaxPrice(this.companyService.getCompanyById(companyID), couponPrice)
				.forEach(coupon -> couponList.add(coupon));
		return couponList;
	}

	public List<Coupon> getCouponsByMaxPrice(double couponPrice) throws ApplicationException {
		List<Coupon> couponList = new ArrayList<>();
		this.couponRepo.getCouponsByMaxPrice(couponPrice).forEach(coupon -> couponList.add(coupon));
		return couponList;
	}

	public List<Coupon> getCompanyCouponsUpToCertainDate(long companyID, Date endDate) throws ApplicationException {
		List<Coupon> couponList = new ArrayList<>();
		this.couponRepo.getByCompanyAndEndDate(this.companyService.getCompanyById(companyID), endDate)
				.forEach(coupon -> couponList.add(coupon));
		return couponList;
	}

	public List<Coupon> getCouponsUpToCertainDate(Date endDate) {
		List<Coupon> couponList = new ArrayList<>();
		this.couponRepo.getByEndDate(endDate).forEach(coupon -> couponList.add(coupon));
		return couponList;
	}

	public void removeAllExpiredCoupons(Date endDate) throws ApplicationException {
		this.couponRepo.findByEndDateBefore(endDate).forEach(coupon -> this.couponRepo.delete(coupon));
		System.out.println("Expired coupons were removed successfuly");
	}

	public boolean checkIfExpired(long couponID) throws ApplicationException {

		boolean expired = true;

		Coupon coupon = getCouponById(couponID);
		Date date = Date.valueOf(LocalDate.now());
		if (date.before(coupon.getEndDate())) {
			expired = false;
		}

		return expired;
	}

}
