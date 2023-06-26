package com.poly.filter;

import java.io.IOException;
import java.io.PrintWriter;

import com.poly.entity.Accounts;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class AuthFilter implements Filter {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Accounts accounts = (Accounts) httpServletRequest.getSession().getAttribute("accountLogged");
		if (accounts.isAdmin() == false) {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.print("Bạn không có quyền truy cập vào trang này");
			return;
		}
		
		chain.doFilter(request, response);
	}

}
