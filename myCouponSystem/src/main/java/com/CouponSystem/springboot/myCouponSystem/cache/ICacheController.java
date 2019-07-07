package com.CouponSystem.springboot.myCouponSystem.cache;

public interface ICacheController {

	public void put(String key, Object data);

	public Object get(String key);

	public void remove(String key);
}
