package com.chunmaru.horoscopoapp.data.providers

import com.chunmaru.horoscopoapp.domain.model.HoroscopeInfo
import com.chunmaru.horoscopoapp.domain.model.HoroscopeInfo.*
import javax.inject.Inject

class HoroscopoProvider @Inject constructor(){
    fun getHoroscopo(): List<HoroscopeInfo> {
        return listOf(
            Aries,
            Tauro,
            Geminis,
            Cancer,
            Leo,
            Virgo,
            Libra,
            Scorpio,
            Sagitario,
            Capricornio,
            Aquario,
            Piscis
        )
    }
}