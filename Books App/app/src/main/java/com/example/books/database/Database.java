package com.example.books.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.books.dao.BookDao;
import com.example.books.model.Book;

@androidx.room.Database(entities = {Book.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract BookDao bookDao();

    private static Database INSTANCE;

    public static Database getDataBaseInstance(Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "database").allowMainThreadQueries().build();

        }
        return INSTANCE;
    }

}
