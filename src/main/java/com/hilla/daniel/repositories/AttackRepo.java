package com.hilla.daniel.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.hilla.daniel.models.Attack;

public interface AttackRepo extends CrudRepository<Attack, Long> {
	ArrayList<Attack> findAll();
}
