package com.chunmaru.horoscopoapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chunmaru.horoscopoapp.domain.model.HoroscopoModel
import com.chunmaru.horoscopoapp.domain.usecase.GetPredictionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HoroscopoDetailViewModel @Inject constructor(private val getPredictionUseCase: GetPredictionUseCase) :
    ViewModel() {
    private var _state = MutableStateFlow<HoroscopoDetailState>(HoroscopoDetailState.Loading)
    val state: StateFlow<HoroscopoDetailState> = _state
    lateinit var horoscopo: HoroscopoModel
    fun getHoroscope(sign: HoroscopoModel) {
        horoscopo = sign
        viewModelScope.launch {
            //hilo principal
            _state.value = HoroscopoDetailState.Loading
            val result =
                withContext(Dispatchers.IO) { getPredictionUseCase(sign.name) } //hilo secundario
            //hilo prinicipal
            if (result != null) {
                _state.value = HoroscopoDetailState.success(result.horoscope, result.sign, horoscopo)
            } else {
                _state.value =
                    HoroscopoDetailState.Error("ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

}