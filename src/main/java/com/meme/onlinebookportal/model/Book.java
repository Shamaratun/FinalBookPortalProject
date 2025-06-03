package com.meme.onlinebookportal.model;

import com.meme.onlinebookportal.dto.BookResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "meme_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "isbn_number")
    private Integer bookIsbnNumber;

    @Column(name = "book_price")
    private BigDecimal bookPrice;

    // TODO: Need to add review option later
    @Column(name = "book_rating")
    private BigDecimal bookRating;

    @Column(name = "book_category")
    private String bookCategory;

    @Column(name = "book_imageUrl")
    private String bookImageUrl;

    @Column(name = "book_quantity")
    private Integer bookQuantity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> bookAuthors = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public BookResponseDto toBookResponseDto() {
        BookResponseDto dto = new BookResponseDto();

        dto.setId(this.id);
        dto.setBookName(this.bookName);
        dto.setBookIsbnNumber(this.bookIsbnNumber);
        dto.setBookPrice(this.bookPrice);
        dto.setBookRating(this.bookRating);
        dto.setBookQuantity(this.bookQuantity);
        dto.setBookCategory(this.bookImageUrl);
        dto.setBookCategory(this.bookCategory);

        return dto;
    }

}