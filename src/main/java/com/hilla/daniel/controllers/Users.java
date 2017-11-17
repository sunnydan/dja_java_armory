package com.hilla.daniel.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	private AuthenticationManager authenticationManager;

	public Users(UserRepo userrepo, BCryptPasswordEncoder bCryptPasswordEncoder, UserValidator userValidator,
			AuthenticationManager authenticationManager) {
		this.userrepo = userrepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userValidator = userValidator;
		this.authenticationManager = authenticationManager;
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
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result,
			HttpServletRequest request) {
		// NEW
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "registrationPage";
		}
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		List<User> superadmins = userrepo.findByPermissionLevel(3);
		if (superadmins.size() == 0) {
			user.setPermissionLevel(3);
			userrepo.save(user);
		} else {
			user.setPermissionLevel(1);
			userrepo.save(user);
		}
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
				password);
		request.getSession();
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());
		request.getSession().setAttribute("username", user.getUsername());
		request.getSession().setAttribute("authorities", token.getAuthorities());
		return "redirect:/weapons";
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
	public String admin() {
		return "redirect:/admin/users/page/1";
	}

	@RequestMapping("/admin/users/page/{pageNumber}")
	public String adminPage(Model model, @PathVariable("pageNumber") int pageNumber, Principal principal) {
		String username = principal.getName();
		model.addAttribute("currentUser", userrepo.findByUsername(username));
		Page<User> users = userrepo.usersPerPage(pageNumber - 1);
		int totalPages = users.getTotalPages();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("users", users);
		return "users";
	}

	@RequestMapping("/admin/users/delete/{userid}")
	public String deleteUser(Model model, @PathVariable("userid") long userid, Principal principal) {
		User user = userrepo.findOne(userid);
		userrepo.delete(user);
		return "redirect:/admin/users/page/1";
	}

	@RequestMapping("/superadmin/users/promote/{userid}")
	public String promoteUser(@PathVariable("userid") long userid) {
		User user = userrepo.findOne(userid);
		System.out.println("Promoting " + user.getUsername());
		user.setPermissionLevel(2);
		userrepo.save(user);
		return "redirect:/admin/users/page/1";
	}

	@RequestMapping("/superadmin/users/demote/{userid}")
	public String demoteUser(@PathVariable("userid") long userid) {
		User user = userrepo.findOne(userid);
		System.out.println("Demoting " + user.getUsername());
		user.setPermissionLevel(1);
		userrepo.save(user);
		return "redirect:/admin/users/page/1";
	}

}
