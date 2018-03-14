package com.conf.jkupcho.boxr.inventory;

import com.conf.jkupcho.boxr.core.AbstractIdentifiable;
import com.conf.jkupcho.boxr.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Inventory extends AbstractIdentifiable {

    public static final Integer DEFAULT_LOW_THRESHOLD = 100;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer onHand;
    private Integer onOrder;
    private Integer lowThreshold;

    protected Inventory() {
        this.lowThreshold = DEFAULT_LOW_THRESHOLD;
    }

    public Inventory(Integer onHand, Integer onOrder) {
        this(onHand, onOrder, DEFAULT_LOW_THRESHOLD);
    }

    public Inventory(Integer onHand, Integer onOrder, Integer lowThreshold) {
        this.onHand = onHand;
        this.onOrder = onOrder;
        this.lowThreshold = lowThreshold;
    }

    public void pick(int numPicked) {
        onHand -= numPicked;
    }

    public boolean isOutOfStock() {
        return onHand <= 0;
    }

    public boolean isLowThresholdMet() {
        return onHand <= getLowThreshold();
    }

    public boolean canOrder() {
        return isOutOfStock() && onOrder <= 0;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getOnHand() {
        return onHand;
    }

    public Integer getOnOrder() {
        return onOrder;
    }

    public Integer getLowThreshold() {
        return lowThreshold == null ? DEFAULT_LOW_THRESHOLD : lowThreshold;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        Inventory rhs = (Inventory)obj;

        return new EqualsBuilder()
            .append(onHand, rhs.onHand)
            .append(onOrder, rhs.onOrder)
            .append(lowThreshold, rhs.lowThreshold)
            .build();
    }
}
