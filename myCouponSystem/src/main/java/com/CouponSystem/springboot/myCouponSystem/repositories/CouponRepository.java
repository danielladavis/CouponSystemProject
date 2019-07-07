package com.CouponSystem.springboot.myCouponSystem.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystem.springboot.myCouponSystem.entities.Company;
import com.CouponSystem.springboot.myCouponSystem.entities.Coupon;
import com.CouponSystem.springboot.myCouponSystem.enums.CouponCategoryType;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {

	public Coupon findByCouponTitle(String couponTitle);

	public Coupon findByCompanyAndCouponTitle(Company company, String couponTitle);

	public List<Coupon> findByCompany(Company company);

	public List<Coupon> findByCompanyAndCategoryType(Company company, CouponCategoryType categoryType);

	public List<Coupon> findByCategoryType(CouponCategoryType categoryType);

	@Query("SELECT c FROM Coupon c WHERE company = ?1 AND couponPrice <= ?2")
	public List<Coupon> getCompanyCouponsByMaxPrice(Company company, double couponPrice);

	@Query("SELECT c FROM Coupon c WHERE couponPrice <= ?1")
	public List<Coupon> getCouponsByMaxPrice(double couponPrice);

	@Query("SELECT c FROM Coupon c WHERE company = ?1 AND endDate <= ?2")
	public List<Coupon> getByCompanyAndEndDate(Company company, Date endDate);

	@Query("SELECT c FROM Coupon c WHERE endDate <= ?1")
	public List<Coupon> getByEndDate(Date endDate);

	public List<Coupon> findByEndDateBefore(Date endDate);

}
