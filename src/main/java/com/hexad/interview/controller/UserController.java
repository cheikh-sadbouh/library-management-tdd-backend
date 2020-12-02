package com.hexad.interview.controller;

import com.hexad.interview.model.Book;
import com.hexad.interview.service.IBookService;
import com.hexad.interview.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@Api(description = "Provide Rest API to Manage Library ")
public class UserController {
  @Autowired IUserService userService;
  @Autowired private IBookService bookService;

  @GetMapping(value = "/libraryBooks")
  public ResponseEntity<Set<Book>> getLibraryBooks() {

    return bookService
        .getLibraryBookList()
        .map(books -> new ResponseEntity<>(books, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
  }

  @GetMapping("/BorrowedBooks")
  public ResponseEntity<List<Book>> getBorrowedBookList() {
    return userService
        .getBorrowedBooks()
        .map(books -> new ResponseEntity<>(books, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
  }

  @PutMapping("/BorrowBook/{bookId}")
  public ResponseEntity<String> borrowBook(@PathVariable("bookId") String bookId) {
    return new ResponseEntity<>(userService.borrowBook(bookId), HttpStatus.OK);
  }

  @PutMapping("/ReturnBook/{bookId}")
  public ResponseEntity<String> returnBook(@PathVariable("bookId") String bookId) {
    return new ResponseEntity<>(userService.returnBook(bookId), HttpStatus.OK);
  }
}
