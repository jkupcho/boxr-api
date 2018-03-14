package com.conf.jkupcho.boxr.inventory.web;

import com.conf.jkupcho.boxr.inventory.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class InventoryLinks {

    static final String PICK = "/pick";
    static final String PICK_REL = "pick";

    private final EntityLinks entityLinks;

    @Autowired
    public InventoryLinks(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    Link getPickLink(Inventory inventory) {
        return entityLinks.linkForSingleResource(inventory).slash(PICK).withRel(PICK_REL);
    }

}
