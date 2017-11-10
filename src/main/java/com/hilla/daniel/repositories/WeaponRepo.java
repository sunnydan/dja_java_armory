package com.hilla.daniel.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.hilla.daniel.models.Weapon;

public interface WeaponRepo extends CrudRepository<Weapon, Long> {
	ArrayList<Weapon> findAll();
}
