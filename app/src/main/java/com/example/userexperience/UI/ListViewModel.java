package com.example.userexperience.UI;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.userexperience.models.PlaceDistanceComparator;
import com.example.userexperience.models.PlacesToBook;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.collections.CircleManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ListViewModel extends AndroidViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final CollectionReference colref = db.collection("Cities");
    List<DocumentSnapshot> docs = new ArrayList<>();
    private ArrayList<PlacesToBook> places = new ArrayList<>();
    private final MutableLiveData<ArrayList<PlacesToBook>> Selected = new MutableLiveData<>();
    private Context context;
    private GeoLocation center;
    private Location location;
    private double radiusinM;


    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public ArrayList<PlacesToBook> getDatalist(){
        return places;
    }

    public void init(Context context){
        this.context = context;
        colref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.w(TAG, "listen failed", error);
                    return;
                } else {
                    createData();
                }

            }
        });
    }

    public LiveData<ArrayList<PlacesToBook>> getSelected(){
        return Selected;
    }

    @SuppressLint("MissingPermission")
    public void createData(){

        docs.clear();
        places.clear();
        LocationManager locationManager = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,false));
        center = new GeoLocation(location.getLatitude(),location.getLongitude());
        radiusinM = 50*1000;

        List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center,radiusinM);
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        for(GeoQueryBounds b : bounds){
            Query q = db.collection("Cities").orderBy("geohash").startAt(b.startHash).endAt(b.endHash).limit(20);
            tasks.add(q.get());
        }
        Tasks.whenAllComplete(tasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {

            @Override
            public void onComplete(@NonNull Task<List<Task<?>>> t) {

                for( Task<QuerySnapshot> task : tasks){
                    QuerySnapshot snap = task.getResult();
                    for (DocumentSnapshot doc : snap.getDocuments()){
                        double lat = doc.getDouble("lat");
                        double lng = doc.getDouble("lng");
                        GeoLocation docLocation = new GeoLocation(lat,lng);
                        double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                        if (distanceInM <= radiusinM) {
                            docs.add(doc);

                        }
                    }
                }
                CreateArrayList();
            }
        });


    }
    void CreateArrayList(){
        for (DocumentSnapshot doc:docs) {
            PlacesToBook placesToBook = new PlacesToBook(doc.getString("desc"),doc.getString("geohash"),doc.getDouble("lat"),
                    doc.getDouble("lng"),doc.getString("price"),doc.getString("stradr"), doc.getString("url"),
                    doc.getString("title"));
            places.add(placesToBook);
        }

        for(int i = 0; i<places.size(); i++){
            GeoLocation userlocation = new GeoLocation(location.getLatitude(),location.getLongitude());
            GeoLocation doclocation = new GeoLocation(places.get(i).getLat(),places.get(i).getLng());
            double distancetoobject = GeoFireUtils.getDistanceBetween(doclocation,userlocation)/1000;
            places.get(i).setDistancetouser(distancetoobject);
        }
        sortArraylist();
        Selected.setValue(places);

    }

    private void sortArraylist(){
        Collections.sort(places, new PlaceDistanceComparator());
    }



}
