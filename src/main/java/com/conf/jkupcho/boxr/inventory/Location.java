package com.conf.jkupcho.boxr.inventory;

import com.conf.jkupcho.boxr.core.AbstractIdentifiable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Location extends AbstractIdentifiable {

    private final String name;

    @OneToMany(mappedBy = "location")
    private List<Inventory> inventoryItems;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
