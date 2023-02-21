package com.example.books.adapters;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.books.R;
import com.example.books.activities.BookActivity;
import com.example.books.activities.SearchActivity;
import com.example.books.model.Book;
import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;


public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.AllBooksAdapter>{
    Context context;
    ArrayList<Book> books;
    static Context mainContext = null;

    public BooksAdapter (Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public BooksAdapter.AllBooksAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout & design rows

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.book_card,  parent,  false);

        return new BooksAdapter.AllBooksAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.AllBooksAdapter holder, int position) {
        String volumeID = books.get(position).getVolumeID();
        String buyLink = books.get(position).getBuyLink();
        String previewLink = books.get(position).getPreviewLink();
        String rating = books.get(position).getAverageRating();
        if(rating != null) {
            holder.rating.setText(rating);
            if(Float.parseFloat(rating) >= 4) {
                holder.rating.setTextSize(24);
                holder.rating.setTextColor(Color.parseColor("#588157"));
            } else if(Float.parseFloat(rating) >=3 ) {
                holder.rating.setTextSize(24);
                holder.rating.setTextColor(Color.parseColor("#e9c46a"));
            } else {holder.rating.setTextSize(24);
                holder.rating.setTextColor(Color.parseColor("#d00000"));};
        } else {

            holder.rating.setText("N/A");
            holder.rating.setTextColor(Color.parseColor("#415a77"));
            holder.rating.setTextSize(16);
        };
        holder.bookTitle.setText(books.get(position).getTitle());
        holder.seeDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetails(volumeID);
                //Log.d("VOLUMEID", "onClick: " + volumeID);
            }
        });

        holder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBuyPage(buyLink, previewLink);
            }
        });


        //Log.d("IMAGE", "onBindViewHolder: " + books.get(position).getImageSmallBookPage());
       Glide.with(holder.bookImage).load(books.get(position).getImageSmallThumbnail()).placeholder(R.drawable.ic_baseline_photo_24).into(holder.bookImage);


    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class AllBooksAdapter extends RecyclerView.ViewHolder {

        ImageView bookImage;
        TextView bookTitle;
        MaterialButton seeDetailsButton;
        MaterialButton buyButton;
        TextView cardID;
        TextView rating;
        public AllBooksAdapter(@NonNull View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.ratingText);
            cardID = itemView.findViewById(R.id.cardID);
            seeDetailsButton = itemView.findViewById(R.id.buttonSeeDetails);
            buyButton = itemView.findViewById(R.id.buttonBuy);
            bookImage = itemView.findViewById(R.id.bookImage);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            mainContext = itemView.getContext();
        }


    }

    private void goToDetails( String volume) {
        Intent bookDetails = new Intent(context, BookActivity.class);
        bookDetails.putExtra("volumeID", volume);
        bookDetails.putExtra("context", context.getClass().getName());
        context.startActivity(bookDetails);


    }

    private void goToBuyPage (String buyLink, String previewLink) {
        Uri uri = Uri.parse("");
        String url = buyLink;
        if(url != null){
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                url  = "http://" + url ;}

            uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);}
        else {
            url = previewLink;
            if(url != null){
                if (!url.startsWith("http://") && !url.startsWith("https://")){
                    url  = "http://" + url ;}
                uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);}
            else if(url == null) {
                Toast.makeText(context, "This book does not have a buy page!", Toast.LENGTH_SHORT).show();
            }

        }

    }


}
