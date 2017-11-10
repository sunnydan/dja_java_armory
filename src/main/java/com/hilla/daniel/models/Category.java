package com.hilla.daniel.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	@Size(min = 3, max = 20)
	private String name;

	@Column
	private String description;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
	private Date createdAt;

	@Column
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
	private Date updatedAt;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Weapon> weapons;

	public Category() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Weapon> getWeapon() {
		return (ArrayList<Weapon>) weapons;
	}

	public void setWeapon(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public Long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}
}
