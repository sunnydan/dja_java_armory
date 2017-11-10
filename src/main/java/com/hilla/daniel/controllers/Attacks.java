package com.hilla.daniel.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hilla.daniel.models.Attack;
import com.hilla.daniel.repositories.AttackRepo;

@Controller
public class Attacks {

	private final AttackRepo attackrepo;

	public Attacks(AttackRepo attackrepo) {
		this.attackrepo = attackrepo;
	}
	
	@RequestMapping("/attacks/new")
	public String newAttack(@ModelAttribute("attack") Attack attack) {
		return "newAttack";
	}

	@PostMapping("/attacks/new")
	public String createAttack(@Valid @ModelAttribute("attack") Attack attack, BindingResult result) {
		if (result.hasErrors()) {
			return "newAttack";
		} else {
			this.attackrepo.save(attack);
			return "redirect:/weapons";
		}
	}
}
