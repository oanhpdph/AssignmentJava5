package com.poly.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFilter {

	@Bean
	public FilterRegistrationBean<LoginFilter> loggingFilter() {
		FilterRegistrationBean<LoginFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new LoginFilter());
		filter.addUrlPatterns("/admin/*", "/custommer/cart", "/custommer/order");
		filter.setOrder(1);
		return filter;
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> adminFilter() {
		FilterRegistrationBean<AuthFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new AuthFilter());
		filter.addUrlPatterns("/admin/*");
		filter.setOrder(2);
		return filter;
	}

	@Bean
	public FilterRegistrationBean<LoggedFilter> loggedFilter() {
		FilterRegistrationBean<LoggedFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new LoggedFilter());
		filter.addUrlPatterns("/assignment/login");
		filter.setOrder(3);
		return filter;
	}

	@Bean
	public FilterRegistrationBean<OrderOrCartFilter> orderOrCartFilter() {
		FilterRegistrationBean<OrderOrCartFilter> filter = new FilterRegistrationBean<>();
		filter.setFilter(new OrderOrCartFilter());
		filter.addUrlPatterns("/custommer/product/OrderOrCart");
		filter.setOrder(4);
		return filter;
	}

//	@Bean
//	public FilterRegistrationBean<ProductFilter> ProductFilter() {
//		FilterRegistrationBean<ProductFilter> filter = new FilterRegistrationBean<>();
//		filter.setFilter(new ProductFilter());
//		filter.addUrlPatterns("/custommer/product/detail");
//		filter.setOrder(4);
//		return filter;
//	}
}
