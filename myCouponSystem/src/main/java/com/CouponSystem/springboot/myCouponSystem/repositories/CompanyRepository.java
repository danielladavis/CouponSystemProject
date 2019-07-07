package com.CouponSystem.springboot.myCouponSystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystem.springboot.myCouponSystem.entities.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {

	public Company findByCompanyName(String companyName);

}
