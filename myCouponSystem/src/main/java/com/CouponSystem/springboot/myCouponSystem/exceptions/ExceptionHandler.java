package com.CouponSystem.springboot.myCouponSystem.exceptions;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CouponSystem.springboot.myCouponSystem.beans.ErrorBean;
import com.CouponSystem.springboot.myCouponSystem.enums.ErrorType;

@SuppressWarnings("serial")
@ControllerAdvice
public class ExceptionHandler extends Exception {

	// Handles ApplicationExceptions

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public @ResponseBody ErrorBean handleConflict(HttpServletResponse response, Exception e) {
		response.setStatus(600);
		String errorMessage;
		if (e instanceof ApplicationException) {
			ApplicationException e2 = (ApplicationException) e;
			errorMessage = e2.getMessage();
			if (e2.getErrorType().isPrintStackTrace()) {
				e.printStackTrace();
			}
			ErrorBean errorBean = new ErrorBean(errorMessage, e2.getErrorType().getInternalErrorCode());
			return errorBean;
		}
		e.printStackTrace();
		ErrorBean errorBean = new ErrorBean(ErrorType.GENERAL_ERROR.getExternalMessage(),
				ErrorType.GENERAL_ERROR.getInternalErrorCode());
		return errorBean;
	}
}
