package com.hexad.interview.repository;

import com.hexad.interview.model.Book;

import java.util.Set;

public interface IBookRepository {
  Set<Book> getLibraryBookList();
}
