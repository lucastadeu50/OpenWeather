# OpenWeather
OpenWeather usando MVP


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
appid que representa a key, lang que seleciona a lingua portuguesa e units que seleciona o sistema de medidas para metrico, o retorno é salvo em um objeto do tipo Feed.




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

A MainActivity recebe a resposta vinda da SplashScreen através da intent e mostra na tela.


### MainActivity

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


### MainPresenter

A MainPresenter lida com o objeto feed e separa em Strings para serem exiidas na MainActivity
```
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


```

O package model foi todo feito com a ajuda do plugin RoboPOJOGenerator que gerou todas classes de modelo automaticamente

````

public class Feed implements Serializable {
	private int dt;
	private List<WeatherItem> weather;
	private String name;
	private int cod;
	private Main main;
	private int id;
	private String base;

	public int getDt() {
		return dt;
	}

	public void setDt(int dt) {
		this.dt = dt;
	}

	public List<WeatherItem> getWeather() {
		return weather;
	}

	public void setWeather(List<WeatherItem> weather) {
		this.weather = weather;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	@Override
	public String toString() {
		return "Feed{" +
				"dt=" + dt +
				", weather=" + weather +
				", name='" + name + '\'' +
				", cod=" + cod +
				", main=" + main +
				", id=" + id +
				", base='" + base + '\'' +
				'}';
	}
}

````

### NetworkClient e NetworkInterface

Para fazer a chamada na api é necessario uma Interface e um Client.


```
public class NetworkClient {

    private static String BASE_URL = "http://api.openweathermap.org/";

    public static Retrofit retrofit;

    public void NetworkClient(){

    }

    public static Retrofit getRetrofit(){

        if(retrofit==null){
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


}

````
```
public interface NetworkInterface {

        @Headers("Content-Type: application/json")
        @GET("/data/2.5/weather")
        Call<Feed> getData(@Query("lat") String latitude, @Query("lon") String longitude, @Query("appid") String appid, @Query("lang") String lang, @Query("units") String units);
}

````






