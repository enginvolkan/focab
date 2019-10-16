package com.engin.focab.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="customer")

public class Customer {
	public Customer(String string) {
		super();
		this.fullname = string;
//		favorites.add(new FavoriteList("default",this));
	}
	

	public Customer() {
		super();
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String fullname;
	@NotNull
	private String email;
	private String password;
	private boolean enabled;

	@OneToMany
	private Set<FavoriteList> favorites= new HashSet<FavoriteList>();

	@OneToMany
	private Set<Role> roles= new HashSet<Role>();
	
	
	public String getName() {
		return fullname;
	}

	public void setName(String name) {
		this.fullname = name;
	}

	public Set<FavoriteList> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<FavoriteList> favorites) {
		this.favorites = favorites;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public Long getId() {
		return id;
	}


	public String getFullname() {
		return fullname;
	}

}
