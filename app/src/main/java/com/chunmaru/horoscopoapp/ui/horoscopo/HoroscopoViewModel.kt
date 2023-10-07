package com.chunmaru.horoscopoapp.ui.horoscopo

import androidx.lifecycle.ViewModel
import com.chunmaru.horoscopoapp.data.providers.HoroscopoProvider
import com.chunmaru.horoscopoapp.domain.model.HoroscopeInfo
import com.chunmaru.horoscopoapp.domain.model.HoroscopeInfo.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HoroscopoViewModel @Inject constructor(private val horoscopoProvider: HoroscopoProvider):ViewModel() {
    private var _horoscopo = MutableStateFlow<List<HoroscopeInfo>>(emptyList())
    val horoscopo:StateFlow<List<HoroscopeInfo>> = _horoscopo

    init {
        _horoscopo.value = horoscopoProvider.getHoroscopo()
    }

}