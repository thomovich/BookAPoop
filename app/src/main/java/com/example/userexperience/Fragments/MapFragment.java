package com.example.userexperience.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.userexperience.R;
import com.example.userexperience.UI.ListViewModel;
import com.example.userexperience.models.PlacesToBook;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {
    private GoogleMap googleMap;
    private ListViewModel model;
    MapView mMapView;
    private ArrayList<PlacesToBook> places = new ArrayList<>();
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        places = model.getDatalist();
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED  ){
                    requestPermissions(new String[]{
                                    android.Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    return ;
                }

                googleMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,false));
                if(location == null){
                    LatLng Arhus = new LatLng(56.162937, 10.203921);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(Arhus).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    System.out.println("my position is null");
                } else {
                    LatLng me = new LatLng(location.getLatitude(),location.getLongitude());
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(me).zoom(12).build();
                    googleMap.addMarker(new MarkerOptions().position(me).title("you are here"));
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

                for(int i = 0; i<places.size(); i++ ){
                    LatLng marker = new LatLng(places.get(i).getLat(),places.get(i).getLng());
                    googleMap.addMarker(new MarkerOptions()
                            .position(marker)
                            .title(places.get(i).getTitle())
                            .snippet(places.get(i).getPrice() + " kr")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.poopemoji)));
                }





            }
        });


        return rootView;
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        marker.showInfoWindow();
        return true;
    }
}
