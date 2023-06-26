package com.poly.page.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.poly.entity.Accounts;
import com.poly.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.entity.Products;
import com.poly.repository.ProductsRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/custommer")
public class DetailProductController {

	@Autowired
	private ProductsRepository productsRepository;

	@GetMapping("/product/detail")
	private String detailPage(HttpSession session) {
		// TODO Auto-generated method stub

		session.setAttribute("title", "Detail Product");
		session.setAttribute("view", "page/product/detail_product");
		return "page/layout";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/{id}/product/detail")
	private String detailProduct(@PathVariable("id") int id, HttpSession session) {
		// TODO Auto-generated method stub
		Products products = productsRepository.findById(id).orElse(null);
		List<Products> listRecently = new ArrayList<>();
		if (session.getAttribute("productRecently") != null) {
			listRecently = new ArrayList<>((List<Products>) session.getAttribute("productRecently"));
		}

		listRecently.add(products);
		for (Products products2 : listRecently) {
			System.out.println(products2);
		}
		session.setAttribute("productDetail", products);
		session.setAttribute("productRecently", listRecently.stream().distinct().collect(Collectors.toList()));
		return "redirect:/custommer/product/detail";
	}
}
