package com.example.userexperience.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.userexperience.R;
import com.example.userexperience.UI.MainActivity;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CreateNewRentingFragment extends Fragment {
    EditText title;
    EditText description;
    EditText address;
    EditText price;
    EditText zipcode;
    EditText country;
    ImageView backimage;
    String downloaduri;
    Button create;
    Button choose;
    FirebaseStorage storage;
    UploadTask uploadTask;
    StorageReference storageReference;
    private Uri filePath;
    private ImageView imagepreview;
    private final int PICK_IMAGE_REQUEST = 22;


    public CreateNewRentingFragment(){
        super(R.layout.newrenting_fragment);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.newrenting_fragment, container, false);
        title = view.findViewById(R.id.title_edittext);
        description = view.findViewById(R.id.description_edittext);
        address = view.findViewById(R.id.address_edittext);
        price = view.findViewById(R.id.price_edittext);
        create = view.findViewById(R.id.createbookingbutton);
        zipcode = view.findViewById(R.id.zipcode_edittext);
        country = view.findViewById(R.id.country_edittext);
        choose = view.findViewById(R.id.chooseimagebutton);
        imagepreview = view.findViewById(R.id.imagepreview);
        backimage = view.findViewById(R.id.backimage);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();




        //Firebase storage

        create.setOnClickListener(v->{
            UploadImage();

        });



        choose.setOnClickListener(v->{
            SelectImage();
        });



        return view;
    }

    private void UploadImage() {
        if(filePath != null){
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("uploading");
            progressDialog.show();

           StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
           uploadTask = ref.putFile(filePath);

           Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
               @Override
               public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                   if(!task.isSuccessful()){
                       throw task.getException();
                   }
                   return ref.getDownloadUrl();
               }
           }).addOnCompleteListener(new OnCompleteListener<Uri>() {
               @Override
               public void onComplete(@NonNull Task<Uri> task) {
                   if(task.isSuccessful()){
                       downloaduri = task.getResult().toString();
                       UploadToDatabase();
                   } else{

                   }
               }
           });
           uploadTask.addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(
                                UploadTask.TaskSnapshot taskSnapshot)
                        {
                            double progress
                                    = (100.0
                                    * taskSnapshot.getBytesTransferred()
                                    / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(
                                    "Uploaded "
                                            + (int)progress + "%");
                        }

                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                   progressDialog.dismiss();
               }
           });


        }
    }


    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select image from here"),PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == MainActivity.RESULT_OK && data !=null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContext().getContentResolver(),
                                filePath);
                imagepreview.setImageBitmap(bitmap);

            } catch (Exception e){
                e.printStackTrace();
            }
        }


    }


    private void UploadToDatabase(){
        if(title.getText() != null && description.getText() != null && address.getText() != null && price.getText() !=null && zipcode.getText() !=null && country.getText() !=null){
            String strAddress = address.getText() + "," + zipcode.getText() + "," + country.getText();
            int Price = Integer.valueOf(price.getText().toString());
            LatLng latLng = CreateLatlng(strAddress);
            String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(latLng.latitude,latLng.longitude));
            Map<String, Object> docdata = new HashMap<>();
            docdata.put("geohash",hash);
            docdata.put("lat", latLng.latitude);
            docdata.put("lng", latLng.longitude);
            docdata.put("url", downloaduri);
            docdata.put("desc", description.getText().toString());
            docdata.put("price", price.getText().toString());
            docdata.put("stradr", strAddress);
            docdata.put("title", title.getText().toString());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Cities").add(docdata).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    title.setText("");
                    description.setText("");
                    address.setText("");
                    price.setText("");
                    zipcode.setText("");
                    country.setText("");
                    imagepreview.setImageResource(R.drawable.toiletseat);

                }
            });

        } else {
            Toast.makeText(getContext(), "All fields have to be filled out", Toast.LENGTH_SHORT).show();
        }
    }

    public LatLng CreateLatlng(String address){
        Geocoder coder = new Geocoder(getContext());
        List<Address> addresses;
        LatLng latlng = null;
        try{
            addresses = coder.getFromLocationName(address,5);
            if(addresses ==null){
                return null;
            }
            Address location = addresses.get(0);
            location.getLatitude();
            location.getLongitude();

            latlng = new LatLng(location.getLatitude(),location.getLongitude());
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(latlng + "");
       return latlng;
    }


}
