package com.conf.jkupcho.boxr.product;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.Embeddable;

@Embeddable
public class Classification {

    private String name;

    protected Classification() {}

    public Classification(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        Classification rhs = (Classification) obj;

        return new EqualsBuilder()
            .append(name, rhs.name)
        .build();
    }
}
