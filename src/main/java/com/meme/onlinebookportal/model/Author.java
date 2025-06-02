package com.meme.onlinebookportal.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.meme.onlinebookportal.dto.AuthorResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "meme_author")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authorId;

	@Column(name = "name")
	private String authorName;

	@Column(name = "author_nid")
	private Integer authorNid;

	@Column(name = "bio")
	private String authorBio;

	@Column(name = "address")
	private String address;

	@ManyToMany(mappedBy = "bookAuthors")
	@JsonBackReference(value = "book")
	private Set<Book> authorBooks = new HashSet<>();

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}
	}

	public AuthorResponseDto toResponseDto() {
		AuthorResponseDto dto = new AuthorResponseDto();
		dto.setAuthorId(this.getAuthorId());
		dto.setAuthorName(this.authorName);
		dto.setAuthorNid(this.getAuthorNid());
		dto.setAuthorBio(this.authorBio);
		dto.setAddress(this.address);
		return dto;
	}

}