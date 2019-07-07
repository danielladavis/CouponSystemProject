package com.CouponSystem.springboot.myCouponSystem.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystem.springboot.myCouponSystem.entities.Company;
import com.CouponSystem.springboot.myCouponSystem.entities.Purchase;
import com.CouponSystem.springboot.myCouponSystem.entities.UserLoginDetails;
import com.CouponSystem.springboot.myCouponSystem.enums.CouponCategoryType;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

	public List<Purchase> findByUser(UserLoginDetails user);

	@Query("SELECT p FROM  Purchase p JOIN p.coupon c WHERE p.coupon.couponID = c.couponID AND c.company = ?1 AND p.user = ?2")
	public List<Purchase> getCompanyCustomerPurchases(Company company, UserLoginDetails user);

	@Query("SELECT p FROM  Purchase p JOIN p.coupon c WHERE p.coupon.couponID = c.couponID AND c.categoryType = ?1")
	public List<Purchase> getByCategoryType(CouponCategoryType categoryType);

	@Query("SELECT p FROM  Purchase p JOIN p.coupon c WHERE p.coupon.couponID = c.couponID AND c.couponPrice <= ?1")
	public List<Purchase> getByMaxPrice(double couponPrice);

	@Query("SELECT p FROM  Purchase p JOIN p.coupon c WHERE p.coupon.couponID = c.couponID AND c.endDate <= ?1")
	public List<Purchase> getByEndDate(Date endDate);

	@Query("SELECT p.user FROM  Purchase p JOIN p.coupon c WHERE p.coupon.couponID = c.couponID AND c.company = ?1")
	public List<UserLoginDetails> getCompanyCustomers(Company company);

	@Query("SELECT p FROM  Purchase p JOIN p.coupon c WHERE p.coupon.couponID = c.couponID AND c.company = ?1")
	public List<Purchase> getCompanyPurchases(Company company);

}
