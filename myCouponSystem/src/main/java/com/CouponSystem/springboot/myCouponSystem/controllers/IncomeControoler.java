package com.CouponSystem.springboot.myCouponSystem.controllers;

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

import com.CouponSystem.springboot.myCouponSystem.entities.Income;
import com.CouponSystem.springboot.myCouponSystem.enums.IncomeDescription;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.services.IncomeService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/incomes")
public class IncomeControoler {

	// Attribute

	@Autowired
	private IncomeService incomeService;

	// Methods

	@PostMapping
	public void createIncome(@RequestBody Income income) throws ApplicationException {
		this.incomeService.createIncome(income);
	}

	@DeleteMapping("/{incomeID}")
	public void removeIncome(@PathVariable("incomeID") long incomeID) throws ApplicationException {
		this.incomeService.removeIncome(incomeID);
	}

	@PutMapping
	public void changeIncomeCompanyToNull(@RequestParam("companyID") long companyID) throws ApplicationException {
		this.incomeService.changeIncomeCompanyToNull(companyID);
	}

	@GetMapping("/{id}")
	public Income getIncomeById(@PathVariable("id") long incomeID) throws ApplicationException {
		return this.incomeService.getIncomeById(incomeID);
	}

	@GetMapping("/byDescription")
	public List<Income> getIncomesByDescription(@RequestParam("incomeDescription") IncomeDescription incomeDescription)
			throws ApplicationException {
		return this.incomeService.getIncomesByDescription(incomeDescription);
	}

	@GetMapping("/byCompany")
	public List<Income> getIncomesByCompany(@RequestParam("companyID") long companyID) throws ApplicationException {
		return this.incomeService.getIncomesByCompany(companyID);
	}

	@GetMapping
	public List<Income> getAllIncomes() throws ApplicationException {
		return this.incomeService.getAllIncomes();
	}

}
