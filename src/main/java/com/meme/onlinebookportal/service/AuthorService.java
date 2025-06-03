package com.meme.onlinebookportal.service;

import com.meme.onlinebookportal.dto.AddAuthorDto;
import com.meme.onlinebookportal.dto.UpdateAuthorDto;
import com.meme.onlinebookportal.model.Author;
import com.meme.onlinebookportal.model.Book;
import com.meme.onlinebookportal.repository.AuthorRepository;
import com.meme.onlinebookportal.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Author addNewAuthor(AddAuthorDto addAuthorDto) {
        if (addAuthorDto == null) {
            throw new IllegalArgumentException("AddAuthorDto must not be null");
        }

        Author author = new Author();
        author.setAuthorName(addAuthorDto.getAuthorName());
        author.setAuthorBio(addAuthorDto.getAuthorBio());
        author.setAuthorNid(addAuthorDto.getAuthorNid());
        author.setAddress(addAuthorDto.getAddress());

        return authorRepository.save(author);
    }

    @Transactional
    public Author updateAuthor(UpdateAuthorDto updateAuthorDto) {

        Author existingAuthor = authorRepository.findById(updateAuthorDto.getAuthorId())
                .orElse(null);

        if (existingAuthor != null) {
            existingAuthor.setAuthorName(updateAuthorDto.getAuthorName());
            existingAuthor.setAuthorBio(updateAuthorDto.getAuthorBio());
            existingAuthor.setAuthorNid(updateAuthorDto.getAuthorNid());
            existingAuthor.setAddress(updateAuthorDto.getAddress());

            return authorRepository.save(existingAuthor);
        }
        return null;
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        List<Book> allWithAuthors = bookRepository.findAllBooksWithAuthors();
        return allWithAuthors;
    }

    // Debug method
    @Transactional(readOnly = true)
    public void debugBookAuthors() {
        List<Book> books = bookRepository.findAllBooks();
        for (Book book : books) {
            System.out.println("Book: " + book.getBookName());
            try {
                Set<Author> bookAuthors = book.getBookAuthors();
                System.out.println("Authors size: " + bookAuthors.size());
                // This will trigger lazy loading if needed
                bookAuthors.forEach(author ->
                        System.out.println("Author: " + author.getAuthorName())
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
