package com.hexad.interview.service;

import com.hexad.interview.model.Book;
import com.hexad.interview.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements IBookService {

  @Autowired private IBookRepository bookRepository;

  @Override
  public Optional<Set<Book>> getLibraryBookList() {

    return Optional.of(bookRepository.getLibraryBookList());
  }

  @Override
  public Optional<Book> findBookById(String bookId) {
    return bookRepository.getLibraryBookList().stream()
        .filter(book -> book.getIsbn().equals(bookId))
        .findAny();
  }

  @Override
  public void incrementBookNumberOfCopyByOne(String bookId) {
    findBookById(bookId).ifPresent(book -> book.setNumberOfCopy(book.getNumberOfCopy() + 1));
  }

  @Override
  public void decrementBookNumberOfCopyByOne(String bookId) {
    findBookById(bookId)
        .ifPresent(
            book -> {
              if (book.getNumberOfCopy() == 1) {
                removeBook(book);
                System.out.println("book deleted");
              } else {
                book.setNumberOfCopy(book.getNumberOfCopy() - 1);
              }
            });
  }

  @Override
  public void removeBook(Book book) {
    bookRepository.getLibraryBookList().remove(book);
  }
}
