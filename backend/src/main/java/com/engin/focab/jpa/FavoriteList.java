package com.engin.focab.jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

@Entity
public class FavoriteList implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer	favoriteId;
    private String name;
    @ManyToOne
    private Customer customer;

    @ElementCollection
	@CollectionTable(name = "favoriteentries", joinColumns = @JoinColumn(name = "favorite_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "favorite_id", "lexi_model_id" }) })
    private Set<FavoriteEntry> favoriteEntries = new HashSet<FavoriteEntry>();


	public FavoriteList() {
		super();
	}

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

	public Integer getFavoriteId() {
		return favoriteId;
	}



}
