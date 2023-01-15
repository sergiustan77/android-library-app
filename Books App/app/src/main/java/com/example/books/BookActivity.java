package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.books.model.Book;
import com.example.books.services.DataBaseService;
import com.example.books.services.HttpRequestService;
import com.google.android.material.appbar.MaterialToolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    private HttpRequestService httpRequestService;
    private Book book = new Book();
    private String volumeID;
    private TextView bookInfo;
    private MaterialToolbar topAppBarr;
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView bookDescription;
    private TextView bookAuthor;
    private TextView bookPublished;
    private DataBaseService dataBaseService;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        volumeID = getIntent().getExtras().getString("volumeID");
        Log.d("VOLUMEID", "onCreate: " + volumeID);
        bookInfo = findViewById(R.id.textBookDescriptionTitle);
        bookInfo = findViewById(R.id.textBookDescriptionTitle);
        topAppBarr = findViewById(R.id.topAppBar);
        bookImage = findViewById(R.id.bookImage);
        bookTitle = findViewById(R.id.bookTitle);
        bookDescription = findViewById(R.id.bookDescription);
        bookAuthor = findViewById(R.id.bookAuthors);
        bookPublished = findViewById(R.id.bookPublished);



        topAppBarr.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goBack = new Intent(BookActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        }) ;

        topAppBarr.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.favorite) {
                    addBookToFavorites(book);
                }
                return false;
            }
        }) ;


        new GetBookTask().execute();
    }

    public void goToBuyPage(View view) {
        Uri uri;
        String url = book.getBuyLink();
        if(url != null){
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                url  = "http://" + url ;}

         uri = Uri.parse(url);}
        else {
            url = book.getPreviewLink();
            if(url != null){
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                url  = "http://" + url ;} }
            else if(url == null) {
                url = "https://www.youtube.com/watch?v=eBGIQ7ZuuiU&ab_channel=YouGotRickRolled";
            }
            uri = Uri.parse(url);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    private class GetBookTask extends AsyncTask<String, Void, List<String>> {
        String bookString;
        @Override
        protected List<String> doInBackground(String... strings) {

            try {
                httpRequestService = new HttpRequestService();
                book = httpRequestService.getBookById( volumeID);
                Log.d("PICTURE", "run: " + book.getTitle());

                return new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            Glide.with(bookImage).load(book.getImageSmallBookPage()).placeholder(R.drawable.hearticonwhite).into(bookImage);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("IMAGE!!!", "run: " + book.getImageSmallBookPage());

                    Log.d("BUY LINK", "run: " + book.getBuyLink());
                bookTitle.setText(book.getTitle());
                bookDescription.setText(book.getDescription());
                bookAuthor.setText("by " + book.getAuthors());
                bookPublished.setText(book.getPublishedDate());


               // bookInfo.setText(book.toString());
                }
            });

        }
    }

    private void addBookToFavorites (Book book) {
        dataBaseService = new DataBaseService(getApplicationContext());
        dataBaseService.saveToBooksLibraryDatabase(book);


    }
}