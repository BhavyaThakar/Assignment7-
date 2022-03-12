package com.example.assignment7;

import com.google.gson.annotations.SerializedName;

public class RequiredJson {

    @SerializedName("main")
    Main main;

    @SerializedName("wind")
    Wind wind;

    @SerializedName("clouds")
    Clouds clouds;

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
