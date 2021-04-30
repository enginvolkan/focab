package com.engin.focab.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.engin.focab.jpa.corpus.LexiModel;

@Entity
@Table(name = "customer")

public class Customer {
	public Customer(String string) {
		super();
		this.username = string;
//		favorites.add(new FavoriteList("default",this));
	}


	public Customer() {
		super();
	}

	@Id
	private String username;
	private String password;
	private String fullname;
	private boolean enabled;
	private int level;

	@OneToMany
	private Set<FavoriteList> favorites= new HashSet<FavoriteList>();

	@OneToMany
	private Set<Authority> authority= new HashSet<Authority>();

	@OneToMany
	private Set<LexiModel> knownWords = new HashSet<LexiModel>();

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Authority> getRoles() {
		return authority;
	}

	public void setRoles(Set<Authority> authority) {
		this.authority = authority;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getFullname() {
		return fullname;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Set<LexiModel> getKnownWords() {
		return knownWords;
	}

	public void setKnownWords(Set<LexiModel> knownWords) {
		this.knownWords = knownWords;
	}

	public Set<Authority> getAuthority() {
		return authority;
	}

}
