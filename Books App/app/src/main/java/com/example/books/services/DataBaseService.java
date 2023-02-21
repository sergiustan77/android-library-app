package com.example.books.services;

import android.content.Context;


import com.example.books.dao.BookDao;
import com.example.books.database.Database;
import com.example.books.model.Book;

import java.util.List;

public class DataBaseService {

    private BookDao bookDao;
    private Database database;
    private Context context;

    public DataBaseService(Context context) {
        this.context = context;
        database = Database.getDataBaseInstance(context);
        bookDao = database.bookDao();
    }

    public boolean saveToBooksLibraryDatabase(Book book) {
        if(bookDao.findBookByVolumeID(book.getVolumeID()) == null){
        bookDao.insertBook(book);
        return true;} else {

            return false;
        }
    }

    public List<Book> getAllFavoriteBooks() {
        List<Book> books = bookDao.getAllBooks();

        return books;
    }

    public void removeFromFavorites(Book book) {
        bookDao.delete(book);

    }

}
