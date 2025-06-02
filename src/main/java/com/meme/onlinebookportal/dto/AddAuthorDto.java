package com.meme.onlinebookportal.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddAuthorDto {

	private String authorName;
	private Integer authorNid;
	private String authorBio;
	private String address;
//	private Set<Long> bookAuthorIds;
}
