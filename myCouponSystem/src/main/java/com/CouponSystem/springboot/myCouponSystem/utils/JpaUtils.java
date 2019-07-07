package com.CouponSystem.springboot.myCouponSystem.utils;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystem.springboot.myCouponSystem.enums.ErrorType;
import com.CouponSystem.springboot.myCouponSystem.enums.RepoType;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;

@Repository
public class JpaUtils {

	public void validateResourceIsPresent(CrudRepository<?, Long> repository, long id, RepoType repoType)
			throws ApplicationException {
		boolean isPresent = repository.findById(id).isPresent();
		if (!isPresent) {
			switch (repoType) {

			case COMPANY:
				throw new ApplicationException(ErrorType.COMPANY_DOES_NOT_EXIST,
						ErrorType.COMPANY_DOES_NOT_EXIST.getExternalMessage());

			case COUPON:
				throw new ApplicationException(ErrorType.COUPON_DOES_NOT_EXIST,
						ErrorType.COUPON_DOES_NOT_EXIST.getExternalMessage());

			case CUSTOMER:
				throw new ApplicationException(ErrorType.CUSTOMER_DOES_NOT_EXIST,
						ErrorType.CUSTOMER_DOES_NOT_EXIST.getExternalMessage());

			case INCOME:
				throw new ApplicationException(ErrorType.INCOME_DOES_NOT_EXIST,
						ErrorType.INCOME_DOES_NOT_EXIST.getExternalMessage());

			case PURCHASE:
				throw new ApplicationException(ErrorType.PURCHASE_DOES_NOT_EXIST,
						ErrorType.PURCHASE_DOES_NOT_EXIST.getExternalMessage());

			case USER:
				throw new ApplicationException(ErrorType.ACCOUNT_DOES_NOT_EXIST,
						ErrorType.ACCOUNT_DOES_NOT_EXIST.getExternalMessage());

			case IMAGE:
				throw new ApplicationException(ErrorType.IMAGE_DOES_NOT_EXIST,
						ErrorType.IMAGE_DOES_NOT_EXIST.getExternalMessage());
			}

		}

	}
}
