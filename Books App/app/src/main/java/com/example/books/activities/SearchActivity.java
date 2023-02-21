package com.example.books.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.books.adapters.BooksAdapter;
import com.example.books.R;
import com.example.books.model.Book;
import com.example.books.services.RequestsService;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RequestsService httpRequestService;
    private RecyclerView searchView;
    private TextView searchTitle;
    private ArrayList<Book> books;
    private String query = null;
    private MaterialToolbar topAppBarr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        topAppBarr = findViewById(R.id.topAppBarSearch);
        searchView = findViewById(R.id.searchRecycleview);
        query = getIntent().getStringExtra("query");
        searchTitle = findViewById(R.id.searchTitle);
        searchTitle.setText("Searched for: " + query);

        topAppBarr.setNavigationOnClickListener(view -> {

            finish();

        });




        topAppBarr.setOnMenuItemClickListener ( menuItem ->
        {
            if(menuItem.getItemId() == R.id.favorite) {
                Intent goToFavorites = new Intent(this, FavoritesActivity.class);
                startActivity(goToFavorites);
                return true;
            }

            return false;
        });
        new GetBookTask().execute();
    }



    private class GetBookTask extends AsyncTask<String, Void, List<String>> {
        String booksString;
        @Override
        protected List<String> doInBackground(String... strings) {

            try {
                httpRequestService = new RequestsService();
                books = httpRequestService.getBooksBySearch(SearchActivity.this, query);

                return new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {

            BooksAdapter booksAdapter = new BooksAdapter(SearchActivity.this, books);
            searchView.setAdapter(booksAdapter);
            searchView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));


        }
    }

}