package com.CouponSystem.springboot.myCouponSystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.CouponSystem.springboot.myCouponSystem.entities.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

	public Image saveAndFlush(Image dbImage);

}
