package com.example.covid19tracker

import retrofit2.Call
import retrofit2.http.GET

interface CovidService {

    @GET("/data.json")
    fun getStatesData(): Call<CovidData>
}