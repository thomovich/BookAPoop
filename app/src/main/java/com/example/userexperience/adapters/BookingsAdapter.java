package com.example.userexperience.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userexperience.R;
import com.example.userexperience.models.PlacesToBook;

import java.util.ArrayList;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder>{
    private ArrayList<PlacesToBook> places;
    final private OnListItemClickListener mOnListItemClickListener;

    public BookingsAdapter(ArrayList<PlacesToBook> places, OnListItemClickListener listener) {
        this.places = places;
        mOnListItemClickListener = listener;
    }

    @NonNull
    @Override
    public BookingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.place_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BookingsAdapter.ViewHolder holder, int position) {
        holder.title.setText(places.get(position).getTitle());
        holder.image.setImageResource(R.drawable.toiletseat);
        holder.adress.setText(places.get(position).getAdress());
        holder.price.setText(String.valueOf(places.get(position).getPrice()));
        holder.distance.setText(String.valueOf(places.get(position).getDistance()));
        holder.rating.setRating(places.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView image;
        TextView adress;
        TextView price;
        TextView distance;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titletext);
            image = itemView.findViewById(R.id.listimage);
            adress = itemView.findViewById(R.id.adresstext);
            price = itemView.findViewById(R.id.pricetext);
            distance = itemView.findViewById(R.id.distancetext);
            rating = itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onListItemClick(getAdapterPosition());
        }

    }

    public interface  OnListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }


}

