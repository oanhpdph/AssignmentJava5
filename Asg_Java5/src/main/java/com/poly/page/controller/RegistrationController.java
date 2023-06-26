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

import com.poly.common.RandomNumber;
import com.poly.common.SendEmail;
import com.poly.entity.Accounts;
import com.poly.entity.validation.RegistrationValidation;
import com.poly.repository.AccountRepository;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/assignment/")
public class RegistrationController {

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/registration")
	public String registrationPage(Model model) {
		model.addAttribute("registration", new Accounts());
		return "page/users/registration";
	}

	@PostMapping("/registration")
	public String registration(Model model,
			@Validated(RegistrationValidation.class) @ModelAttribute("registration") Accounts account,
			BindingResult bindingResult, RedirectAttributes attributes, HttpSession httpSession)
			throws MessagingException {
		// validate
		if (bindingResult.hasErrors()) {
			return "page/users/registration";
		}
		// suscess
		if (account != null) {
			Accounts find = accountRepository.findByEmail(account.getEmail());
			if (find != null) {
				attributes.addFlashAttribute("message", "Email đã được đăng ký");
				return "redirect:/assignment/registration";
			}
			RandomNumber rand = new RandomNumber();
			int value = rand.randomNumber();
			httpSession.setAttribute("accountRegis", account);
			httpSession.setAttribute("randomNumber", value);
			sendEmail.sendEmail(account.getEmail(), "Email xác thực tài khoản",
					"Mã xác thực tài khoản của bạn là: " + value);
			return "redirect:/assignment/accuracy";
		}
		return "page/users/registration";
	}
}
