package com.tenx.ms.retail.order.exception;

import java.util.NoSuchElementException;

public class ProductNotAvailableException extends NoSuchElementException {
    public ProductNotAvailableException() {
        super();
    }

    public ProductNotAvailableException(String message) {
        super(message);
    }
}
