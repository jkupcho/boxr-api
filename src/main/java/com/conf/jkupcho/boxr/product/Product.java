package com.conf.jkupcho.boxr.product;

import com.conf.jkupcho.boxr.core.AbstractIdentifiable;
import com.conf.jkupcho.boxr.inventory.Inventory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Product extends AbstractIdentifiable {

    private final String description;

    @Embedded
    private final Classification classification;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Inventory> inventories;

    public Product(String description, Classification classification) {
        this.description = description;
        this.classification = classification;
    }

    public String getDescription() {
        return description;
    }

    public Classification getClassification() {
        return classification;
    }
}
