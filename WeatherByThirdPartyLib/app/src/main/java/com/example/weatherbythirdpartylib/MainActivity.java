package com.example.weatherbythirdpartylib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.common.Main;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView city1122, tempeee112;
    String temop1222;
    Button getweatehree;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    List<Address> addresses;

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
        city1122 = findViewById(R.id.city223);
        tempeee112 = findViewById(R.id.temperature112);
        getweatehree = findViewById(R.id.getweather);
        progressBar = findViewById(R.id.progressCaluculator);
        linearLayout = findViewById(R.id.linearLayout2);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getweatehree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeather();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsandUpdateLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

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

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
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

    private void getWeather() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 44);
                        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.api_key));
                        progressBar.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        helper.setUnits(Units.METRIC);
                        helper.setLang(Lang.ENGLISH);
                        helper.getCurrentWeatherByGeoCoordinates(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), new CurrentWeatherCallback() {
                            @Override
                            public void onSuccess(CurrentWeather currentWeather) {
                                progressBar.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);

                                Log.v("THROWINGERRO", "Coordinates: " + currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon() +"\n"
                                        +"Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                                        +"Temperature: " + currentWeather.getMain().getTempMax()+"\n"
                                        +"Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                                        +"City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                                );
                                city1122.setText(currentWeather.getName());
                                temop1222 = String.valueOf(currentWeather.getMain().getTemp());
                                tempeee112.setText(temop1222);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Toast.makeText(MainActivity.this, "The Failure reason: "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.v("THROWINGERRO","The failure reason: "+throwable.getMessage());



                            }
                        });
                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, "The error in loading is: "+e, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        Log.v("THROWINGERRO", "The exception reason"+e);
                    }
                }
                else if (location == null) {
                    Toast.makeText(MainActivity.this, "There is a problem in fetching the location", Toast.LENGTH_SHORT).show();
                    Log.i("LOCAT",""+location);
                }
            }
        });
    }

}