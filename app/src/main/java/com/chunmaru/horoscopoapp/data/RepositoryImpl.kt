package com.chunmaru.horoscopoapp.data

import android.util.Log
import com.chunmaru.horoscopoapp.data.network.HoroscopoApiService
import com.chunmaru.horoscopoapp.data.network.response.PredictionResponse
import com.chunmaru.horoscopoapp.domain.Repository
import com.chunmaru.horoscopoapp.domain.model.PredictionModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: HoroscopoApiService) :Repository {
    override suspend fun getPrediction(sign: String):PredictionModel? {
        // llamar a retrofit
        runCatching { apiService.getHoroscopo(sign) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("repositoryImpl","ha ocurrido un error: ${it.message}") }
        return null
    }
}