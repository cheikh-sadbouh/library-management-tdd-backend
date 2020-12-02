package com.hexad.interview.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
  @Autowired private IUserRepository userRepository;

  @Test
  public void Should_ReturnUserDetails_When_UserCreated() {
    assertThat(userRepository.getUser().getBorrowedBookList()).isEmpty();
    assertThat(userRepository.getUser().getId()).isEqualTo("1");
    assertThat(userRepository.getUser().getName()).isEqualTo("user1");
  }
}
