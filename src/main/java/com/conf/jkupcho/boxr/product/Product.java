package com.conf.jkupcho.boxr.product;

import com.conf.jkupcho.boxr.core.AbstractIdentifiable;
import com.conf.jkupcho.boxr.inventory.Inventory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Product extends AbstractIdentifiable {

    private String description;

    @Embedded
    private Classification classification;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Inventory> inventories;

    protected Product() {}

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        Product rhs = (Product)obj;

        return new EqualsBuilder()
            .append(description, rhs.description)
            .reflectionAppend(classification, rhs.classification)
        .build();
    }
}
