package com.arctouch.floripabus.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.arctouch.floripabus.R;
import com.arctouch.floripabus.tasks.SearchStreetNameTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;


public class GoogleMapActivity extends Activity implements Receiver<String> {

    private GoogleMap map;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        configureMap();

        Toast.makeText(this, "Longclick in a street to search", Toast.LENGTH_LONG).show();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (firstTime) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                    firstTime = false;
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        centerMapOnMyLocation();
    }

    private void configureMap() {

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                executeGetStreetNameTask(latLng);
            }
        });

    }

    private void centerMapOnMyLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (location != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(16)
                    .build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    public void executeGetStreetNameTask(LatLng latLng) {
        new SearchStreetNameTask(this).execute(latLng);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onReceive(String streetName) {
        if (streetName.isEmpty()) {
            Toast.makeText(this, "Sorry! We do not find the selected street!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("streetName", streetName);
        setResult(RESULT_OK, intent);
        finish();
    }
}
