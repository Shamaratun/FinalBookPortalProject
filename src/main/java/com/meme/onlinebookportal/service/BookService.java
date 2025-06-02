package com.meme.onlinebookportal.service;

import com.meme.onlinebookportal.dto.AddBookDto;
import com.meme.onlinebookportal.dto.UpdateBookDto;
import com.meme.onlinebookportal.model.Author;
import com.meme.onlinebookportal.model.Book;
import com.meme.onlinebookportal.repository.AuthorRepository;
import com.meme.onlinebookportal.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Book addNewBook(AddBookDto addBookDto) {

        Book existBook = bookRepository.findByBookIsbnNumber(addBookDto.getBookIsbnNumber());

        if (existBook != null) {
            throw new RuntimeException("Book already exist by the ISBN Number: " + addBookDto.getBookIsbnNumber());
        }

        Book book = new Book();

        book.setBookName(addBookDto.getBookName());
        book.setBookCategory(addBookDto.getBookCategory());
        book.setBookQuantity(addBookDto.getBookQuantity());
        book.setBookIsbnNumber(addBookDto.getBookIsbnNumber());
        book.setBookPrice(addBookDto.getBookPrice());
        book.setBookRating(addBookDto.getBookRating());

        if (addBookDto.getBookAuthorIds() != null) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(addBookDto.getBookAuthorIds()));
            book.setBookAuthors(authors);
        }

        return bookRepository.save(book);
    }

    @Transactional
    public List<Book> getAllBooks() {
        List<Book> allBookList = bookRepository.findAll();
        return allBookList;
    }


    @Transactional
    public Book updateBook(UpdateBookDto updateBookDto) {

        Book existBook = bookRepository.findById(updateBookDto.getId()).orElse(null);

        if (existBook != null) {
            if(updateBookDto.getBookName() != null)
                existBook.setBookName(updateBookDto.getBookName());
            if(updateBookDto.getBookCategory() != null)
                existBook.setBookCategory(updateBookDto.getBookCategory());
            if(updateBookDto.getBookQuantity() != null)
                existBook.setBookQuantity(updateBookDto.getBookQuantity());
            if(updateBookDto.getBookIsbnNumber() != null)
                existBook.setBookIsbnNumber(updateBookDto.getBookIsbnNumber());
            if(updateBookDto.getBookPrice() != null)
                existBook.setBookPrice(updateBookDto.getBookPrice());
            if(updateBookDto.getBookRating() != null)
                existBook.setBookRating(updateBookDto.getBookRating());

            if (updateBookDto.getBookAuthorIds() != null) {
                Set<Author> authors = new HashSet<>(authorRepository.findAllById(updateBookDto.getBookAuthorIds()));
                existBook.setBookAuthors(authors);
            }

            return bookRepository.save(existBook);
        }

        return null;
    }

    @Transactional
    public String deleteExistingBook(Long id) {

        bookRepository.deleteById(id);

        return "Account deleted successfully.";
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }
}
