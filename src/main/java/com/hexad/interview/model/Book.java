package com.hexad.interview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

  private String isbn;
  private String title;
  private int numberOfCopy;
  private String thumbnailUrl;
  private String description;
}
