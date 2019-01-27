package com.example.lucas.openweather.splashscreen;

import com.example.lucas.openweather.model.Feed;
import com.example.lucas.openweather.network.NetworkClient;
import com.example.lucas.openweather.network.NetworkInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashPresenter implements SplashPresenterInterface{
    private static String appid = "7052879bbb729b5e5e15752641ae6b79";
    private static String lang = "pt";
    private static String units = "metric";
    SplashScreenViewInterface splashScreenViewInterface;
    String latitude;
    String longitude;


    public SplashPresenter(SplashScreenViewInterface mvi, String latitude, String longitude) {
        this.splashScreenViewInterface = mvi;
        this.latitude = latitude;
        this.longitude = longitude;
    }



    @Override
    public void getWeather (){

        NetworkInterface networkInterface = NetworkClient.getRetrofit().create(NetworkInterface.class);


        final Call<Feed> call = networkInterface.getData(latitude, longitude, appid, lang, units);

        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {

                if (response.isSuccessful()) {
                    Feed feed = response.body();
                    splashScreenViewInterface.startMainActivity(feed);
                }
            }
            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                splashScreenViewInterface.displayError(t.getMessage());

            }

        });

    }


}
