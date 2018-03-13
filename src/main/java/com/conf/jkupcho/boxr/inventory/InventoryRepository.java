package com.conf.jkupcho.boxr.inventory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "inventory")
public interface InventoryRepository extends CrudRepository<Inventory, Long> {
}
