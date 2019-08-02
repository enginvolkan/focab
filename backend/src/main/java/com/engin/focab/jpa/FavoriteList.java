package com.engin.focab.jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class FavoriteList implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
    private Integer	FavoriteId;
    private String name;
    @ManyToOne
    private Customer customer;

    @ElementCollection  
    private Set<FavoriteEntry> favoriteEntries = new HashSet<FavoriteEntry>();

	public FavoriteList(String name, Customer customer) {
		super();
		this.name = name;
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<FavoriteEntry> getFavoriteEntries() {
		return favoriteEntries;
	}

	public void setFavoriteEntries(Set<FavoriteEntry> favoriteEntries) {
		this.favoriteEntries = favoriteEntries;
	}
	


}
