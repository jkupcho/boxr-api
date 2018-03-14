package com.conf.jkupcho.boxr.inventory.web;

import com.conf.jkupcho.boxr.inventory.Inventory;
import com.conf.jkupcho.boxr.inventory.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("inventory/{id}")
@ExposesResourceFor(Inventory.class)
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PutMapping(path = InventoryLinks.PICK)
    ResponseEntity<Void> pickInventory(@PathVariable("id") Inventory inventory, @RequestBody Pick pick) {
        if (!pick.canPick(inventory)) { throw new OutOfStockException(); }

        inventory.pick(pick.numPicked);

        inventoryRepository.save(inventory);

        return ResponseEntity.noContent().build();
    }

    public static class Pick {

        public int numPicked;

        public boolean canPick(Inventory inventory) {
            return inventory.getOnHand() >= numPicked;
        }

    }

}
