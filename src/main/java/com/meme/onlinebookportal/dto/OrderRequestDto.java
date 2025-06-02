package com.meme.onlinebookportal.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class OrderRequestDto {
    private Long userId;
    private Set<Long> bookIds;
    private BigDecimal orderPrice; // Optional if you want to calculate on backend
}