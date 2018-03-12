package com.conf.jkupcho.boxr.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.Identifiable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractIdentifiable implements Identifiable<Long> {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    // This is for JPA
    protected AbstractIdentifiable() {
        this.id = null;
    }

    @Override
    public Long getId() {
        return id;
    }

}
