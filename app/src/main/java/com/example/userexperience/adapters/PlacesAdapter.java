package com.example.userexperience.adapters;

import android.content.Context;
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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PlacesAdapter extends FirestoreRecyclerAdapter<PlacesToBook, PlacesAdapter.PlaceHolder> {
    Context context;

    public PlacesAdapter(@NonNull FirestoreRecyclerOptions<PlacesToBook> options, Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull PlaceHolder holder, int position, @NonNull PlacesToBook model) {
        holder.adress.setText(model.getStradr());
        holder.title.setText(model.getTitle());
        holder.rating.setRating(1);
        Glide.with(context).load(model.getUrl()).into(holder.image);
    }


    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_item, viewGroup, false);
        return new PlaceHolder(v);
    }


    class PlaceHolder extends RecyclerView.ViewHolder {


        TextView title;
        ImageView image;
        TextView adress;
        TextView price;
        TextView distance;
        RatingBar rating;
        View view;

        public PlaceHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titletext);
            image = itemView.findViewById(R.id.listimage);
            adress = itemView.findViewById(R.id.adresstext);
            price = itemView.findViewById(R.id.pricetext);
            distance = itemView.findViewById(R.id.distancetext);
            rating = itemView.findViewById(R.id.ratingBar);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Klikket på item",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
