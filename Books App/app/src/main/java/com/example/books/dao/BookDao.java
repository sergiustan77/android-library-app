package com.example.books.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.books.model.Book;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    List<Book> getAllBooks();

    @Query("SELECT * FROM book where volume_id=:volumeID")
    public Book findBookByVolumeID(String volumeID);

    @Insert
    void insertBook(Book... books);

    @Delete
    void delete(Book book);
}
