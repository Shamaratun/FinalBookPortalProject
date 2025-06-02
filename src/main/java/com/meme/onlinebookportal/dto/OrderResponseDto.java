package com.meme.onlinebookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private BigDecimal orderPrice;
    private Long userId;
    private List<Long> bookIds;
    private LocalDateTime createdAt;
}
