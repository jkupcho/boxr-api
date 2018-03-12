package com.conf.jkupcho.boxr.inventory;

import com.conf.jkupcho.boxr.core.JacksonConfiguration;
import com.conf.jkupcho.boxr.product.Classification;
import com.conf.jkupcho.boxr.product.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@Import(JacksonConfiguration.class)
public class InventoryJsonTests {

    @Autowired
    private JacksonTester<Inventory> json;

    private Inventory inventory;

    @Value("classpath:com/conf/jkupcho/boxr/inventory/inventory.json")
    private Resource inventoryJson;

    @Before
    public void setup() {
        Classification toy = new Classification("Toy");
        inventory = new Inventory(10, 20, 1000);
    }

    @Test
    public void testSerialize() throws Exception {
        JsonContent<Inventory> content = this.json.write(inventory);
        assertThat(content).isEqualToJson("inventory.json");

        assertThat(content).extractingJsonPathNumberValue("@.onHand").isEqualTo(10);
        assertThat(content).extractingJsonPathNumberValue("@.onOrder").isEqualTo(20);
        assertThat(content).extractingJsonPathNumberValue("@.lowThreshold").isEqualTo(1000);
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = IOUtils.toString(inventoryJson.getInputStream(), "UTF-8");

        assertThat(json.parse(content)).isEqualTo(inventory);
    }

}
