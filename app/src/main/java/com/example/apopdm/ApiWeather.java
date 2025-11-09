package com.example.apopdm;

import retrofit2.Call;
import retrofit2.http.GET;

interface ApiTempo {
    //"https://api.hgbrasil.com/weather/?format=json&woeid=455883&locale=pt"
    @GET("weather/?format=json&woeid=26804127&locale=pt")
//    455833
//            26804127
    Call<ApiPojo> getInfTempo();
}