package com.hexad.interview.controller;

import com.hexad.interview.model.Book;
import com.hexad.interview.service.IBookService;
import com.hexad.interview.service.IUserService;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private IUserService userService;
  @MockBean private IBookService bookService;

  @Test
  public void Should_ReturnLibraryBookList_When_LibraryBookListIsNotEmpty() throws Exception {
    doReturn(
            Optional.of(
                new HashSet<>(
                    Arrays.asList(
                        new Book("1", "book1", 20, "", ""),
                        new Book("2", "book2", 33, "", ""),
                        new Book("3", "book3", 31, "", ""),
                        new Book("4", "book4", 16, "", "")))))
        .when(bookService)
        .getLibraryBookList();
    mockMvc
        .perform(get("/api/v1/libraryBooks/").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void Should_NoContent_When_LibraryBookListIsEmpty() throws Exception {
    doReturn(Optional.empty()).when(bookService).getLibraryBookList();

    mockMvc
        .perform(get("/api/v1/libraryBooks/").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  public void Should_ReturnBorrowedBookList_When_BorrowedBookListIsNotEmpty() throws Exception {
    given(userService.getBorrowedBooks())
        .willReturn(
            Optional.of(new ArrayList<>(Arrays.asList(new Book("1", "book1", 20, "", "")))));
    mockMvc
        .perform(get("/api/v1/BorrowedBooks/").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    Mockito.verify(userService).getBorrowedBooks();
  }

  @Test
  public void Should_NoContent_When_BorrowedBookListIsEmpty() throws Exception {
    given(userService.getBorrowedBooks()).willReturn(Optional.empty());
    mockMvc
        .perform(get("/api/v1/BorrowedBooks/").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNoContent());
    Mockito.verify(userService).getBorrowedBooks();
  }

  @ParameterizedTest
  @CsvSource({
    "1,Book Not Found",
    "1,reached maximum borrow limit",
    "2,already has a book copy",
    "3,book 3 has been borrowed"
  })
  void Should_BorrowBook_When_BookIsFoundInLibraryBookList(String bookId, String expectedResult)
      throws Exception {
    given(userService.borrowBook(bookId)).willReturn(expectedResult);
    mockMvc
        .perform(put("/api/v1/BorrowBook/" + bookId).accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    Mockito.verify(userService).borrowBook(bookId);
  }

  @ParameterizedTest
  @CsvSource({"1,Book Not Found", "5,book 5 has been returned"})
  void Should_ReturnBook_When_BookIsFoundInBorrowedBookList(String bookId, String expectedResult)
      throws Exception {
    given(userService.returnBook(bookId)).willReturn(expectedResult);
    mockMvc
        .perform(put("/api/v1/ReturnBook/" + bookId).accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    Mockito.verify(userService).returnBook(bookId);
  }
}
