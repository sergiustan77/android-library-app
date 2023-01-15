package com.example.books;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.books.model.Book;
import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;


public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.AllBooksAdapter>{
    Context context;
    ArrayList<Book> books;

    public BooksAdapter (Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public BooksAdapter.AllBooksAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout & design rows

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.book_card_row,  parent,  false);

        return new BooksAdapter.AllBooksAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.AllBooksAdapter holder, int position) {
        String volumeID = books.get(position).getVolumeID();
        holder.bookTitle.setText(books.get(position).getTitle());
        holder.seeDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetails(volumeID);
                Log.d("VOLUMEID", "onClick: " + volumeID);
            }
        });
       // holder.buyButton.setOnClickListener(goToBuyPage());

        Log.d("IMAGE", "onBindViewHolder: " + books.get(position).getImageSmallBookPage());
       Glide.with(holder.bookImage).load(books.get(position).getImageSmallThumbnail()).placeholder(R.drawable.hearticon).into(holder.bookImage);


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
        public AllBooksAdapter(@NonNull View itemView) {
            super(itemView);

            cardID = itemView.findViewById(R.id.cardID);
            seeDetailsButton = itemView.findViewById(R.id.buttonSeeDetails);
            buyButton = itemView.findViewById(R.id.buttonBuy);
            bookImage = itemView.findViewById(R.id.bookImage);
            bookTitle = itemView.findViewById(R.id.bookTitle);
        }


    }

    private void goToDetails( String volume) {
        Intent bookDetails = new Intent(context.getApplicationContext(), BookActivity.class);
        bookDetails.putExtra("volumeID", volume);
        context.startActivity(bookDetails);


    }
}
