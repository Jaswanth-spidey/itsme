package com.example.addingtrafficlayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ToggleButton normalview;
    ToggleButton satelliteview;
    LocationManager manager;
    double lati, longi;
    EditText mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        normalview = findViewById(R.id.nomarlview);
        satelliteview = findViewById(R.id.satelliteview);
        mSearch = findViewById(R.id.search_text_input);

        final String[] textFromauto = new String[1];


        satelliteview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });


        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                ;
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }
        };
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1, listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, listener);
        innit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                ;
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }
        };


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
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                ;
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }
        };


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
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*10000, 1, listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5*10000, 1, listener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                ;
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }
        };


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
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*10000, 1, listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5*10000, 1, listener);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    public void innit() {
        Log.d("THERESULT", "initializing");
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    try {
                        geoLocate(mSearch.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });

    }

    public void geoLocate(String name) throws IOException {
        Log.d("THERESULT", "geolocating...");
        String searchText = name;

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocationName(searchText, 1);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("THERESULT", "geoLocating: IOException" + e.getMessage());
        }

        if (addresses.size() > 0) {
            getKummuko(addresses);
        }

        if(addresses.size() == 0){
            Toast.makeText(this, "Sorry we don't have that location at present...", Toast.LENGTH_SHORT).show();
        }
    }

    public void getKummuko(List<Address> addresser){
        Address address = addresser.get(0);
        double latitu = address.getLatitude();
        double longitu = address.getLongitude();
        LatLng kassydney = new LatLng(latitu,longitu);
        Log.d("THERESULT", "THE Location found: " + address);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(kassydney).title("Your Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kassydney,12.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        normalview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    googleMap.setTrafficEnabled(true);
                } else {
                    googleMap.setTrafficEnabled(false);
                }
            }
        });
        // Add a marker in Sydney and move the
        LatLng sydney = new LatLng(lati, longi);
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}