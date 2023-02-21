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

public class RequestsService {
    private String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private String LATEST_URL = "https://www.googleapis.com/books/v1/volumes?q=subject:fiction";
    private String API_KEY = "AIzaSyCZ7vWLGVxyqM4Dafw4CoL21fttiP-9LSU";


    public ArrayList<Book> getAllBooks(Context context) {
        String recData = null;
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


    public Book getBookById(String volumeID) {


        return parseBookPage(volumeID);

    }

    private ArrayList<Book> parseAllBooks(String booksData) {
        ArrayList<Book> parsedBooks = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(booksData);
            JSONArray booksJSON = jsonObject.getJSONArray("items");
            for (int i = 0; i < booksJSON.length(); i++) {
                JSONObject singleBookJSON = booksJSON.getJSONObject(i);
                parsedBooks.add(parseBook(singleBookJSON));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("PARSED BOOKS", "parseAllBooks: " + parsedBooks.toString());
        return parsedBooks;
    }

    private Book parseBook(JSONObject bookData) {
        String authors = "", categories = "";
        String id = null, title = null, publisher = null, publishedDate = null, description = null, pageCount = null, language = null, averageRating = null, imageSmallThumbnail = null, previewLink = null, buyLink = null;
        JSONObject volumeInfo = null;
        try {
            volumeInfo = bookData.getJSONObject("volumeInfo");
            id = bookData.getString("id");
            title = volumeInfo.getString("title");


            if (volumeInfo.has("imageLinks") && !volumeInfo.isNull("imageLinks") && volumeInfo.getJSONObject("imageLinks").has("thumbnail") && !(volumeInfo.getJSONObject("imageLinks").getString("thumbnail") == null)) {
                imageSmallThumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
            }

            if (volumeInfo.has("buyLink") && !volumeInfo.isNull("buyLink")) {
                buyLink = bookData.getJSONObject("saleInfo").getString("buyLink");
            }

            if (volumeInfo.has("previewLink") && !volumeInfo.isNull("previewLink")) {
                previewLink = volumeInfo.getString("previewLink");
            }

            if (volumeInfo.has("averageRating") && !volumeInfo.isNull("averageRating")) {
                averageRating = volumeInfo.getString("averageRating");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("BOOKS", "parseSingleBook: " + title + " " + categories + " " + id);
        return new Book(id, title, authors, publisher, publishedDate, description, pageCount, categories, imageSmallThumbnail, imageSmallThumbnail, language, previewLink, buyLink, averageRating);
    }

    private Book parseBookPage(String id) {
        String recData = null;
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(BASE_URL + "/" + id).build();
        String bookTitle = null, bookPublisher = null, publishedDate = null, description = null, pageCount = null, categories = "", imageSmallThumbnail = null, imageSmallBookPage = null, language = null, previewLink = null, buyLink = null;
        String bookAuthors = "";
        String averageRating = null;
        try {
            Response response = client.newCall(req).execute();
            recData = response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject bookJSON = new JSONObject(recData);
            JSONObject volumeInfo = bookJSON.getJSONObject("volumeInfo");
            JSONArray bookAuthorsArray = null;
            JSONArray categoriesArray = null;


            bookTitle = volumeInfo.getString("title");




            if (volumeInfo.has("publisher") && !volumeInfo.isNull("publisher")) {
                bookPublisher = volumeInfo.getString("publisher");
            }

            if (volumeInfo.has("pageCount") && !volumeInfo.isNull("pageCount")) {
                pageCount = volumeInfo.getString("pageCount");
            }

            if (volumeInfo.has("language") && !volumeInfo.isNull("language")) {
                language = volumeInfo.getString("language");
            }


            if (volumeInfo.has("description") && !volumeInfo.isNull("description")) {
                description = volumeInfo.getString("description");
            }

            if (volumeInfo.has("publishedDate") && !volumeInfo.isNull("publishedDate")) {
                publishedDate = volumeInfo.getString("publishedDate");
            }

            if (volumeInfo.has("imageLinks") && !volumeInfo.isNull("imageLinks") && volumeInfo.getJSONObject("imageLinks").has("thumbnail") && !(volumeInfo.getJSONObject("imageLinks").getString("thumbnail") == null)) {
                imageSmallThumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
            }

            if (volumeInfo.has("buyLink") && !volumeInfo.isNull("buyLink")) {
                buyLink = bookJSON.getJSONObject("saleInfo").getString("buyLink");
            }

            if (volumeInfo.has("previewLink") && !volumeInfo.isNull("previewLink")) {
                previewLink = volumeInfo.getString("previewLink");
            }

            if (volumeInfo.has("averageRating") && !volumeInfo.isNull("averageRating")) {
                averageRating = volumeInfo.getString("averageRating");
            }

            if (volumeInfo.has("categories") && !volumeInfo.isNull("categories")) {

                categoriesArray = volumeInfo.getJSONArray("categories");
                for (int i = 0; i < categoriesArray.length(); i++) {

                    categories += categoriesArray.get(i).toString();
                    if (i != categoriesArray.length() - 1) {
                        categories += ", ";
                    }

                }
            }


            if (volumeInfo.has("authors") && !volumeInfo.isNull("authors")) {

                bookAuthorsArray = volumeInfo.getJSONArray("authors");

                for (int i = 0; i < bookAuthorsArray.length(); i++) {

                    bookAuthors += bookAuthorsArray.get(i).toString();
                    if (i != bookAuthorsArray.length() - 1) {
                        bookAuthors += ", ";
                    }

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new Book(id, bookTitle, bookAuthors, bookPublisher, publishedDate, description, pageCount, categories, imageSmallThumbnail, imageSmallBookPage, language, previewLink, buyLink, averageRating);
    }

    public ArrayList<Book> getBooksBySearch(Context context, String query) {
        String recData = null;
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
