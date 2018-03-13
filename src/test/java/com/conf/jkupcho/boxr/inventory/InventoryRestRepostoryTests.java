package com.conf.jkupcho.boxr.inventory;

import com.conf.jkupcho.boxr.product.Classification;
import com.conf.jkupcho.boxr.product.Product;
import com.conf.jkupcho.boxr.product.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
// Note that since this is testing Spring Data Rest Repositories, entire
// ApplicationContext needs to be loaded, then you can auto configure
// MockMvc.
@AutoConfigureMockMvc
public class InventoryRestRepostoryTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        inventoryRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void post_NoLowThreshold_ShouldDefaultAnd201() throws Exception {
        String jsonRequest =
        "{" +
            "\"onHand\": 10," +
            "\"onOrder\": 20" +
        "}";

        String createdLocation = mockMvc.perform(post("/inventory")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(jsonRequest))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getHeader("Location");

        // Browse to location from header.
        String jsonResponse = mockMvc.perform(get(createdLocation))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        Inventory inventory = objectMapper.readValue(jsonResponse, Inventory.class);

        assertEquals(new Inventory(10, 20), inventory);
    }

    @Test
    public void post_LowThresholdSet_ShouldSetAnd201() throws Exception {
        String jsonRequest =
        "{" +
            "\"onHand\": 10," +
            "\"onOrder\": 20," +
            "\"lowThreshold\": 200" +
        "}";

        String createdLocation = mockMvc.perform(post("/inventory")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(jsonRequest))
            .andExpect(status().isCreated())
        .andReturn().getResponse().getHeader("Location");

        // Browse to location from header.
        String jsonResponse = mockMvc.perform(get(createdLocation))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        Inventory inventory = objectMapper.readValue(jsonResponse, Inventory.class);

        assertEquals(new Inventory(10, 20, 200), inventory);
    }

    @Test
    public void post_SetProduct_ShouldSucceed() throws Exception {
        Product product = new Product("Awesome Widget!", new Classification("Widget"));
        product = productRepository.save(product);

        String jsonRequest =
        "{" +
            "\"onHand\": 10," +
            "\"onOrder\": 20," +
            "\"lowThreshold\": 200" +
        "}";

        String createdLocation = mockMvc.perform(post("/inventory")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(jsonRequest))
            .andExpect(status().isCreated())
        .andReturn().getResponse().getHeader("Location");

        // Get the id from the location header
        Long id = Long.parseLong(createdLocation.substring(createdLocation.lastIndexOf('/') + 1));

        // Retrieve it from repo to assert on the product being null.
        Inventory inventory = inventoryRepository.findById(id).get();

        assertNull(inventory.getProduct());

        mockMvc.perform(put(createdLocation + "/product")
                       .content("http://localhost/product/" + product.getId())
                       .contentType("text/uri-list"))
        .andExpect(status().isNoContent());

        // Get the inventory from the repository again
        inventory = inventoryRepository.findById(id).get();

        // Verify the association worked.
        assertNotNull(inventory.getProduct());
        assertEquals(product, inventory.getProduct());
    }

}
