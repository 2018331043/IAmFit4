// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.iamfit;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import java.util.Random;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class FindDoctorTestActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor_test);
        //currentLocation=new Location()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        /*double temp=.008f;
        float lat=23.732257f,lang= 90.382186f;

        for(Integer i=1;i<=300;i++){
            Doctor temp2=new Doctor("01654994"+i,"Dummy Doctor"+i,"Dummy Speciality",lat,lang);
            Random rand=new Random();

            if(i%4==0){
                lat+=temp;
                lang-=temp;
            }
            else if(i%4==1){
                lang+=temp;
                lat-=temp;
            }
            else if(i%4==2){
                lang+=temp;
                lat+=temp;
            }
            else if(i%4==3){
                lang-=temp;
                lat-=temp;
            }
            FirebaseDatabase.getInstance().getReference("Doctors").child("Doctor"+i)
                    .setValue(temp2);
            temp= rand.nextDouble();
        }*/

        fetchLastLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            //ActivityCompat.requestPermissions(this,
                   // new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},REQUEST_CODE);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation=location;
                    //Toast.makeText(FindDoctorTestActivity.this, "Hi "+currentLocation.getLatitude()+" "+currentLocation.getLongitude() , Toast.LENGTH_LONG).show();
                    SupportMapFragment supportMapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById((R.id.map));
                    supportMapFragment.getMapAsync(FindDoctorTestActivity.this);
                }
            }
        });
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       LatLng latLng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        // Add a marker in Sydney and move the camera
        //LatLng latLng = new LatLng(23.777176, 90.399452);
       // MarkerOptions ma

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("User")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        FirebaseDatabase.getInstance().getReference("Doctors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    double lat= (double) snapshot.child("lat").getValue();
                    double longi=(double)snapshot.child("longi").getValue();
                   // String name2=snapshot.child("name").getValue().toString();
                    if(Math.sqrt(((lat-currentLocation.getLatitude())*(lat-currentLocation.getLatitude()))+((longi-currentLocation.getLongitude())*(longi-currentLocation.getLongitude())))<=.15){
                        LatLng latLng2=new LatLng(lat, longi);
                        //Toast.makeText(FindDoctorTestActivity.this, "Hi "+lat+" "+longi+" "+name2 , Toast.LENGTH_LONG).show();
                        //Toast.makeText(FindDoctorTestActivity.this, "Hi "+name2 , Toast.LENGTH_LONG).show();
                        String doc_info=snapshot.child("speciality").getValue().toString();//"\n Coontact Number"+snapshot.child("appointmentNumber").getValue().toString();
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng2)
                                .title(snapshot.child("name").getValue().toString())
                                .snippet(doc_info));
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLastLocation();
            }
        }
    }
}
