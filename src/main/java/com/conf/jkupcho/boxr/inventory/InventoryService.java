package com.conf.jkupcho.boxr.inventory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class InventoryService {

    private final RestTemplate restTemplate;
    private final String locationServiceUrl;

    public InventoryService(RestTemplate restTemplate,
                            @Value("${location.service.url}") String locationServiceUrl) {
        this.restTemplate = restTemplate;
        this.locationServiceUrl = locationServiceUrl;
    }

    public List<Inventory> findNearbyInventory(Inventory inventory) {
        ResponseEntity<Inventory[]> response = restTemplate.getForEntity(locationServiceUrl + "/inventory/{id}", Inventory[].class, inventory.getId());

        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.asList(response.getBody());
        }

        return Collections.emptyList();
    }

}
