package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.example.books.model.Book;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {
    private RecyclerView favorites;

    private ArrayList<Book> favBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("favBooks");
        favBooks = (ArrayList<Book>) args.getSerializable("favbooklist");


        favorites = findViewById(R.id.favoriteBooks);
        FavoritesAdapter booksAdapter = new FavoritesAdapter(this, favBooks);
        favorites.setAdapter(booksAdapter);
        ImageCardDivider imageCardDivider = new ImageCardDivider(50);
        favorites.addItemDecoration(imageCardDivider);
        favorites.setLayoutManager(new LinearLayoutManager(this));
    }
}