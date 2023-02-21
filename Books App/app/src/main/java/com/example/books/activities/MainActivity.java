package com.example.books.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.SearchView;


import com.example.books.adapters.BooksAdapter;
import com.example.books.R;
import com.example.books.model.Book;
import com.example.books.services.RequestsService;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Book> books;
    private RequestsService httpRequestService;

    private RecyclerView bookListView;
    private MaterialToolbar topAppBar;

    private SearchView searchBooks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        httpRequestService = new RequestsService();

        bookListView = findViewById(R.id.favoritesRecycleView);
        books = new ArrayList<>();

        searchBooks = findViewById(R.id.searchView);
        searchBooks.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchForText(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setOnMenuItemClickListener(menuItem ->
        {
            if (menuItem.getItemId() == R.id.favorite) {
                Intent goToFavorites = new Intent(this, FavoritesActivity.class);
                startActivity(goToFavorites);
                return true;
            }

            return false;
        });


        new GetBookTask().execute();

    }

    private void searchForText(String s) {
        Intent textQuery = new Intent(this, SearchActivity.class);
        textQuery.putExtra("query", s);
        startActivity(textQuery);
    }

    private class GetBookTask extends AsyncTask<String, Void, List<String>> {
        String booksString;

        @Override
        protected List<String> doInBackground(String... strings) {

            try {

                books = httpRequestService.getAllBooks(MainActivity.this);
              //  Log.d("BOOKS", "doInBackground: " + books.toString());
                return new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {

            BooksAdapter booksAdapter = new BooksAdapter(MainActivity.this, books);
            //Log.d("BOOKS!!", "onPostExecute: " + books.toString());
            bookListView.setAdapter(booksAdapter);
            bookListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        }
    }


}