package com.example.lucas.openweather.mainactivity;


import com.example.lucas.openweather.model.Feed;


public class MainPresenter implements MainPresenterInterface {
    MainViewInterface mvi;

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }


    @Override
    public void getWeather (Feed feed){
        String cidade = feed.getName();
        String temperatura = String.valueOf(feed.getMain().getTemp());
        String descricao = feed.getWeather().get(0).getDescription();
        String texto = temperatura + "° graus em " + cidade +" com " + descricao +".";
        mvi.displayWeather(texto);

    }




}
