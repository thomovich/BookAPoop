package com.example.userexperience.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.userexperience.R;
import com.example.userexperience.models.PlacesToBook;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacesAdapter2 extends RecyclerView.Adapter<PlacesAdapter2.ViewHolder> {
private ArrayList<PlacesToBook> places = new ArrayList<>();

private static ClickListener clickListener;
Context context;

    public PlacesAdapter2(ArrayList<PlacesToBook> places, Context context) {
        this.context = context;

        this.places = places;
        System.out.println("added to adapter");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        System.out.println(getItemCount() + "");
        holder.adress.setText(places.get(position).getStradr());
        holder.title.setText(places.get(position).getTitle());
        holder.rating.setRating(1);
        holder.price.setText(places.get(position).getPrice() + "kr");
        Glide.with(context).load(places.get(position).getUrl()).into(holder.image);
        double distancetouser = places.get(position).getDistancetouser();
        String dist = String.format("%.2f", distancetouser);
        holder.distance.setText(dist + "km");
        if(position % 2 == 0){
            holder.view.setBackgroundColor(Color.CYAN);
        }


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
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titletext);
            image = itemView.findViewById(R.id.listimage);
            adress = itemView.findViewById(R.id.adresstext);
            price = itemView.findViewById(R.id.pricetext);
            distance = itemView.findViewById(R.id.distancetext);
            rating = itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(this::onClick);
            view = itemView;

        }

        @Override
        public void onClick(View v) {
            clickListener.onListitemClick(getAdapterPosition(),v);
        }
    }

    public interface ClickListener {
       public void onListitemClick(int position, View v);

    }

    public void setOnItemClickListener(ClickListener clickListener){
        PlacesAdapter2.clickListener = clickListener;
    }
}
