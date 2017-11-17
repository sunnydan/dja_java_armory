package com.hilla.daniel.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "weapons")
public class Weapon {

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "attacks_weapons", joinColumns = @JoinColumn(name = "weapon_id"), inverseJoinColumns = @JoinColumn(name = "attack_id"))
	private List<Attack> attacks;

	public Weapon() {

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Attack> getAttacks() {
		return attacks;
	}

	public void setAttacks(ArrayList<Attack> attacks) {
		this.attacks = attacks;
	}

	public void addAttack(Attack attack) {
		this.attacks.add(attack);
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
	
	public User getAuthor() {
		return user;
	}

	public void setAuthor(User user) {
		this.user = user;
	}

}
