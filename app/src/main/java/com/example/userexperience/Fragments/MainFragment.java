package com.example.userexperience.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.userexperience.R;
import com.example.userexperience.models.MainFragmentPost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainFragment extends Fragment {
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
    TextView title;
    TextView article;
    ImageView image;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        title = view.findViewById(R.id.TitleTextView);
        article = view.findViewById(R.id.ContentTextview);
        image = view.findViewById(R.id.starImage);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MainFragmentPost post = snapshot.getValue(MainFragmentPost.class);
                title.setText(post.getTitle());
                article.setText(post.getContent());
                if(getContext() != null){
                    Glide.with(getContext()).load(post.getImgurl()).into(image);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myref.addValueEventListener(postListener);
        return view;
    }


}
