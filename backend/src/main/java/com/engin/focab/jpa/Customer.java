package com.engin.focab.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer {
	public Customer(String string) {
		super();
		this.name = string;
		favorites.add(new FavoriteList("default",this));
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String name;
	
	@OneToMany
	private Set<FavoriteList> favorites= new HashSet<FavoriteList>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<FavoriteList> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<FavoriteList> favorites) {
		this.favorites = favorites;
	}

	
	// standard constructors

	// standard getters and setters
}
