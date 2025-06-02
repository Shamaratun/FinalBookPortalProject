package com.meme.onlinebookportal.service;

import com.meme.onlinebookportal.dto.OrderRequestDto;
import com.meme.onlinebookportal.dto.OrderResponseDto;
import com.meme.onlinebookportal.model.Book;
import com.meme.onlinebookportal.model.Order;
import com.meme.onlinebookportal.model.User;
import com.meme.onlinebookportal.repository.BookRepository;
import com.meme.onlinebookportal.repository.OrderRepository;
import com.meme.onlinebookportal.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public ResponseEntity<?> placeOrderRequest(OrderRequestDto orderRequestDto) {
        Optional<User> userOptional = userRepository.findById(orderRequestDto.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Set<Book> books = new HashSet<>(bookRepository.findAllById(orderRequestDto.getBookIds()));
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid books provided");
        }

        // Calculate total price (optional if not provided)
        BigDecimal totalPrice = books.stream()
                .map(Book::getBookPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(userOptional.get());
        order.setBooks(books);
        order.setOrderPrice(orderRequestDto.getOrderPrice() != null ? orderRequestDto.getOrderPrice() : totalPrice);

        orderRepository.save(order);
        return ResponseEntity.ok("Order placed successfully!");
    }

    @Transactional
    public Object getAllOrders() {

        return orderRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private OrderResponseDto mapToDto(Order order) {
        List<Long> bookIds = order.getBooks().stream()
                .map(Book::getId)
                .collect(Collectors.toList());

        return new OrderResponseDto(
                order.getOrderId(),
                order.getOrderPrice(),
                order.getUser().getId(),
                bookIds,
                order.getCreatedAt()
        );
    }
}


