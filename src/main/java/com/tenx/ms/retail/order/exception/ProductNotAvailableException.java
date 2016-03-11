package com.tenx.ms.retail.order.exception;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException() {
    }

    public ProductNotAvailableException(String message) {
        super(message);
    }
}
