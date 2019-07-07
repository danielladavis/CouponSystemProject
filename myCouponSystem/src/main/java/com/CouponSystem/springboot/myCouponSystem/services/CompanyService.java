package com.CouponSystem.springboot.myCouponSystem.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CouponSystem.springboot.myCouponSystem.entities.Company;
import com.CouponSystem.springboot.myCouponSystem.enums.ErrorType;
import com.CouponSystem.springboot.myCouponSystem.enums.RepoType;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.repositories.CompanyRepository;
import com.CouponSystem.springboot.myCouponSystem.utils.JpaUtils;

@Service
public class CompanyService {

	// Attribute

	@Autowired
	private CompanyRepository companyRepo;

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private JpaUtils jpaUtils;

	// Constructor

	/**
	 * Public constructor. Constructs a new {@code CompanyService} object.
	 */
	public CompanyService() {

	}

	// Methods

	public void createCompany(Company company) throws ApplicationException {
		try {
			this.companyRepo.save(company);
			System.out.println("Company was created successfuly");
		} catch (Exception e) {
			throw new ApplicationException(ErrorType.COMPANY_ALREADY_EXISTS,
					ErrorType.COMPANY_ALREADY_EXISTS.getExternalMessage());
		}

	}

	@Transactional
	public void removeCompany(long companyID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.companyRepo, companyID, RepoType.COMPANY);
		this.incomeService.changeIncomeCompanyToNull(companyID);
		this.companyRepo.deleteById(companyID);
		System.out.println("Company was removed successfuly");

	}

	public Company getCompanyById(long companyID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.companyRepo, companyID, RepoType.COMPANY);
		return this.companyRepo.findById(companyID).get();
	}

	public Company getCompanyByName(String companyName) throws ApplicationException {
		Company company = this.companyRepo.findByCompanyName(companyName);
		return company;
	}

	public List<Company> getAllCompanies() throws ApplicationException {
		List<Company> companyList = new ArrayList<>();
		this.companyRepo.findAll().forEach(company -> companyList.add(company));
		return companyList;
	}

}
