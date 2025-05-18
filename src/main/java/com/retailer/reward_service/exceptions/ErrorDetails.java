package com.retailer.reward_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDetails {

    LocalDateTime dateTime;
    String description;
    private String message;

}
