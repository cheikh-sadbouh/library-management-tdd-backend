package com.hexad.interview.repository;

import com.hexad.interview.model.Book;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Repository
public class BookRepositoryImpl implements IBookRepository {

  private Set<Book> librarybookList =
      new HashSet<>(
          Arrays.asList(
              new Book(
                  "1",
                  "Zend Framework in Action",
                  20,
                  "https://s3.amazonaws.com/AKIAJC5RLADLUMVRPFDQ.book-thumb-images/allen.jpg",
                  "A beautifully written book that is a must have for every Java Developer.       Ashish Kulkarni, Technical Director, E-Business Software Solutions Ltd."),
              new Book(
                  "2",
                  "Flex on Java",
                  33,
                  "https://s3.amazonaws.com/AKIAJC5RLADLUMVRPFDQ.book-thumb-images/allmon.jpg",
                  "A beautifully written book that is a must have for every Java Developer.       Ashish Kulkarni, Technical Director, E-Business Software Solutions Ltd."),
              new Book(
                  "3",
                  "Griffon in Action",
                  31,
                  "https://s3.amazonaws.com/AKIAJC5RLADLUMVRPFDQ.book-thumb-images/almiray.jpg",
                  "A beautifully written book that is a must have for every Java Developer.       Ashish Kulkarni, Technical Director, E-Business Software Solutions Ltd."),
              new Book(
                  "4",
                  "OSGi in Depth",
                  16,
                  "https://s3.amazonaws.com/AKIAJC5RLADLUMVRPFDQ.book-thumb-images/alves.jpg",
                  "A beautifully written book that is a must have for every Java Developer.       Ashish Kulkarni, Technical Director, E-Business Software Solutions Ltd.")));

  @Override
  public Set<Book> getLibraryBookList() {
    return librarybookList;
  }
}
