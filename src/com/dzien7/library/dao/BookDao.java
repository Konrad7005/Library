package com.dzien7.library.dao;

import com.dzien7.library.domain.Book;

import java.util.List;

/**
 * Created by Konrad on 2016-11-16.
 */
public interface BookDao {
    void addBook( Book book);     // metoda ktora nic nie zwraca
    void removeBook(Book book);
    List<Book> getAllBooks();


}
