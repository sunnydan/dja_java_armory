package com.hilla.daniel.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hilla.daniel.models.User;
import com.hilla.daniel.repositories.UserRepo;
import com.hilla.daniel.validators.UserValidator;

@Controller
public class Users {

	private UserRepo userrepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserValidator userValidator;

	public Users(UserRepo userrepo, BCryptPasswordEncoder bCryptPasswordEncoder, UserValidator userValidator) {
		this.userrepo = userrepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userValidator = userValidator;
	}

	@RequestMapping("/users/dashboard")
	public String weapon(Model model, Principal principal) {
		model.addAttribute("currentUser", userrepo.findByUsername(principal.getName()));
		return "userDashboard";
	}

	@RequestMapping(value = { "/", "/home" })
	public String home(Principal principal, Model model) {
		// 1
		model.addAttribute("currentUser", userrepo.findByUsername(principal.getName()));
		return "redirect:/weapons";
	}

	@RequestMapping("/registration")
	public String registerForm(@Valid @ModelAttribute("user") User user) {
		return "registrationPage";
	}

	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		// NEW
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "registrationPage.jsp";
		}
		List<User> superadmins = userrepo.findByPermissionLevel(3);
		if (superadmins.size() == 0) {
			userrepo.saveUser(user, bCryptPasswordEncoder, 3);
		} else {
			userrepo.saveUser(user, bCryptPasswordEncoder, 1);
		}
		return "redirect:/login";
	}

	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		return "loginPage";
	}

	@RequestMapping("/admin")
	public String adminPage(Principal principal, Model model) {
		String username = principal.getName();
		model.addAttribute("currentUser", userrepo.findByUsername(username));
		return "adminPage";
	}

}
