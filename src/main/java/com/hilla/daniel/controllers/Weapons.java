package com.hilla.daniel.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
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
import com.hilla.daniel.repositories.UserRepo;
import com.hilla.daniel.repositories.WeaponRepo;

@Controller
public class Weapons {

	private final WeaponRepo weaponrepo;
	private final AttackRepo attackrepo;
	private final CategoryRepo categoryrepo;
	private final UserRepo userrepo;

	public Weapons(WeaponRepo weaponrepo, AttackRepo attackrepo, CategoryRepo categoryrepo, UserRepo userrepo) {
		this.weaponrepo = weaponrepo;
		this.attackrepo = attackrepo;
		this.categoryrepo = categoryrepo;
		this.userrepo = userrepo;
	}

	@RequestMapping("/weapons")
	public String weapons() {
		return "redirect:/weapons/page/1";
	}

	@RequestMapping("/weapons/page/{pageNumber}")
	public String getWeaponsPerPage(Model model, @PathVariable("pageNumber") int pageNumber, Principal principal) {
		model.addAttribute("currentUser", userrepo.findByUsername(principal.getName()));
		// we have to subtract 1 because the Pages iterable is 0 indexed. This is for
		// our links to be able to show from 1...pageMax, instead of 0...pageMax - 1.
		Page<Weapon> weapons = weaponrepo.weaponsPerPage(pageNumber - 1);
		// total number of pages that we have
		int totalPages = weapons.getTotalPages();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("weapons", weapons);
		return "weapons";
	}

	@RequestMapping("/weapons/new")
	public String newWeapon(Model model, @ModelAttribute("weapon") Weapon weapon) {
		model.addAttribute("categories", categoryrepo.findAll());
		return "newWeapon";
	}

	@PostMapping("/weapons/new")
	public String createWeapon(@Valid @ModelAttribute("weapon") Weapon weapon, BindingResult result,
			Principal principal) {
		if (result.hasErrors()) {
			return "newWeapon";
		} else {
			weapon.setAuthor(userrepo.findByUsername(principal.getName()));
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
