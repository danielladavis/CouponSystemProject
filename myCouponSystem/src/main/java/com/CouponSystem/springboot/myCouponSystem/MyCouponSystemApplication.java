package com.CouponSystem.springboot.myCouponSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.CouponSystem.springboot.myCouponSystem.filters.CORSFilter;

@SpringBootApplication
@EnableScheduling
public class MyCouponSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCouponSystemApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<CORSFilter> createCrossOriginFilter() {
		FilterRegistrationBean<CORSFilter> filter = new FilterRegistrationBean<CORSFilter>();
		filter.setFilter(new CORSFilter());
		filter.addUrlPatterns("/customers");

		return filter;
	}

}
