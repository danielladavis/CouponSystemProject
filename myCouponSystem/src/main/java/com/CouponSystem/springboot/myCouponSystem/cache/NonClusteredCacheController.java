package com.CouponSystem.springboot.myCouponSystem.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class NonClusteredCacheController implements ICacheController {

	private Map<String, Object> cache;

	public NonClusteredCacheController() {
		this.cache = new HashMap<>();
	}

	@Override
	public void put(String key, Object data) {
		this.cache.put(key, data);

	}

	@Override
	public Object get(String key) {
		return this.cache.get(key);
	}

	@Override
	public void remove(String key) {
		this.cache.remove(key);
	}

}
