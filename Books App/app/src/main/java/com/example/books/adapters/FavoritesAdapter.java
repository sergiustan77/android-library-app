package com.example.books.adapters;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.books.R;
import com.example.books.activities.BookActivity;
import com.example.books.model.Book;
import com.example.books.services.DataBaseService;
import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.AllBooksAdapter>{
    Context context;
    ArrayList<Book> books;

    public FavoritesAdapter (Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public FavoritesAdapter.AllBooksAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout & design rows

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.favorite_card,  parent,  false);

        return new FavoritesAdapter.AllBooksAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.AllBooksAdapter holder, int position) {
        String volumeID = books.get(position).getVolumeID();
        holder.bookTitle.setText(books.get(position).getTitle());
        String buyLink = books.get(position).getBuyLink();
        String previewLink = books.get(position).getPreviewLink();


        holder.seeDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetails(volumeID);
                Log.d("VOLUMEID", "onClick: " + volumeID);
            }
        });

        holder.goToBuyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("IN BUY", "onClick: " + previewLink);
                goToBuyPage(previewLink);
            }
        });

        holder.removeBookFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseService dataBaseService = new DataBaseService(context);
                dataBaseService.removeFromFavorites(books.get(position));
                books.remove(books.get(position));
                notifyDataSetChanged();
                Toast.makeText(context, "Removed from favorites!", Toast.LENGTH_LONG).show();

            }
        });

        Glide.with(holder.bookImage).load(books.get(position).getImageSmallThumbnail()).placeholder(R.drawable.ic_baseline_book_24).into(holder.bookImage);


    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class AllBooksAdapter extends RecyclerView.ViewHolder {

        ImageView bookImage;
        TextView bookTitle;
        MaterialButton seeDetailsButton;
        MaterialButton goToBuyPage;
        TextView cardID;
        Button removeBookFromFavorites;


        public AllBooksAdapter(@NonNull View itemView) {
            super(itemView);
            removeBookFromFavorites = itemView.findViewById(R.id.buttonRemoveFromFavorites);
            cardID = itemView.findViewById(R.id.cardID);
            seeDetailsButton = itemView.findViewById(R.id.buttonSeeDetails);
            goToBuyPage = itemView.findViewById(R.id.goToBuyPage);
            bookImage = itemView.findViewById(R.id.bookImage);
            bookTitle = itemView.findViewById(R.id.bookTitle);
        }


    }

    private void goToDetails( String volume) {
        Intent bookDetails = new Intent(context, BookActivity.class);
        bookDetails.putExtra("context", context.getClass().getName());
        bookDetails.putExtra("volumeID", volume);
        context.startActivity(bookDetails);


    }

    private void goToBuyPage ( String previewLink) {
        Uri uri = Uri.parse("");
        String url = previewLink;
        if(url != null){
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                url  = "http://" + url ;}

            uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);}
        else {
            if(url == null) {
                Toast.makeText(context, "This book does not have a buy page!", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
