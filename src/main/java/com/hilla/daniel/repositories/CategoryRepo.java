package com.hilla.daniel.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.hilla.daniel.models.Category;

public interface CategoryRepo extends CrudRepository<Category, Long> {
	ArrayList<Category> findAll();
}
