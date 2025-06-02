package com.meme.onlinebookportal.repository;

import com.meme.onlinebookportal.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByBookIsbnNumber(Integer bookIsbnNumber);
}
