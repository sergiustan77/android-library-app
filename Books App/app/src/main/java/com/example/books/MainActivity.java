package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.books.model.Book;
import com.example.books.services.DataBaseService;
import com.example.books.services.HttpRequestService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] searchQueries = {"Title", "Author", "Category"};
    private Spinner searchSpinner;
    private ListView listOfBooks;
    private ArrayAdapter<String> booksAdapter;
    private ArrayList<Book> books;
    private HttpRequestService httpRequestService;
    private TextView titleOfApp;
    private RecyclerView bookListView;
    String recData = null;
    private DataBaseService dataBaseService;
  private SearchView searchBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        httpRequestService = new HttpRequestService();

        bookListView = findViewById(R.id.favoritesRecycleView);
        books = new ArrayList<>();

        searchBooks = findViewById(R.id.searchView);
        searchBooks.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent textQuery = new Intent(MainActivity.this, Search.class);
                textQuery.putExtra("query", s);
                startActivity(textQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        new GetBookTask().execute();




    }

    public void onGoToFavorites(View view) {
        List<Book> favoriteBooks = new ArrayList<>();
        dataBaseService = new DataBaseService(getApplicationContext());
       favoriteBooks = dataBaseService.getAllFavoriteBooks();
        Intent goToFavorites = new Intent(this, Favorites.class);
        Bundle args = new Bundle();
        args.putSerializable("favbooklist",(Serializable)favoriteBooks);
        goToFavorites.putExtra("favBooks", args);

        startActivity(goToFavorites);
    }

    private class GetBookTask extends AsyncTask<String, Void, List<String>>{
        String booksString;
        @Override
        protected List<String> doInBackground(String... strings) {

            try {
            httpRequestService = new HttpRequestService();
            books = httpRequestService.getAllBooks(MainActivity.this);

                return new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {

            BooksAdapter booksAdapter = new BooksAdapter(MainActivity.this, books);
            bookListView.setAdapter(booksAdapter);
            ImageCardDivider imageCardDivider = new ImageCardDivider(50);
            bookListView.addItemDecoration(imageCardDivider);
            bookListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        }
    }




}