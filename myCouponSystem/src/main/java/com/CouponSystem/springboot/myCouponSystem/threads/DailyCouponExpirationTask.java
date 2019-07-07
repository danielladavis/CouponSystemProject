package com.CouponSystem.springboot.myCouponSystem.threads;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.CouponSystem.springboot.myCouponSystem.entities.Coupon;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.services.CouponService;

@Component
public class DailyCouponExpirationTask {

	// Attributes

	@Autowired
	private CouponService couponService;

	// Methods

	/**
	 * Removes expired coupons from the DB. Gets all the {@code Coupon} objects from
	 * the DB and Checks, one by one, if the {@code Coupon} object has already
	 * expired. If its expired Removes this existing {@code Coupon} object from the
	 * DB.<br>
	 * 
	 * - Uses {@link DBDAOcoupon#getAllCoupons() getAllCoupons} method.<br>
	 * - Uses {@link DBDAOcoupon#checkIfCouponExpired(long) checkIfCouponExpired}
	 * method.<br>
	 * - Uses {@link DBDAOcoupon#removeCompanyCoupon(Coupon) removeCompanyCoupon}
	 * method.<br>
	 */
	@Scheduled(cron = "	0 0 0 1/1 * ?")
	public void runTask() throws ApplicationException {
		try {
			List<Coupon> coupons = this.couponService.getAllCoupons();
			for (Coupon c : coupons) {
				if (this.couponService.checkIfExpired(c.getCouponID())) {
					this.couponService.removeCoupon(c.getCouponID());
				}
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}