package com.example.covid_19api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    String latitudeeer, longitudeeer;
    TextView loading_textview,date_textview,time_textview;
    ProgressBar progressBaras;
    ScrollView scrollViewddd;
    LinearLayout ddate_laodm;
    LocationRequest locationRequest;

    RecyclerView district_level_recycler,state_level_recycler,country_level_recycler;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null){
                return;
            }
            for (Location location: locationResult.getLocations()){
                Log.i("LOCAT", ""+location.toString());
                Log.i("ITSLATI",""+location.getLatitude());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        district_level_recycler = findViewById(R.id.districtlevelrecycler);
        state_level_recycler = findViewById(R.id.statelevelrecycler);
        country_level_recycler = findViewById(R.id.countrylevelrecycler);
        scrollViewddd = findViewById(R.id.scrollHuks);
        ddate_laodm = findViewById(R.id.datr_linear_layout);
        date_textview = findViewById(R.id.date_textview);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        time_textview = findViewById(R.id.time_textview);
        date_textview.setText(currentDate);
        loading_textview = findViewById(R.id.loading_textview);
        progressBaras = findViewById(R.id.progressingbarr);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getDeviceLocation();

        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsandUpdateLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void checkSettingsandUpdateLocation() {

        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
                .build();

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(MainActivity.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void getDeviceLocation()
    {

        scrollViewddd.setVisibility(View.GONE);
        progressBaras.setVisibility(View.VISIBLE);
        loading_textview.setVisibility(View.VISIBLE);
        ddate_laodm.setVisibility(View.GONE);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());


                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    latitudeeer = String.valueOf(addresses.get(0).getSubAdminArea());
                    longitudeeer = String.valueOf(addresses.get(0).getAdminArea());
                    if (latitudeeer == null || longitudeeer == null){
                        Toast.makeText(MainActivity.this, "latitudeer or longitudeneer in main activity is null", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        District_async districtAsync = new District_async(MainActivity.this, district_level_recycler, latitudeeer,longitudeeer);
                        districtAsync.execute();
                        state_async state_asyncaa = new state_async(MainActivity.this,state_level_recycler,longitudeeer);
                        state_asyncaa.execute();
                        country_async country_asynsd = new country_async(MainActivity.this,country_level_recycler);
                        country_asynsd.execute();
                    }
                    scrollViewddd.setVisibility(View.VISIBLE);
                    loading_textview.setVisibility(View.GONE);
                    progressBaras.setVisibility(View.GONE);
                    ddate_laodm.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("HEEEXCEPTION",""+e.getMessage());
                    Toast.makeText(MainActivity.this, "Please turn on your location and network if done wait for 5 sec", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "We are colloecting your location the timer starts now watch here", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "5", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "If still the app is not loading plaese restart the app", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "If still the app is not starting there is problem in your network", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}