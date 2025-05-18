package com.retailer.reward_service.exceptions;

public class InvalidTransactionAmountException extends RuntimeException{
    public InvalidTransactionAmountException(String message) {
        super(message);
    }

}
