package com.CouponSystem.springboot.myCouponSystem.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CouponSystem.springboot.myCouponSystem.beans.CacheData;
import com.CouponSystem.springboot.myCouponSystem.beans.SuccessfulLoginData;
import com.CouponSystem.springboot.myCouponSystem.cache.ICacheController;
import com.CouponSystem.springboot.myCouponSystem.entities.Company;
import com.CouponSystem.springboot.myCouponSystem.entities.UserLoginDetails;
import com.CouponSystem.springboot.myCouponSystem.enums.ErrorType;
import com.CouponSystem.springboot.myCouponSystem.enums.RepoType;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.repositories.CompanyRepository;
import com.CouponSystem.springboot.myCouponSystem.repositories.UserRepository;
import com.CouponSystem.springboot.myCouponSystem.utils.JpaUtils;

@Service
public class UserService {

	// Attribute

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CompanyRepository companyRepo;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private JpaUtils jpaUtils;

	@Autowired
	private ICacheController cacheController;

	// Constructor

	/**
	 * Public constructor. Constructs a new {@code UserService} object.
	 */
	public UserService() {

	}

	// Methods - Login / Logout

	public SuccessfulLoginData login(String userName, String userPassword) throws ApplicationException {

		UserLoginDetails user = this.userRepo.findByUsername(userName);

		if (this.userRepo == null) {
			throw new ApplicationException(ErrorType.ACCOUNT_DOES_NOT_EXIST,
					ErrorType.ACCOUNT_DOES_NOT_EXIST.getExternalMessage());

		}

		if (!userPassword.equals(user.getUserPassword())) {
			throw new ApplicationException(ErrorType.INCORRECT_PASSWORD,
					ErrorType.INCORRECT_PASSWORD.getExternalMessage());
		}

		int token = generateToken(userName, userPassword);
		String strToken = String.valueOf(token);

		Long companyID;
		Company company = user.getCompany();
		if (company == null) {
			companyID = null;
		} else {
			companyID = company.getCompanyID();
		}

		CacheData cacheData = new CacheData(strToken, companyID, user.getUserID(), user.getUserClientType());

		this.cacheController.put(strToken, cacheData);

		return new SuccessfulLoginData(user.getUsername(), strToken, user.getUserClientType());
	}

	public void logout(String key) throws Throwable {
		this.cacheController.remove(key);
	}

	// Methods

	@Transactional
	public void createUser(UserLoginDetails user) throws ApplicationException {
		if (this.userRepo.findByUsername(user.getUsername()) != null) {
			throw new ApplicationException(ErrorType.ACCOUNT_ALREADY_EXIST,
					ErrorType.ACCOUNT_ALREADY_EXIST.getExternalMessage());
		} else {
			try {
				this.userRepo.save(user);
				System.out.println("User was created successfuly");
			} catch (Exception e) {
				throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getExternalMessage());
			}
		}
	}

	@Transactional
	public void createEmployee(UserLoginDetails user, long companyID) throws ApplicationException {
		user.setCompany(this.companyService.getCompanyById(companyID));
		if (this.userRepo.findByUsername(user.getUsername()) != null) {
			throw new ApplicationException(ErrorType.ACCOUNT_ALREADY_EXIST,
					ErrorType.ACCOUNT_ALREADY_EXIST.getExternalMessage());
		} else {
			try {
				this.userRepo.save(user);
				System.out.println("User-Employee was created successfuly");
			} catch (Exception e) {
				throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getExternalMessage());
			}
		}
	}

	@Transactional
	public void removeUser(long userID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.userRepo, userID, RepoType.USER);
		this.userRepo.deleteById(userID);
		System.out.println("Coupon was removed successfuly");
	}

	@Transactional
	public void updateUser(UserLoginDetails user) throws ApplicationException {
		UserLoginDetails updatedUser = getUserByName(user.getUsername());
		if (updatedUser == null) {
			throw new ApplicationException(ErrorType.USER_DOES_NOT_EXIST,
					ErrorType.USER_DOES_NOT_EXIST.getExternalMessage());
		} else {
			if (user.getUserPassword() != updatedUser.getUserPassword() && user.getUserPassword() != null) {
				updatedUser.setUserPassword(user.getUserPassword());
			}
			this.userRepo.save(updatedUser);
			System.out.println("User was updated successfuly");
		}
	}

	public UserLoginDetails getUserById(long userID) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.userRepo, userID, RepoType.USER);
		return this.userRepo.findById(userID).get();
	}

	public UserLoginDetails getUserByName(String userName) throws ApplicationException {
		UserLoginDetails user = this.userRepo.findByUsername(userName);
		return user;
	}

	public List<UserLoginDetails> getAllUsers() throws ApplicationException {
		List<UserLoginDetails> userList = new ArrayList<>();
		this.userRepo.findAll().forEach(user -> userList.add(user));
		return userList;
	}

	@Transactional
	public List<UserLoginDetails> getAllCompanyEmployees(long companyID) throws ApplicationException {
		List<UserLoginDetails> companyEmployeeList = new ArrayList<>();
		this.userRepo.findByCompany(this.companyService.getCompanyById(companyID))
				.forEach(user -> companyEmployeeList.add(user));
		return companyEmployeeList;
	}

	@Transactional
	public UserLoginDetails getCompanyEmployee(long companyID, String employeeName) throws ApplicationException {
		this.jpaUtils.validateResourceIsPresent(this.companyRepo, companyID, RepoType.COMPANY);
		UserLoginDetails user = this.userRepo.getCompanyEmployee(companyID, employeeName);
		return user;
	}

	private int generateToken(String userName, String userPassword) {

		String salt = "dsfsdfsdfs";
		String token = userName + salt + userPassword;
		return token.hashCode();
	}

}
