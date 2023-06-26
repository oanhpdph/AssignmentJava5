package com.poly.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.entity.Accounts;
import com.poly.entity.validation.UpdateValidation;
import com.poly.repository.AccountRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/")
public class UsersController {
	@Autowired
	private AccountRepository accountRepository;

	@GetMapping(value = { "usermanager" })
	private String userPage(Model model, HttpSession httpSession,
			@RequestParam(name = "pageSize", defaultValue = "6") int pageSize,
			@RequestParam(name = "pageNum", defaultValue = "1", required = false) int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		Page<Accounts> page = accountRepository.findAll(pageable);

		httpSession.setAttribute("listUser", page.getContent());
		httpSession.setAttribute("totalPage", page.getTotalPages());
		httpSession.setAttribute("view", "admin/users/user_manager.html");
		httpSession.setAttribute("active", "user");
		model.addAttribute("user", new Accounts());
		return "admin/layout";
	}

	@PostMapping(value = "usermanager")
	private String updateUser(Model model, @Validated(UpdateValidation.class) @ModelAttribute("user") Accounts accounts,
			BindingResult bindingResult, HttpServletRequest request, RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			return "admin/layout";
		}

		Accounts user = accountRepository.findById(accounts.getId()).orElse(null);
		if (user != null) {
			user.setFullname(accounts.getFullname());
			user.setUsername(accounts.getUsername());
			user.setActivated(accounts.isActivated());
			accountRepository.save(user);
			model.addAttribute("user", new Accounts());
			attributes.addFlashAttribute("message", "Cập nhật thành công");
			return "redirect:/admin/usermanager";
		}
		attributes.addFlashAttribute("message", "Không thể cập nhật do chưa có tài khoản");
		return "redirect:/admin/usermanager";
	}

	@GetMapping(value = "/edit")
	private String selectUser(Model model, @RequestParam("id") Integer id) {
		Accounts accounts = accountRepository.findById(id).orElse(null);
		model.addAttribute("user", accounts);
		return "admin/layout";
	}

	@GetMapping(value = "/delete")
	private String deleteUser(Model model, @RequestParam(name = "id", defaultValue = "-1", required = false) String id,
			RedirectAttributes attributes) {

		if ("null".equals(id)) {
			attributes.addFlashAttribute("message", "Chưa chọn tài khoản!");
			return "redirect:/admin/usermanager";
		}
		accountRepository.deleteById(Integer.parseInt(id));
		attributes.addFlashAttribute("message", "Xóa thành công!");
		return "redirect:/admin/usermanager";
	}

}
