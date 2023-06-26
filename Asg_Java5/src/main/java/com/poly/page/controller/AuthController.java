package com.poly.page.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.entity.Accounts;
import com.poly.entity.validation.LoginValidation;
import com.poly.repository.AccountRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/assignment/")
public class AuthController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping(value = "/login")
	public String pageLogin(Model model) {
		model.addAttribute("account", new Accounts());
		return "page/users/login";
	}

	@PostMapping("/login")
	private String submitLogin(Model model,
			@Validated(LoginValidation.class) @ModelAttribute("account") Accounts accounts, BindingResult result,
			HttpSession httpSession, RedirectAttributes redirectAttributes) {

		// validate
		if (result.hasErrors()) {
			return "page/users/login";
		}
		// login suscess
		Accounts account = accountRepository.findByEmailAndPassword(accounts.getEmail(), accounts.getPassword());
		if (account == null) {
			model.addAttribute("message", "Nhập sai thông tin");
			return "page/users/login";
		}

		if (account != null && account.isActivated() == true) {
			account.setPassword("");
			httpSession.setAttribute("accountLogged", account);
			if (account == null || account.isAdmin() == false) {
				return "redirect:/custommer/home";
			}
			return "redirect:/admin/usermanager";
		}

		// login failed
		if (account.isActivated() == false) {
			model.addAttribute("message", "Tài khoản của bạn bị khóa");
		}
		return "page/users/login";
	}

}
