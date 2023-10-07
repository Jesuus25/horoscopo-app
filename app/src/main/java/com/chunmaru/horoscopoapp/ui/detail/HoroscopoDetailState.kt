package com.chunmaru.horoscopoapp.ui.detail

import com.chunmaru.horoscopoapp.domain.model.HoroscopoModel

sealed class HoroscopoDetailState {
    data object Loading:HoroscopoDetailState()
    data class Error(val error:String):HoroscopoDetailState()
    data class success(val data:String,val sign:String,val horoscopoModel: HoroscopoModel):HoroscopoDetailState()

}