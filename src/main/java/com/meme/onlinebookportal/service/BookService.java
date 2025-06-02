package com.meme.onlinebookportal.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.meme.onlinebookportal.dto.AddBookDto;
import com.meme.onlinebookportal.dto.UpdateBookDto;
import com.meme.onlinebookportal.model.Author;
import com.meme.onlinebookportal.model.Book;
import com.meme.onlinebookportal.repository.AuthorRepository;
import com.meme.onlinebookportal.repository.BookRepository;

import jakarta.transaction.Transactional;

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

		Set<Author> authors = authorRepository.findAllById(addBookDto.getBookAuthorIds()).stream()
				.collect(Collectors.toSet());

		book.setBookAuthors(authors);

		return bookRepository.save(book);
	}

	@Transactional
	public List<Book> getAllBooks() {
		List<Book> allBookList = bookRepository.findAll();

		return allBookList;
	}

	@Transactional
	public Book updateNewBook(UpdateBookDto updateBookDto) {

		Book existBook = bookRepository.findById(updateBookDto.getId()).orElse(null);

		if (existBook != null) {
			existBook.setBookName(updateBookDto.getBookName());
			existBook.setBookCategory(updateBookDto.getBookCategory());
			existBook.setBookQuantity(updateBookDto.getBookQuantity());
			existBook.setBookIsbnNumber(updateBookDto.getBookIsbnNumber());
			existBook.setBookPrice(updateBookDto.getBookPrice());
			existBook.setBookRating(updateBookDto.getBookRating());

			Set<Author> authors = authorRepository.findAllById(updateBookDto.getBookAuthorIds()).stream()
					.collect(Collectors.toSet());

			existBook.setBookAuthors(authors);
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
