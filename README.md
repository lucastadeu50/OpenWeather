# OpenWeather
OpenWeather usando MVP

## Getting Started

### SplashScreenActivity

A primeira que é solicittado ao usuario a requisição de permissão para acessar a localização

```
   if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        
 ```
Depois de permitido o acesso a localização, o metodo onRequestPermissionsResult é chamado, dentro desse metodo ele verifica 
se a permissão foi aceita ou não. Se aceita, o aplicativo pega a latitude e longitude do dispositivo,faz a chamada na api 
do Openweather e fecha a SplashScreen.


````
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
````


### SplashScreenPresenter

Para realização da chamada na api foi utilizado retrofit 2, 5 parametros são passados para a api a latitude, longitude, 
appid que representa a key, lang que seleciona a lingua portuguesa e units que seleciona o sistema de medidas para metrico.




```

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
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
