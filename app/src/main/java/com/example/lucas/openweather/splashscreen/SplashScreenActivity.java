package com.example.lucas.openweather.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lucas.openweather.R;

import com.example.lucas.openweather.model.Feed;
import com.example.lucas.openweather.mainactivity.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class SplashScreenActivity extends AppCompatActivity implements SplashScreenViewInterface {

    private FusedLocationProviderClient client;
    private Context mContext;

    SplashPresenter splashPresenter;
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mContext = SplashScreenActivity.this;
        client = LocationServices.getFusedLocationProviderClient(mContext);


        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        client.getLastLocation().addOnSuccessListener((Activity) mContext, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    String latitude = String.valueOf(location.getLatitude());
                    String longitude = String.valueOf(location.getLongitude());
                    setupMVP(latitude, longitude);
                    getWeather();
                }
            }
        });

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    client.getLastLocation().addOnSuccessListener((Activity) mContext, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                String latitude = String.valueOf(location.getLatitude());
                                String longitude = String.valueOf(location.getLongitude());
                                setupMVP(latitude, longitude);
                                getWeather();

                            }
                        }
                    });

                } else {
                    finish();
                }
                return;
            }
        }
    }

    private void setupMVP(String latitude, String longitude) {
        splashPresenter = new SplashPresenter(this, latitude, longitude);
    }


    private void getWeather() {

        splashPresenter.getWeather();

    }

    @Override
    public void displayError(String s) {
        Toast.makeText(SplashScreenActivity.this, s, Toast.LENGTH_LONG).show();

    }

    @Override
    public void startMainActivity(Feed feed) {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("feed", feed);
        mContext.startActivity(intent);
        ((Activity) mContext).finish();
    }

}
