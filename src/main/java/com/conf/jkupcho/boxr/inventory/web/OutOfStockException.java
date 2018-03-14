package com.conf.jkupcho.boxr.inventory.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OutOfStockException extends RuntimeException {

    public OutOfStockException() {
        super("out of stock!");
    }

}
