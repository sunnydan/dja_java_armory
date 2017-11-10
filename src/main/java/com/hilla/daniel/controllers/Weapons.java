package com.hilla.daniel.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hilla.daniel.models.Weapon;
import com.hilla.daniel.repositories.AttackRepo;
import com.hilla.daniel.repositories.CategoryRepo;
import com.hilla.daniel.repositories.WeaponRepo;

@Controller
public class Weapons {

	private final WeaponRepo weaponrepo;
	private final AttackRepo attackrepo;
	private final CategoryRepo categoryrepo;

	public Weapons(WeaponRepo weaponrepo, AttackRepo attackrepo, CategoryRepo categoryrepo) {
		this.weaponrepo = weaponrepo;
		this.attackrepo = attackrepo;
		this.categoryrepo = categoryrepo;
	}

	@RequestMapping("")
	public String index() {
		return "redirect:/weapons";
	}

	@RequestMapping("/weapons")
	public String weapons(Model model) {
		model.addAttribute("weapons", weaponrepo.findAll());
		return "weapons";
	}

	@RequestMapping("/weapons/new")
	public String newWeapon(Model model, @ModelAttribute("weapon") Weapon weapon) {
		model.addAttribute("categories", categoryrepo.findAll());
		System.out.println(categoryrepo.findAll());
		return "newWeapon";
	}

	@PostMapping("/weapons/new")
	public String createWeapon(@Valid @ModelAttribute("weapon") Weapon weapon, BindingResult result) {
		if (result.hasErrors()) {
			return "newWeapon";
		} else {
			this.weaponrepo.save(weapon);
			return "redirect:/weapons";
		}
	}

	@RequestMapping("/weapons/{id}")
	public String weapon(@PathVariable("id") Long id, Model model) {
		Weapon weapon = weaponrepo.findOne(id);
		if (weapon != null) {
			model.addAttribute("weapon", weapon);
			model.addAttribute("attacks", attackrepo.findAll());
			return "showWeapon";
		} else {
			return "redirect:/weapons";
		}
	}

	@RequestMapping(value = "/weapons/{id}", method = RequestMethod.POST)
	public String updateWeapon(@PathVariable("id") Long id, @RequestParam("attack") Long attackid) {
		Weapon weapon = weaponrepo.findOne(id);
		if (weapon != null) {
			weapon.addAttack(attackrepo.findOne(attackid));
			weaponrepo.save(weapon);
			return "redirect:/weapons/" + id.toString();
		} else {
			return "redirect:/weapons";
		}
	}

	@RequestMapping("/weapons/delete/{id}")
	public String destroyWeapon(@PathVariable("id") long id) {
		weaponrepo.delete(id);
		return "redirect:/weapons";
	}
}
