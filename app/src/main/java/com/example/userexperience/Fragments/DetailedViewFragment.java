package com.example.userexperience.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.userexperience.R;
import com.example.userexperience.UI.SharedViewModel;
import com.example.userexperience.models.PlacesToBook;

import java.util.ArrayList;

public class DetailedViewFragment extends Fragment {

    private  TextView title;
    private TextView price;
    private ImageView bigimg;
    private TextView desc;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailedlistfragment,container,false);
        title = view.findViewById(R.id.TitleTextView);
        price = view.findViewById(R.id.Pricetext);
        bigimg = view.findViewById(R.id.BigImage);
        desc = view.findViewById(R.id.description);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        model.getSelected().observe(getViewLifecycleOwner(), PlacesToBook ->{
            title.setText(PlacesToBook.getTitle());
            price.setText(PlacesToBook.getPrice() + " Kr");
            desc.setText(PlacesToBook.getDesc());
            Glide.with(getContext()).load(PlacesToBook.getUrl()).into(bigimg);
        });
    }


}
