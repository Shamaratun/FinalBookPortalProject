package com.meme.onlinebookportal.dto;

import com.meme.onlinebookportal.model.Author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {
	private Long authorId;
	private String authorName;
	private Integer authorNid;
	private String authorBio;
	private String address;

	public AuthorResponseDto fromAuthro(Author author) {
		this.authorId = author.getAuthorId();
		this.authorName = author.getAuthorName();
		this.authorNid = author.getAuthorNid();
		this.authorBio = author.getAuthorBio();
		this.address = author.getAddress();
		return this;
	}

}
