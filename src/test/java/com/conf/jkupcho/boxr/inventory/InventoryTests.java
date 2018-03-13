package com.conf.jkupcho.boxr.inventory;

import com.conf.jkupcho.boxr.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InventoryTests {

    private Product product;

    @Before
    public void setup() {
        product = Mockito.mock(Product.class);
    }

    @Test
    public void isOutOfStock_OnHandBelowZero_ShouldReturnTrue() {
        Inventory inventory = new Inventory(-10, 0);

        assertTrue(inventory.isOutOfStock());
    }

    @Test
    public void isOutOfStock_OnHandAboveZero_ShouldReturnFalse() {
        Inventory inventory = new Inventory(10, 0);

        assertFalse(inventory.isOutOfStock());
    }

    @Test
    public void isLowThresholdMet_OnHandLessThanThreshold_ShouldReturnTrue() {
        Inventory inventory = new Inventory(4, 0, 5);

        assertTrue(inventory.isLowThresholdMet());
    }

    @Test
    public void isLowThresholdMet_OnHandGreaterThanThreshold_ShouldReturnFalse() {
        Inventory inventory = new Inventory(6, 0, 5);

        assertFalse(inventory.isLowThresholdMet());
    }

    @Test
    public void canOrder_OutOfStockAndOnOrderZero_ShouldReturnTrue() {
        Inventory inventory = new Inventory(-10, 0);

        assertTrue(inventory.canOrder());
    }

    @Test
    public void canOrder_OutOfStockAndOnOrderZero_ShouldReturnFalse() {
        Inventory inventory = new Inventory(-10, 10);

        assertFalse(inventory.canOrder());
    }

    @Test
    public void canOrder_NotOutOfStock_ShouldReturnFalse() {
        Inventory inventory = new Inventory(10, 0);

        assertFalse(inventory.canOrder());
    }
}
