package com.conf.jkupcho.boxr.inventory.web;

import com.conf.jkupcho.boxr.inventory.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class InventoryResourceProcessor implements ResourceProcessor<Resource<Inventory>> {

    private final InventoryLinks inventoryLinks;

    @Autowired
    public InventoryResourceProcessor(InventoryLinks inventoryLinks) {
        this.inventoryLinks = inventoryLinks;
    }

    @Override
    public Resource<Inventory> process(Resource<Inventory> resource) {
        Inventory inventory = resource.getContent();

        if (!inventory.isOutOfStock()) {
            resource.add(inventoryLinks.getPickLink(inventory));
        }

        return resource;
    }
}
