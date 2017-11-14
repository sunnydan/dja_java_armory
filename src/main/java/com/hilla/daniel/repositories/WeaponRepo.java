package com.hilla.daniel.repositories;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hilla.daniel.models.Weapon;

public interface WeaponRepo extends PagingAndSortingRepository<Weapon, Long> {
	ArrayList<Weapon> findAll();

	public default Page<Weapon> weaponsPerPage(int pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber, 5, Sort.Direction.ASC, "name");
		return findAll(pageRequest);
	}
}
