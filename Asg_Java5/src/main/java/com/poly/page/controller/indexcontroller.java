package com.poly.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class indexcontroller {

	@GetMapping("/oanh")
	private String page() {
		// TODO Auto-generated method stub
		System.out.println("oanh");
		return "index";
	}
}
