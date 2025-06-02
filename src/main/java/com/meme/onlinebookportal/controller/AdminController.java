package com.meme.onlinebookportal.controller;

import com.meme.onlinebookportal.dto.*;
import com.meme.onlinebookportal.model.Author;
import com.meme.onlinebookportal.model.Book;
import com.meme.onlinebookportal.service.AuthorService;
import com.meme.onlinebookportal.service.BookService;
import com.meme.onlinebookportal.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final OrderService orderService;

    public AdminController(BookService bookService, AuthorService authorService, OrderService orderService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id); // Assume this fetches the book
        return ResponseEntity.ok(book);
    }

    @PostMapping("/add/book")
    public ResponseEntity<?> addNewBook(@RequestBody AddBookDto addBookDto) {
        Book book = bookService.addNewBook(addBookDto);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
//	@PostMapping("/add/book")
//	public ResponseEntity<?> addNewBook(@RequestBody AddBookDto addBookDto) {
//		Book book = bookService.addNewBook(addBookDto);
//
//		return new ResponseEntity<>(book, HttpStatus.CREATED);
//	}

    @PutMapping("/update/book")
    public ResponseEntity<?> updateExistingBook(@PathVariable Long id, @RequestBody UpdateBookDto updateBookDto) {
        Book book = bookService.updateNewBook(updateBookDto);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/delete/book/{id}")
    public ResponseEntity<Void> deleteExistingBook(@PathVariable Long id) {
        bookService.deleteExistingBook(id);
        return ResponseEntity.noContent().build();
    }

    // author
    @PostMapping("/add/author")
    public ResponseEntity<?> addNewAuthor(@RequestBody AddAuthorDto addAuthorDto) {
        Author author = authorService.addNewAuthor(addAuthorDto);
        AuthorResponseDto responseDto = author.toResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/get/all/authors")
    public ResponseEntity<?> getAllAuthors() {
        List<Author> author = authorService.getAllAuthors();
        List<AuthorResponseDto> authorResponseDtos = author
                .stream()
                .map(Author::toResponseDto)
                .toList();

        return new ResponseEntity<>(authorResponseDtos, HttpStatus.OK);
    }

    @PutMapping("/update/author")
    public ResponseEntity<?> updateAuthro(@RequestBody UpdateAuthorDto updateAuthorDto) {
        Author author = authorService.updateAuthor(updateAuthorDto);
        if (author != null) {
            AuthorResponseDto responseDto = author.toResponseDto();
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

}
//@PostMapping("/add/book")
//public ResponseEntity<?> addNewBook(@RequestBody AddBookDto addBookDto) {
//    Book book = packageService.addNewBook(addBookDto);
//    return new ResponseEntity<>(book, HttpStatus.CREATED);
//}
//
//@PutMapping("/edit/package")
//public ResponseEntity<?> updatePackage(@RequestBody CreditPackage editBody){
//
//    CreditPackage creditPackage = creditPackageService.updatePackage(editBody);
//
//    return new ResponseEntity<>(creditPackage, HttpStatus.OK);
//}
//
//@DeleteMapping("/delete/package")
//public ResponseEntity<?> deletePackage(@RequestParam Long id){
//
//    String deletePackage = creditPackageService.deletePackageById(id);
//
//    return new ResponseEntity<>(deletePackage, HttpStatus.OK);
//}