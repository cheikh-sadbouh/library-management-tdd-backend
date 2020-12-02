package com.hexad.interview.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {
  @Autowired private IBookRepository bookRepository;

  @Test
  public void Should_ReturnLibraryBookList_When_LibraryBookListIsNotEmpty() {
    assertThat(bookRepository.getLibraryBookList().size()).isEqualTo(4);
  }
}
