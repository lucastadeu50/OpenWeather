package com.example.lucas.openweather.ui;


import com.example.lucas.openweather.model.Feed;
import com.example.lucas.openweather.network.NetworkClient;
import com.example.lucas.openweather.network.NetworkInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainPresenter implements MainPresenterInterface {



    private static String appid = "7052879bbb729b5e5e15752641ae6b79";
    private static String lang = "pt";
    private static String units = "metric";
    MainViewInterface mvi;
    String latitude;
    String longitude;


    public MainPresenter(MainViewInterface mvi, String latitude, String longitude) {
        this.mvi = mvi;
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

                    String cidade = response.body().getName();
                    String temperatura = String.valueOf(response.body().getMain().getTemp());
                    String descricao = response.body().getWeather().get(0).getDescription();
                    String texto = temperatura + "° graus em " + cidade +" com " + descricao +".";
                    mvi.displayWeather(texto);
                }
            }
            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                mvi.displayError(t.getMessage());

            }

        });

    }




}
