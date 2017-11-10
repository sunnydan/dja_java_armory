package com.hilla.daniel.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hilla.daniel.models.Category;
import com.hilla.daniel.repositories.CategoryRepo;

@Controller
public class Categories {

	private final CategoryRepo categoryrepo;

	public Categories(CategoryRepo categoryrepo) {
		this.categoryrepo = categoryrepo;
	}

	@RequestMapping("/categories/new")
	public String newCategory(@ModelAttribute("category") Category category) {
		return "newCategory";
	}

	@PostMapping("/categories/new")
	public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result) {
		if (result.hasErrors()) {
			return "newCategory";
		} else {
			this.categoryrepo.save(category);
			return "redirect:/weapons";
		}
	}
}
