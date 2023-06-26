package com.poly.page.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.entity.Accounts;
import com.poly.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/assignment/")
public class AccuracyController {
	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/accuracy")
	public String accuracyPage(HttpSession httpSession) {
		if (httpSession.getAttribute("randomNumber") == null) {
			return "redirect:/assignment/registration";
		}
		return "page/users/accuracy";
	}

	@PostMapping("/accuracy")
	public String accuracy(HttpSession httpSession, @RequestParam("verification-code") String code,
			RedirectAttributes attributes) {
		Accounts account = (Accounts) httpSession.getAttribute("accountRegis");
		int value = (int) httpSession.getAttribute("randomNumber");
		if (code.equals(String.valueOf(value))) {
			accountRepository.save(account);
			attributes.addFlashAttribute("message", "Đăng ký thành công");
			httpSession.removeAttribute("accountRegis");
			httpSession.removeAttribute("randomNumber");
			return "redirect:/assignment/login";
		}
		return "page/users/accuracy";
	}
}
