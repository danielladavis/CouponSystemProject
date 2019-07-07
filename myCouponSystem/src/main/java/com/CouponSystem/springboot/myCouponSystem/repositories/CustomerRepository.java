package com.CouponSystem.springboot.myCouponSystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystem.springboot.myCouponSystem.entities.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	public Customer findByCustomerName(String customerName);

}
