package com.CouponSystem.springboot.myCouponSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CouponSystem.springboot.myCouponSystem.entities.Company;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.services.CompanyService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/companies")
public class CompanyController {

	// Attribute

	@Autowired
	private CompanyService companyService;

	// Methods

	@PostMapping
	public void createCompany(@RequestBody Company company) throws ApplicationException {
		this.companyService.createCompany(company);
	}

	@DeleteMapping("/{companyId}")
	public void removeCompany(@PathVariable("companyId") long companyID) throws ApplicationException {
		this.companyService.removeCompany(companyID);
	}

	@GetMapping("/{id}")
	public Company getCompanyById(@PathVariable("id") long companyID) throws ApplicationException {
		return this.companyService.getCompanyById(companyID);
	}

	@GetMapping("/byName")
	public Company getCompanyByName(@RequestParam("companyName") String companyName) throws ApplicationException {
		return this.companyService.getCompanyByName(companyName);
	}

	@GetMapping
	public List<Company> getAllCompanies() throws ApplicationException {
		return this.companyService.getAllCompanies();
	}

}
