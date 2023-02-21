package com.example.books.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;


import com.example.books.adapters.FavoritesAdapter;
import com.example.books.R;
import com.example.books.model.Book;
import com.example.books.services.DataBaseService;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView favorites;
    private DataBaseService dataBaseService;
    private MaterialToolbar topAppBarr;
    private List<Book> favoriteBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        dataBaseService = new DataBaseService(this);
        favoriteBooks = dataBaseService.getAllFavoriteBooks();
        topAppBarr = findViewById(R.id.topAppBarFav);
        topAppBarr.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }) ;
        favorites = findViewById(R.id.favoriteBooks);
        FavoritesAdapter booksAdapter = new FavoritesAdapter(this, (ArrayList<Book>) favoriteBooks);
        favorites.setAdapter(booksAdapter);
        favorites.setLayoutManager(new LinearLayoutManager(this));

    }
}