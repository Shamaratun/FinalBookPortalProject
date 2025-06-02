package com.meme.onlinebookportal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meme.onlinebookportal.dto.AuthorResponseDto;
import com.meme.onlinebookportal.dto.BookResponseDto;
import com.meme.onlinebookportal.dto.OrderRequestDto;
import com.meme.onlinebookportal.model.Author;
import com.meme.onlinebookportal.model.Book;
import com.meme.onlinebookportal.service.AuthorService;
import com.meme.onlinebookportal.service.BookService;
import com.meme.onlinebookportal.service.OrderService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	private final BookService bookService;
	private final OrderService orderService;
	private final AuthorService authorService;

	public UserController(BookService bookService, OrderService orderService, AuthorService authorService) {
		this.bookService = bookService;
		this.orderService = orderService;
		this.authorService = authorService;
	}

//	@GetMapping("/get/all/books")
//	public ResponseEntity<?> getAllBooks() {
//
//		List<Book> allBooks = bookService.getAllBooks();
//
//		return new ResponseEntity<>(allBooks, HttpStatus.OK);
//	}

	@GetMapping("/get/all/books")
	public ResponseEntity<?> getAllBooks() {
		List<Book> bookList = authorService.getAllBooks();
		List<BookResponseDto> dtos = new ArrayList<>();

		for (Book book : bookList) {
			BookResponseDto dto = new BookResponseDto();
			dto.setBookName(book.getBookName());
			dto.setBookIsbnNumber(book.getBookIsbnNumber());
			dto.setBookPrice(book.getBookPrice());
			dto.setBookRating(book.getBookRating());
			dto.setBookQuantity(book.getBookQuantity());
			dto.setBookCategory(book.getBookCategory());

			dtos.add(dto);
		}

		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/get/all/authors")
	public ResponseEntity<?> getAllAuthors() {
		List<Author> authorList = authorService.getAllAuthors();
		List<AuthorResponseDto> dtos = new ArrayList<>();

		for (Author author : authorList) {
			AuthorResponseDto dto = new AuthorResponseDto();
			dto.setAuthorName(author.getAuthorName());
			dto.setAddress(author.getAddress());
			dto.setAuthorBio(author.getAuthorBio());
			dto.setAuthorNid(author.getAuthorNid());

			dtos.add(dto);
		}

		return ResponseEntity.ok(dtos);
	}

	@PostMapping("/order/request")
	public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
		ResponseEntity<?> orderRequest = orderService.placeOrderRequest(orderRequestDto);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
