package com.conf.jkupcho.boxr.product;

import javax.persistence.Embeddable;

@Embeddable
public class Classification {

    private final String name;

    public Classification(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
