package com.example.lucas.openweather.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lucas.openweather.R;

import com.example.lucas.openweather.model.Feed;
import com.example.lucas.openweather.mainactivity.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenViewInterface {

    private FusedLocationProviderClient client;
    private Context mContext;

    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mContext = SplashScreenActivity.this;

        requestPermision();


        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(mContext, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
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

    private void setupMVP(String latitude, String longitude) {
        splashPresenter = new SplashPresenter(this, latitude, longitude);
    }


    private void getWeather() {

        splashPresenter.getWeather();

    }


    private void requestPermision() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }


    @Override
    public void displayError(String s) {
        Toast.makeText(SplashScreenActivity.this, s, Toast.LENGTH_LONG).show();

    }

    @Override
    public void startMainActivity(Feed feed) {
        Intent intent  = new Intent(mContext, MainActivity.class);
        intent.putExtra("feed", feed);
        mContext.startActivity(intent);
        ((Activity) mContext).finish();
    }

}