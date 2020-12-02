package com.hexad.interview.service;

import com.hexad.interview.model.Book;
import com.hexad.interview.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookServiceTest {

  @Autowired private IBookService bookService;
  @MockBean private IBookRepository bookRepository;

  @BeforeEach
  void loadTestData() {
    given(bookRepository.getLibraryBookList())
        .willReturn(
            new HashSet<>(
                Arrays.asList(
                    new Book("1", "book1", 5,"",""),
                    new Book("2", "book2", 3,"",""),
                    new Book("3", "book3", 4,"",""),
                    new Book("4", "book4", 5,"",""),
                    new Book("5", "book5", 1,"",""))));
  }

  @Test
  void Should_ReturnBookList_When_BookListNotEmpty() {
    assertThat(bookService.getLibraryBookList().get().size()).isEqualTo(5);
  }

  @Test
  void Should_FindBook_When_BookExists() {
    assertThat(bookService.findBookById("1")).isEqualTo(Optional.of(new Book("1", "book1", 5,"","")));
  }

  @Test
  void Should_ReturnEmpty_When_BookDoesNotExistExists() {
    assertThat(bookService.findBookById("100")).isEqualTo(Optional.empty());
  }

  @Test
  void Should_IncrementBookNumberOfCopyByOne_When_BookExists() {
    bookService.incrementBookNumberOfCopyByOne("1");
    assertThat(bookService.findBookById("1").get().getNumberOfCopy()).isEqualTo(6);
  }

  @Test
  void Should_DecrementBookNumberOfCopyByOne_When_BookExists() {

    bookService.decrementBookNumberOfCopyByOne("1");
    assertThat(bookService.findBookById("1").get().getNumberOfCopy()).isEqualTo(4);
  }

  @Test
  void Should_DeleteBook_When_BookNumberOfCopyIsZero() {

    bookService.decrementBookNumberOfCopyByOne("5");
    assertThat(bookRepository.getLibraryBookList().size()).isEqualTo(4);
  }

  @Test
  void Should_RemoveBook_When_BookIsFound() {
    bookService.removeBook(new Book("1", "book1", 5,"",""));
    assertThat(bookRepository.getLibraryBookList().size()).isEqualTo(4);
  }
}
