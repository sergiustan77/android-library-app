package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.books.model.Book;
import com.example.books.services.HttpRequestService;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    private HttpRequestService httpRequestService;
    private RecyclerView searchView;
    private TextView searchTitle;
    private ArrayList<Book> books;
    private String query = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchRecycleview);
        searchTitle = findViewById(R.id.searchTitle);
        query = getIntent().getStringExtra("query");

        new GetBookTask().execute();
    }



    private class GetBookTask extends AsyncTask<String, Void, List<String>> {
        String booksString;
        @Override
        protected List<String> doInBackground(String... strings) {

            try {
                httpRequestService = new HttpRequestService();
                books = httpRequestService.getBooksBySearch(Search.this, query);

                return new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {

            BooksAdapter booksAdapter = new BooksAdapter(Search.this, books);
            searchView.setAdapter(booksAdapter);
            ImageCardDivider imageCardDivider = new ImageCardDivider(50);
            searchView.addItemDecoration(imageCardDivider);
            searchView.setLayoutManager(new LinearLayoutManager(Search.this));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchTitle.setText("Searched for: " + query);
                }
            });

        }
    }

}