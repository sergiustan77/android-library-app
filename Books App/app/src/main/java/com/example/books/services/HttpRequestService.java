package com.example.books.services;

import android.content.Context;
import android.util.Log;

import com.example.books.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestService {
    private String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private String LATEST_URL = "https://www.googleapis.com/books/v1/volumes?q=orderBy=relevance";
    private String API_KEY = "AIzaSyCZ7vWLGVxyqM4Dafw4CoL21fttiP-9LSU";


    public ArrayList<Book> getAllBooks (Context context) {
       String recData= null;
        ArrayList<String> books = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(LATEST_URL + "&key=" + API_KEY + "&maxResults=20").build();

        try {
            Response response = client.newCall(req).execute();
            recData = response.body().string();

            
        } catch (IOException e) {
            e.printStackTrace();
        }


        return parseAllBooks(recData);
    }


    public Book getBookById( String volumeID) {


        return parseBook(volumeID);

    }

    private ArrayList<Book> parseAllBooks (String booksData) {
        ArrayList<Book> parsedBooks = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(booksData);
            JSONArray booksJSON = jsonObject.getJSONArray("items");
            for (int i = 0; i < booksJSON.length(); i++) {
                JSONObject singleBookJSON = booksJSON.getJSONObject(i);
               String volumeID = singleBookJSON.getString("id");
                parsedBooks.add(parseBook(volumeID));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return parsedBooks;
    }

    private Book parseBook(String id) {
        String recData= null;
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(BASE_URL + "/" + id).build();
        String bookTitle = null, bookPublisher =null, publishedDate = null, description = null, pageCount = null, categories = null, imageSmallThumbnail = null, imageSmallBookPage = null, language = null, previewLink = null, buyLink = null;
        String  bookAuthors = "";
        try {
            Response response = client.newCall(req).execute();
            recData = response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject bookJSON = new JSONObject(recData);
            JSONObject volumeInfo = bookJSON.getJSONObject("volumeInfo");
            JSONArray bookAuthorsArray = volumeInfo.getJSONArray("authors");
            Log.d("Autori", "parseBook: " + bookAuthorsArray);


            for (int i = 0; i < bookAuthorsArray.length(); i++) {
                Log.d("Autori", "parseBook: " + bookAuthorsArray.get(i).toString());
                bookAuthors += bookAuthorsArray.get(i).toString();
                if(i != bookAuthorsArray.length() - 1){
                bookAuthors += ", ";}

            }


            bookTitle = volumeInfo.getString("title");

            bookPublisher = volumeInfo.getString("publisher");
            publishedDate = volumeInfo.getString("publishedDate");
            description = volumeInfo.getString("description");
            pageCount = volumeInfo.getString("pageCount");
            categories = volumeInfo.getString("categories");
            imageSmallThumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
            imageSmallBookPage = volumeInfo.getJSONObject("imageLinks").getString("large");
            language = volumeInfo.getString("language");
            previewLink = volumeInfo.getString("previewLink");
            buyLink = bookJSON.getJSONObject("saleInfo").getString("buyLink");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new Book(id, bookTitle, bookAuthors, bookPublisher, publishedDate, description, pageCount, categories, imageSmallThumbnail, imageSmallBookPage, language, previewLink, buyLink);
    }

    public ArrayList<Book> getBooksBySearch (Context context, String query) {
        String recData= null;
        ArrayList<String> books = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(BASE_URL + "?q=" + query + "&key=" + API_KEY + "&maxResults=20").build();

        try {
            Response response = client.newCall(req).execute();
            recData = response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }


        return parseAllBooks(recData);
    }



}
