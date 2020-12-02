package com.hexad.interview.repository;

import com.hexad.interview.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepositoryImpl implements IUserRepository {
  User user = new User("1", "user1", new ArrayList<>());

  @Override
  public User getUser() {
    return user;
  }
}
