package com.engin.focab.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String text;
 
    // standard constructors
 
    // standard getters and setters
}
