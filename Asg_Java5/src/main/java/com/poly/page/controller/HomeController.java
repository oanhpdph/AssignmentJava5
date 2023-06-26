package com.poly.page.controller;

import com.poly.entity.Accounts;
import com.poly.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.entity.Products;
import com.poly.repository.ProductsRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/custommer/")
public class HomeController {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductsRepository productsRepository;

	@GetMapping(value = "/home")
	private String homePage(Model model, HttpSession httpSession,
			@RequestParam(name = "pageSize", defaultValue = "9") int pageSize,
			@RequestParam(name = "pageNum", defaultValue = "1", required = false) int pageNumber,
			HttpServletRequest request) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<Products> page = productsRepository.findAll(pageable);
		Accounts accounts = (Accounts) httpSession.getAttribute("accountLogged");
		if (cartRepository.findByAccountId(accounts) != null) {
			httpSession.setAttribute("listCart", cartRepository.findByAccountId(accounts).getCartId());
		}
		model.addAttribute("listAllProduct", page.getContent());
		model.addAttribute("totalPage", page.getTotalPages());
		httpSession.setAttribute("view", "page/home/home.html");
		httpSession.setAttribute("title", "Trang chủ");
		return "page/layout";
	}
}
