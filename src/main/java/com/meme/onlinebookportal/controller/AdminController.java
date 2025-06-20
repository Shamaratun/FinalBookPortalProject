package com.meme.onlinebookportal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meme.onlinebookportal.dto.AddAuthorDto;
import com.meme.onlinebookportal.dto.AddBookDto;
import com.meme.onlinebookportal.dto.AuthorResponseDto;
import com.meme.onlinebookportal.dto.BookResponseDto;
import com.meme.onlinebookportal.dto.BookWithAuthorsDto;
import com.meme.onlinebookportal.dto.UpdateAuthorDto;
import com.meme.onlinebookportal.dto.UpdateBookDto;
import com.meme.onlinebookportal.model.Author;
import com.meme.onlinebookportal.model.Book;
import com.meme.onlinebookportal.service.AuthorService;
import com.meme.onlinebookportal.service.BookService;
import com.meme.onlinebookportal.service.OrderService;
import com.meme.onlinebookportal.service.UserService;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

	private final BookService bookService;
	private final AuthorService authorService;
	private final OrderService orderService;
	private final UserService userService;

	public AdminController(BookService bookService, AuthorService authorService, OrderService orderService,
			UserService userService) {
		this.bookService = bookService;
		this.authorService = authorService;
		this.orderService = orderService;
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Book book = bookService.getBookById(id);
		return ResponseEntity.ok(book);
	}

	@GetMapping("/books/withAuthor")
	public ResponseEntity<?> getBooksWithAuthorName() {

		System.out.println("Debug started");
		authorService.debugBookAuthors();
		System.out.println("Debug ended");

		List<Book> books = authorService.getAllBooks();

		List<BookWithAuthorsDto> bookWithAuthorsDtoList = new ArrayList<>();

		for (Book book : books) {
			StringBuilder authorNamesBuilder = new StringBuilder();

			for (Author author : book.getBookAuthors()) {
				if (!authorNamesBuilder.isEmpty()) {
					authorNamesBuilder.append(", ");
				}
				authorNamesBuilder.append(author.getAuthorName());
			}

			String authorNames = authorNamesBuilder.toString();

			BookWithAuthorsDto dto = new BookWithAuthorsDto(book.getId(), book.getBookName(), book.getBookIsbnNumber(),
					book.getBookPrice(), book.getBookRating(), book.getBookQuantity(), book.getBookCategory(),
					book.getBookImageUrl(), authorNames);

			bookWithAuthorsDtoList.add(dto);
		}

		return ResponseEntity.ok(bookWithAuthorsDtoList);
	}

	@PostMapping("/add/book")
	public ResponseEntity<?> addNewBook(@RequestBody AddBookDto addBookDto) {
		Book book = bookService.addNewBook(addBookDto);
		BookResponseDto bookResponseDto = book.toBookResponseDto();
		return new ResponseEntity<>(bookResponseDto, HttpStatus.CREATED);
	}

	@PutMapping("/update/book")
	public ResponseEntity<?> updateBook(@RequestBody UpdateBookDto updateBookDto) {
		Book book = bookService.updateBook(updateBookDto);
		BookResponseDto bookResponseDto = book.toBookResponseDto();
		return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/delete/book/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteExistingBook(id);
		return ResponseEntity.noContent().build();
	}

	// author
	@PostMapping("/add/author")
	public ResponseEntity<?> addNewAuthor(@RequestBody AddAuthorDto addAuthorDto) {
		Author author = authorService.addNewAuthor(addAuthorDto);
		AuthorResponseDto responseDto = toResponseDto(author);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	@GetMapping("/get/all/authors")
	public ResponseEntity<?> getAllAuthors() {
		List<Author> author = authorService.getAllAuthors();
		List<AuthorResponseDto> authorResponseDtos = author.stream().map(this::toResponseDto).toList();

		return new ResponseEntity<>(authorResponseDtos, HttpStatus.OK);
	}

	private AuthorResponseDto toResponseDto(Author auth) {
		AuthorResponseDto dto = new AuthorResponseDto();
		dto.setAuthorId(auth.getAuthorId());
		dto.setAuthorName(auth.getAuthorName());
		dto.setAuthorNid(auth.getAuthorNid());
		dto.setAuthorBio(auth.getAuthorBio());
		dto.setAddress(auth.getAddress());
		return dto;
	}

	@PutMapping("/update/author")
	public ResponseEntity<?> updateAuthor(@RequestBody UpdateAuthorDto updateAuthorDto) {
		Author author = authorService.updateAuthor(updateAuthorDto);
		if (author != null) {
			AuthorResponseDto responseDto = toResponseDto(author);
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		} else {
			return ResponseEntity.ok(null);
		}
	}

	// TODO: Later make an endpoint where books can be found by author id

	@DeleteMapping("/delete/authors/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		authorService.deleteAuthor(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/get/all/order")
	public ResponseEntity<?> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@GetMapping("/get/all/users")
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

}