package com.CouponSystem.springboot.myCouponSystem.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CouponSystem.springboot.myCouponSystem.entities.Company;
import com.CouponSystem.springboot.myCouponSystem.entities.Income;
import com.CouponSystem.springboot.myCouponSystem.enums.IncomeDescription;
import com.CouponSystem.springboot.myCouponSystem.enums.RepoType;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.repositories.IncomeRepository;
import com.CouponSystem.springboot.myCouponSystem.utils.JpaUtils;

@Service
public class IncomeService {

	// Attribute

	@Autowired
	private IncomeRepository incomeRepo;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private JpaUtils jpaUtils;

	// Constructor

	/**
	 * Public constructor. Constructs a new {@code CompanyService} object.
	 */
	public IncomeService() {

	}

	// Methods

	public void createIncome(Income income) throws ApplicationException {
		this.incomeRepo.save(income);
		System.out.println("Income was created successfuly");
	}

	@Transactional
	public void removeIncome(long incomeID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.incomeRepo, incomeID, RepoType.INCOME);
		this.incomeRepo.deleteById(incomeID);
		System.out.println("Income was removed successfuly");
	}

	public void changeIncomeCompanyToNull(long companyID) throws ApplicationException {
		Company company = this.companyService.getCompanyById(companyID);
		this.incomeRepo.findByCompany(company).forEach(income -> {
			income.setCompany(null);
			income.setCompanyName(company.getCompanyName());
		});
	}

	public Income getIncomeById(long incomeID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.incomeRepo, incomeID, RepoType.INCOME);
		return this.incomeRepo.findById(incomeID).get();
	}

	public List<Income> getAllIncomes() throws ApplicationException {
		List<Income> incomeList = new ArrayList<>();
		this.incomeRepo.findAll().forEach(income -> incomeList.add(income));
		return incomeList;
	}

	@Transactional
	public List<Income> getIncomesByCompany(long companyID) throws ApplicationException {
		List<Income> incomeList = new ArrayList<>();
		this.incomeRepo.findByCompany(this.companyService.getCompanyById(companyID))
				.forEach(income -> incomeList.add(income));
		return incomeList;
	}

	public List<Income> getIncomesByDescription(IncomeDescription incomeDescription) {
		List<Income> incomeList = new ArrayList<>();
		this.incomeRepo.findByIncomeDescription(incomeDescription).forEach(income -> incomeList.add(income));
		return incomeList;
	}

}
