package com.hexad.interview.service;

import com.hexad.interview.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserService {
  Set<Book> getLibraryBookList();

  String borrowBook(String bookId);

  String returnBook(String bookId);

  Optional<List<Book>> getBorrowedBooks();

  Boolean hasReachedBorrowLimit();

  Boolean hasBookCopy(String bookId);
}
