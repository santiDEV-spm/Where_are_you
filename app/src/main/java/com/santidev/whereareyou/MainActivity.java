package com.santidev.whereareyou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.location.LocationListenerCompat;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListenerCompat {

    private TextView txtLat, txtLong, txtSrc;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLat = (TextView) findViewById(R.id.txtLatitud);
        txtLong = (TextView) findViewById(R.id.txtLonguitud);
        txtSrc = (TextView) findViewById(R.id.txtSrc);

        //inicializar el manager que nos dara la posicion del gps
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        Location location = locationManager.getLastKnownLocation(provider);
        if(location!=null){
            txtSrc.setText("Source = " + provider);
            onLocationChanged(location);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 500, 1, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        //double alt = location.getAltitude();

        txtLat.setText(String.valueOf(lat));
        txtLong.setText(String.valueOf(lng));
        txtSrc.setText("Source = "+ provider);
    }

    @Override
    public void onStatusChanged(@NonNull String provider, int status, @Nullable Bundle extras) {
        txtSrc.setText("Source = "+ provider);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        txtSrc.setText("Source = "+ provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        txtSrc.setText("Source = "+ provider);
    }
}