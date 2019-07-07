package com.CouponSystem.springboot.myCouponSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystem.springboot.myCouponSystem.entities.Company;
import com.CouponSystem.springboot.myCouponSystem.entities.UserLoginDetails;

@Repository
public interface UserRepository extends CrudRepository<UserLoginDetails, Long> {

	UserLoginDetails findByUsername(String username);

	List<UserLoginDetails> findByCompany(Company company);

	@Query("SELECT u FROM  UserLoginDetails u JOIN u.company c WHERE u.company.companyID = c.companyID AND c.companyID = ?1 AND u.username = ?2")
	UserLoginDetails getCompanyEmployee(long companyID, String employeeName);
}
