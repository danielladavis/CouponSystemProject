package com.CouponSystem.springboot.myCouponSystem.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CouponSystem.springboot.myCouponSystem.entities.Coupon;
import com.CouponSystem.springboot.myCouponSystem.enums.CouponCategoryType;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.services.CouponService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/coupons")
public class CouponController {

	// Attribute

	@Autowired
	private CouponService couponService;

	// Constructor

	// Methods

	@PostMapping("/{companyID}/{imageID}")
	public void createCoupon(@RequestBody Coupon coupon, @PathVariable("companyID") long companyID, @PathVariable("imageID") long imageID)
			throws ApplicationException {
		this.couponService.createCoupon(coupon, companyID, imageID);
	}

	@DeleteMapping("/{couponID}")
	public void removeCoupon(@PathVariable("couponID") long couponID) throws ApplicationException {
		this.couponService.removeCoupon(couponID);
	}

	@DeleteMapping("/byCompany/{companyID}")
	public void removeCompanyCoupons(@PathVariable("companyID") long companyID) throws ApplicationException {
		this.couponService.removeCompanyCoupons(companyID);
	}

	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		this.couponService.updateCoupon(coupon);
	}

	@GetMapping("/{couponID}")
	public Coupon getCouponById(@PathVariable("couponID") long couponID) throws ApplicationException {
		return this.couponService.getCouponById(couponID);
	}

	@GetMapping("/byTitle")
	public Coupon getCouponByTitle(@RequestParam("couponTitle") String couponTitle) throws ApplicationException {
		return this.couponService.getCouponByTitle(couponTitle);
	}

	@GetMapping("/byCompanyTitle")
	public Coupon getCompanyCouponByTitle(@RequestParam("companyID") long companyID,
			@RequestParam("couponTitle") String couponTitle) throws ApplicationException {
		return this.couponService.getCompanyCouponByTitle(companyID, couponTitle);
	}

	@GetMapping
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return this.couponService.getAllCoupons();
	}

	@GetMapping("/byCompany")
	public List<Coupon> getCompanyCoupons(@RequestParam("companyID") long companyID) throws ApplicationException {
		return this.couponService.getCompanyCoupons(companyID);
	}

	@GetMapping("/byCompanyCategory")
	public List<Coupon> getCompanyCouponsByCategoryType(@RequestParam("companyID") long companyID,
			@RequestParam("categoryType") CouponCategoryType categoryType) throws ApplicationException {
		return this.couponService.getCompanyCouponsByCategoryType(companyID, categoryType);
	}

	@GetMapping("/byCategory")
	public List<Coupon> getCouponsByCategoryType(@RequestParam("categoryType") CouponCategoryType categoryType)
			throws ApplicationException {
		return this.couponService.getCouponsByCategoryType(categoryType);
	}

	@GetMapping("/byCompanyMaxPrice")
	public List<Coupon> getCompanyCouponsByMaxPrice(@RequestParam("companyID") long companyID,
			@RequestParam("couponPrice") double couponPrice) throws ApplicationException {
		return this.couponService.getCompanyCouponsByMaxPrice(companyID, couponPrice);
	}

	@GetMapping("/byMaxPrice")
	public List<Coupon> getCouponsByMaxPrice(@RequestParam("couponPrice") double couponPrice)
			throws ApplicationException {
		return this.couponService.getCouponsByMaxPrice(couponPrice);
	}

	@GetMapping("/byCompanyDate")
	public List<Coupon> getCompanyCouponsUpToCertainDate(@RequestParam("companyID") long companyID,
			@RequestParam("endDate") Date endDate) throws ApplicationException {
		return this.couponService.getCompanyCouponsUpToCertainDate(companyID, endDate);
	}

	@GetMapping("/byDate")
	public List<Coupon> getCouponsUpToCertainDate(@RequestParam("endDate") Date endDate) throws ApplicationException {
		return this.couponService.getCouponsUpToCertainDate(endDate);
	}

}
