package com.conf.jkupcho.boxr.core;

import com.conf.jkupcho.boxr.inventory.Inventory;
import com.conf.jkupcho.boxr.product.Classification;
import com.conf.jkupcho.boxr.product.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.conf.jkupcho.boxr.inventory.Inventory.DEFAULT_LOW_THRESHOLD;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Module boxrModule() {
        return new BoxrModule();
    }

    static class BoxrModule extends SimpleModule {

        BoxrModule() {
            setMixInAnnotation(Inventory.class, InventoryMixin.class);
            setMixInAnnotation(Product.class, ProductMixin.class);
            setMixInAnnotation(Classification.class, ClassificationMixin.class);
        }

        abstract static class InventoryMixin {

            @JsonCreator
            public InventoryMixin(@JsonProperty("onHand") Integer onHand,
                                  @JsonProperty("onOrder") Integer onOrder,
                                  @JsonProperty(value = "lowThreshold") Integer lowThreshold) {}

        }

        abstract static class ProductMixin {

            @JsonCreator
            public ProductMixin(String description, Classification classification) {}

        }

        abstract static class ClassificationMixin {

            @JsonCreator
            public ClassificationMixin(String name) {}

        }

    }

}
