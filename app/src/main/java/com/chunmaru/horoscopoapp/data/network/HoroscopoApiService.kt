package com.chunmaru.horoscopoapp.data.network

import com.chunmaru.horoscopoapp.data.network.response.PredictionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HoroscopoApiService {
    @GET("/{sign}")
    suspend fun getHoroscopo(@Path("sign") sign:String):PredictionResponse
}