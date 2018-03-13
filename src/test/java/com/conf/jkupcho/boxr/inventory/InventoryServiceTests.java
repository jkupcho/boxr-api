package com.conf.jkupcho.boxr.inventory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(
    properties = {
        "location.service.url=http://mock-location-service"
    }
)
public class InventoryServiceTests {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private InventoryService inventoryService;

    @Test
    public void findNearbyInventory_200Success_ShouldReturnKnownList() {
        Inventory[] inventoryArr = new Inventory[]{ new Inventory(10, 20), new Inventory(30, 0) };

        ResponseEntity<Inventory[]> response = ResponseEntity.ok(inventoryArr);

        Inventory concreteInventory = new Inventory(0, 0);
        Inventory spyInventory = Mockito.spy(concreteInventory);

        Mockito.when(spyInventory.getId()).thenReturn(123L);
        Mockito.when(restTemplate.getForEntity("http://mock-location-service/inventory/{id}", Inventory[].class, 123L)).thenReturn(response);

        List<Inventory> inventoryList = inventoryService.findNearbyInventory(spyInventory);

        // Verify Mocks are called.
        Mockito.verify(spyInventory).getId();
        Mockito.verify(restTemplate).getForEntity("http://mock-location-service/inventory/{id}", Inventory[].class, 123L);

        assertEquals(2, inventoryList.size());
    }

    @Test
    public void findNearbyInventory_400BadRequest_ShouldReturnEmptyList() {
        ResponseEntity response = ResponseEntity.badRequest().build();

        Inventory concreteInventory = new Inventory(0, 0);
        Inventory spyInventory = Mockito.spy(concreteInventory);

        Mockito.when(spyInventory.getId()).thenReturn(321L);
        Mockito.when(restTemplate.getForEntity("http://mock-location-service/inventory/{id}", Inventory[].class, 321L)).thenReturn(response);

        List<Inventory> inventoryList = inventoryService.findNearbyInventory(spyInventory);

        // Verify Mocks are called.
        Mockito.verify(spyInventory).getId();
        Mockito.verify(restTemplate).getForEntity("http://mock-location-service/inventory/{id}", Inventory[].class, 321L);

        assertEquals(0, inventoryList.size());
    }

}
