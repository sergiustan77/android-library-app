package com.example.books.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.books.R;
import com.example.books.model.Book;
import com.example.books.services.DataBaseService;
import com.example.books.services.RequestsService;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    private RequestsService httpRequestService;
    private Book book = new Book();
    private String volumeID;
    //private TextView bookInfo;
    private MaterialToolbar topAppBarr;
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView bookDescription;
    private TextView bookAuthor;
    private TextView categories;
    private TextView bookPublished;
    private TextView pageCount;
    private DataBaseService dataBaseService;
    //private CheckBox addToFavorites;

    //private String context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        volumeID = getIntent().getExtras().getString("volumeID");
        categories = findViewById(R.id.categoriesText);
        pageCount = findViewById(R.id.pageCount);
        topAppBarr = findViewById(R.id.topAppBarFav);
        bookImage = findViewById(R.id.bookImage);
        bookTitle = findViewById(R.id.bookTitle);
        bookDescription = findViewById(R.id.bookDescription);
        bookAuthor = findViewById(R.id.bookAuthors);
        bookPublished = findViewById(R.id.bookPublished);



        topAppBarr.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }) ;




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




    public void onAddBookToFavorites(View view) {
        addBookToFavorites(book);
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
            this.startActivity(intent);

    }


    private class GetBookTask extends AsyncTask<String, Void, List<String>> {
        String bookString;
        @Override
        protected List<String> doInBackground(String... strings) {

            try {
                httpRequestService = new RequestsService();
                book = httpRequestService.getBookById( volumeID);

              //  Log.d("PICTURE", "run: " + book.getTitle());

                return new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            Glide.with(bookImage).load(book.getImageSmallThumbnail()).placeholder(R.drawable.book_of_black_cover_closed_svgrepo_com__1_).into(bookImage);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Log.d("IMAGE!!!", "run: " + book.getImageSmallBookPage());

                    //Log.d("BUY LINK", "run: " + book.getBuyLink());
                bookTitle.setText(book.getTitle());
                if(!(book.getDescription() == null)) {
                    bookDescription.setText(Html.fromHtml(book.getDescription()));
                } else bookDescription.setText("No description available");
                categories.setText(book.getCategories());
                pageCount.setText(book.getPageCount() + " pages");
                bookAuthor.setText("by " + book.getAuthors());
                bookPublished.setText(book.getPublishedDate());


               // bookInfo.setText(book.toString());
                }
            });

        }
    }

    private void addBookToFavorites (Book book) {

        dataBaseService = new DataBaseService(getApplicationContext());
        Boolean addToFavorites = dataBaseService.saveToBooksLibraryDatabase(book);
        if(addToFavorites.booleanValue()) {
            Toast.makeText(this, "Added to favorites!", Toast.LENGTH_SHORT).show();
        } else { Toast.makeText(this, "Already in favorites!", Toast.LENGTH_SHORT).show();}
    }
}