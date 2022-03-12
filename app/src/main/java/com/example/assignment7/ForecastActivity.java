package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastActivity extends AppCompatActivity {

    TextView showTemp, showHumidity, showRainy, showWind;
    String city;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apiKey = "5facc01e675a7560d496053394c0078b";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecaset);

        city = getIntent().getStringExtra("city").trim();

        showTemp = findViewById(R.id.show_temp_tv);
        showHumidity = findViewById(R.id.show_humidity_tv);
        showRainy = findViewById(R.id.show_rainy_tv);
        showWind = findViewById(R.id.show_wind_tv);
        getWeather();

    }

    public void getWeather() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        weatherapi myapi = retrofit.create(weatherapi.class);
        Call<RequiredJson> requiredJsonCall = myapi.getweather(city, apiKey);
        requiredJsonCall.enqueue(new Callback<RequiredJson>() {
            @Override
            public void onResponse(Call<RequiredJson> call, Response<RequiredJson> response) {
                if (response.code() == 404){
                    Toast.makeText(ForecastActivity.this, "please enter a valid city", Toast.LENGTH_SHORT).show();
                }else if(!(response.isSuccessful())){
                    Toast.makeText(ForecastActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }else if (city == "" || city == null){
                    Toast.makeText(ForecastActivity.this, "please enter a valid city", Toast.LENGTH_SHORT).show();

                }
                RequiredJson requiredJson = response.body();

                Main main = requiredJson.getMain();
                Double temp = main.getTemp();
                Integer temprature = (int)(temp - 273.15);

                Integer humidity = main.getHumidity();


                showTemp.setText(String.valueOf(temprature));
                showHumidity.setText(String.valueOf(humidity) + "%");

                Clouds clouds = requiredJson.getClouds();
                Integer cld = clouds.getAll();

                showRainy.setText(String.valueOf(cld));

                Wind wind = requiredJson.getWind();
                Double wnd = wind.getSpeed();
                showWind.setText(String.valueOf(wnd) + " m/s");




            }

            @Override
            public void onFailure(Call<RequiredJson> call, Throwable t) {
                Toast.makeText(ForecastActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}