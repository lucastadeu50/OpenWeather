package com.example.lucas.openweather.splashscreen;

import com.example.lucas.openweather.model.Feed;

public interface SplashScreenViewInterface {
    void displayError(String s);
    void startMainActivity(Feed feed);

}
