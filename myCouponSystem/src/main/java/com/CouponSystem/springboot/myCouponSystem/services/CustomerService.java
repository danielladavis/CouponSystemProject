package com.CouponSystem.springboot.myCouponSystem.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CouponSystem.springboot.myCouponSystem.entities.Customer;
import com.CouponSystem.springboot.myCouponSystem.entities.UserLoginDetails;
import com.CouponSystem.springboot.myCouponSystem.enums.ErrorType;
import com.CouponSystem.springboot.myCouponSystem.enums.RepoType;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.repositories.CustomerRepository;
import com.CouponSystem.springboot.myCouponSystem.repositories.PurchaseRepository;
import com.CouponSystem.springboot.myCouponSystem.repositories.UserRepository;
import com.CouponSystem.springboot.myCouponSystem.utils.JpaUtils;

@Service
public class CustomerService {

	// Attribute

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PurchaseRepository purchaseRepo;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private JpaUtils jpaUtils;

	// Constructor

	/**
	 * Public constructor. Constructs a new {@code CustomerService} object.
	 */
	public CustomerService() {

	}

	// Methods

	@Transactional
	public void createCustomer(Customer customer) throws ApplicationException {
		
		if (this.customerRepo.findByCustomerName(customer.getCustomerName()) != null) {
			throw new ApplicationException(ErrorType.CUSTOMER_ALREADY_EXIST,
					ErrorType.CUSTOMER_ALREADY_EXIST.getExternalMessage());
		}
		
		if (this.userRepo.findByUsername(customer.getUser().getUsername()) != null) {

			throw new ApplicationException(ErrorType.ACCOUNT_ALREADY_EXIST,
					ErrorType.ACCOUNT_ALREADY_EXIST.getExternalMessage());
		} else {
			try {
				UserLoginDetails managedUserEntity = this.userRepo.save(customer.getUser());
				customer.setUser(managedUserEntity);
				this.customerRepo.save(customer);
				System.out.println("Customer was created successfuly");
			} catch (Exception e) {
				throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getExternalMessage());
			}
		}
	}

	@Transactional
	public void removeCustomer(long customerID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.customerRepo, customerID, RepoType.CUSTOMER);
		this.customerRepo.deleteById(customerID);
		System.out.println("Customer was removed successfuly");
	}

	public Customer getCustomerById(long customerID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.customerRepo, customerID, RepoType.CUSTOMER);
		return this.customerRepo.findById(customerID).get();
	}

	public Customer getCustomerByName(String customerName) throws ApplicationException {
		Customer customer = this.customerRepo.findByCustomerName(customerName);
		return customer;
	}

	public Customer getCompanyCustomer(long companyID, String customerName) throws ApplicationException {
		List<Customer> customerList = getAllCompanyCustomers(companyID);
		return customerList.stream().filter(customer -> customer.getCustomerName().equals(customerName)).findFirst()
				.orElse(null);
	}

	public List<Customer> getAllCustomers() throws ApplicationException {
		List<Customer> customerList = new ArrayList<>();
		this.customerRepo.findAll().forEach(customer -> customerList.add(customer));
		return customerList;
	}

	@Transactional
	public List<Customer> getAllCompanyCustomers(long companyID) throws ApplicationException {
		List<Customer> customerList = new ArrayList<>();
		List<UserLoginDetails> userList = new ArrayList<>();
		this.purchaseRepo.getCompanyCustomers(this.companyService.getCompanyById(companyID))
				.forEach(user -> userList.add(user));

		userList.forEach(user -> {
			try {
				if (!customerList.contains(getCustomerById(user.getUserID()))) {
					customerList.add(getCustomerById(user.getUserID()));
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		});
		return customerList;
	}
}
