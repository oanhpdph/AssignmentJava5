package com.poly.filter;

import java.io.IOException;

import com.poly.entity.Accounts;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggedFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		Accounts accounts = (Accounts) httpServletRequest.getSession().getAttribute("accountLogged");
		if (accounts != null) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/assignment/logout");
			return;
		}
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

}
