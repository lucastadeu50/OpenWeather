package com.example.lucas.openweather.mainactivity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lucas.openweather.R;
import com.example.lucas.openweather.model.Feed;


public class MainActivity extends AppCompatActivity implements MainViewInterface {

    TextView textView;
    private Context mContext;

    MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Feed feed = (Feed) getIntent().getSerializableExtra("feed");


        mContext = MainActivity.this;

        textView = findViewById(R.id.textView);

        setupMVP();

        getWeather(feed);

    }
    private void setupMVP() {
        mainPresenter = new MainPresenter(this);
    }


    private void getWeather(Feed feed) {

        mainPresenter.getWeather(feed);

    }


    @Override
    public void displayWeather(String texto) {
        textView.setText(texto);

    }

}
