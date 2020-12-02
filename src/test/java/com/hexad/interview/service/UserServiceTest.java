package com.hexad.interview.service;

import com.hexad.interview.model.Book;
import com.hexad.interview.model.User;
import com.hexad.interview.repository.IUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
  @SpyBean private IUserService userService;
  @MockBean private IUserRepository userRepository;
  @MockBean private IBookService bookService;

  @Test
  public void Should_BorrowBook_When_BookIsFoundInLibraryBookList() {
    given(bookService.findBookById("1")).willReturn(Optional.of(new Book("1", "book1", 2,"","")));
    doReturn(new User("1", "user1", new ArrayList<>())).when(userRepository).getUser();
    assertThat(userService.borrowBook("1")).isEqualTo("book 1 has been borrowed");
    assertThat(userRepository.getUser().getBorrowedBookList().size()).isOne();
  }

  @Test
  public void Should_NotBorrowBook_When_BookIsNotFoundInLibraryBookList() {
    doReturn(false).when(userService).hasReachedBorrowLimit();
    doReturn(false).when(userService).hasBookCopy(any());
    given(bookService.findBookById("1")).willReturn(Optional.empty());

    assertThat(userService.borrowBook("1")).isEqualTo("Book Not Found");
  }

  @Test
  public void Should_NotBorrowBook_When_UserHasReachedBorrowLimit() {
    doReturn(true).when(userService).hasReachedBorrowLimit();
    assertThat(userService.borrowBook("1")).isEqualTo("reached maximum borrow limit");
  }

  @Test
  public void Should_NotBorrowBook_When_UserHasBookCopy() {
    doReturn(false).when(userService).hasReachedBorrowLimit();
    doReturn(true).when(userService).hasBookCopy(any());
    assertThat(userService.borrowBook("1")).isEqualTo("already has a book copy");
  }

  @Test
  public void Should_ReturnBook_When_BookIsFoundInBorrowedBookList() {
    doReturn(new User("1", "user1", new ArrayList<>(Arrays.asList(new Book("1", "book1", 2,"","")))))
        .when(userRepository)
        .getUser();

    doNothing().when(bookService).incrementBookNumberOfCopyByOne(any());
    assertThat(userService.returnBook("1")).isEqualTo("Book 1 has been returned");
    assertThat(userRepository.getUser().getBorrowedBookList().size()).isZero();
  }

  @Test
  public void Should_NotReturnBook_When_BookIsNotFoundInBorrowedBookList() {
    doReturn(new User("1", "user1", new ArrayList<>(Arrays.asList(new Book("2", "book1", 2,"","")))))
        .when(userRepository)
        .getUser();

    doNothing().when(bookService).incrementBookNumberOfCopyByOne(any());
    assertThat(userService.returnBook("1")).isEqualTo("Book Not Found");
    assertThat(userRepository.getUser().getBorrowedBookList().size()).isOne();
  }

  @Test
  public void Should_ReturnBorrowedBookList_When_RequestedByUser() {
    doReturn(new User("1", "user1", new ArrayList<>(Arrays.asList(new Book("2", "book1", 2,"","")))))
        .when(userRepository)
        .getUser();
    assertThat(userService.getBorrowedBooks().get().size()).isOne();
  }

  @Test
  public void Should_ReturnLibraryBookList_When_RequestedByUser() {
    doReturn(
            Optional.of(
                new HashSet<>(
                    Arrays.asList(new Book("1", "book1", 20,"",""), new Book("2", "book2", 40,"","")))))
        .when(bookService)
        .getLibraryBookList();
    assertThat(userService.getLibraryBookList().size()).isEqualTo(2);
  }
}
