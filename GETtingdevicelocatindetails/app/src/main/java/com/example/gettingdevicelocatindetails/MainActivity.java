package com.example.gettingdevicelocatindetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView locdetails;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locdetails = findViewById(R.id.textview44);
        client = LocationServices.getFusedLocationProviderClient(this);
    }

    public void getLocation(View view) {
        getDeviceDetails();
    }

    private void getDeviceDetails() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,

                Manifest.permission.ACCESS_FINE_LOCATION) ==

                PackageManager.PERMISSION_GRANTED) {

            getDeviceDetails();


        } else {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                try {
                    List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    String latitude = String.valueOf(list.get(0).getLatitude());
                    String longitude = String.valueOf(list.get(0).getLongitude());
                    String countryname = String.valueOf(list.get(0).getCountryName());
                    String locality = String.valueOf(list.get(0).getLocality());
                    String adrressline = String.valueOf(list.get(0).getAddressLine(0));
                    String postalcode = String.valueOf(list.get(0).getPostalCode());
                    locdetails.setText(latitude + "\n" + longitude + "\n" + countryname + "\n" + locality + "\n" + adrressline + "\n" + postalcode + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}