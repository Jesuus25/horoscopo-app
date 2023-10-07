package com.chunmaru.horoscopoapp.domain

import com.chunmaru.horoscopoapp.domain.model.PredictionModel

interface Repository {
    suspend fun getPrediction(sign:String):PredictionModel?
}