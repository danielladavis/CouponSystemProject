package com.CouponSystem.springboot.myCouponSystem.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystem.springboot.myCouponSystem.entities.Company;
import com.CouponSystem.springboot.myCouponSystem.entities.Income;
import com.CouponSystem.springboot.myCouponSystem.enums.IncomeDescription;

@Repository
public interface IncomeRepository extends CrudRepository<Income, Long> {

	public List<Income> findByCompany(Company company);

	public List<Income> findByIncomeDescription(IncomeDescription incomeDescription);

}
