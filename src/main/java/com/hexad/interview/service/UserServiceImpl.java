package com.hexad.interview.service;

import com.hexad.interview.model.Book;
import com.hexad.interview.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserServiceImpl implements IUserService {
  @Autowired private IBookService bookService;
  @Autowired private IUserRepository userRepository;

  @Override
  public Set<Book> getLibraryBookList() {
    return bookService.getLibraryBookList().get();
  }

  @Override
  public String borrowBook(String bookId) {
    AtomicReference<String> result = new AtomicReference<>("Book Not Found");
    if (hasReachedBorrowLimit()) {
      result.set("reached maximum borrow limit");
      return result.get();
    } else if (hasBookCopy(bookId)) {
      result.set("already has a book copy");
      return result.get();
    } else {
      bookService
          .findBookById(bookId)
          .ifPresent(
              book -> {
                userRepository.getUser().getBorrowedBookList().add(book);
                bookService.decrementBookNumberOfCopyByOne(book.getIsbn());
                result.set("book " + bookId + " has been borrowed");
              });
      return result.get();
    }
  }

  @Override
  public String returnBook(String bookId) {
    AtomicReference<String> result = new AtomicReference<>("Book Not Found");
    System.out.println(userRepository.getUser().getBorrowedBookList());
    userRepository.getUser().getBorrowedBookList().stream()
        .filter(book -> book.getIsbn().equals(bookId))
        .findAny()
        .ifPresent(
            book -> {
              userRepository.getUser().getBorrowedBookList().remove(book);
              bookService.incrementBookNumberOfCopyByOne(book.getIsbn());
              System.out.println(userRepository.getUser().getBorrowedBookList());
              result.set("Book " + bookId + " has been returned");
            });

    return result.get();
  }

  @Override
  public Optional<List<Book>> getBorrowedBooks() {
    return Optional.of(userRepository.getUser().getBorrowedBookList());
  }

  @Override
  public Boolean hasReachedBorrowLimit() {
    return userRepository.getUser().getBorrowedBookList().size() >= 2;
  }

  @Override
  public Boolean hasBookCopy(String bookId) {
    return userRepository.getUser().getBorrowedBookList().stream()
        .anyMatch(book -> book.getIsbn().equals(bookId));
  }
}
