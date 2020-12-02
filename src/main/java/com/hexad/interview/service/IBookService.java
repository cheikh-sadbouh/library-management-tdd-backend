package com.hexad.interview.service;

import com.hexad.interview.model.Book;

import java.util.Optional;
import java.util.Set;

public interface IBookService {
  Optional<Set<Book>> getLibraryBookList();

  Optional<Book> findBookById(String bookId);

  void incrementBookNumberOfCopyByOne(String bookId);

  void decrementBookNumberOfCopyByOne(String bookId);

  void removeBook(Book bookId);
}
