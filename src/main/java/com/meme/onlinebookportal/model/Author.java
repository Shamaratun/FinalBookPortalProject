package com.meme.onlinebookportal.model;

import com.meme.onlinebookportal.dto.AuthorResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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

    @ManyToMany(mappedBy = "bookAuthors", fetch = FetchType.LAZY)
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