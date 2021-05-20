package com.example.userexperience.Fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.userexperience.R;
import com.example.userexperience.UI.ListViewModel;
import com.example.userexperience.UI.SharedViewModel;
import com.example.userexperience.adapters.PlacesAdapter2;
import com.example.userexperience.models.PlacesToBook;


import java.util.ArrayList;


public class FireListFragment extends Fragment{
    RecyclerView recyclerView;
    private PlacesAdapter2 myAdapter;
    private ListViewModel model;
    private SharedViewModel viewModel;
    private ArrayList<PlacesToBook> places = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment,container,false);
        model = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);



        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createlist(view);

        model.getSelected().observe(getViewLifecycleOwner(), placesToBookArrayList ->{
            places = placesToBookArrayList;
            createlist(view);
        });
        super.onViewCreated(view, savedInstanceState);
    }


    void createlist(View view){
        myAdapter = new PlacesAdapter2(places, getContext());
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new PlacesAdapter2.ClickListener() {
            @Override
            public void onListitemClick(int position, View v) {
                openFragment();
                viewModel.select(places.get(position));
            }
        });

    }

    private void openFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, DetailedViewFragment.class, null).commit();
    }




}
