package com.meme.onlinebookportal.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
	private Long id;
	private String bookName;
	private int bookIsbnNumber;
	private BigDecimal bookPrice;
	private BigDecimal bookRating;
	private Integer bookQuantity;
	private String bookCategory;
	private String bookImageUrl;
}