package com.chunmaru.horoscopoapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.chunmaru.horoscopoapp.R
import com.chunmaru.horoscopoapp.databinding.ActivityHoroscopoDetailBinding
import com.chunmaru.horoscopoapp.domain.model.HoroscopoModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHoroscopoDetailBinding

    //segunda forma de conectar con el view model
    private val horoscopoDetailViewModel: HoroscopoDetailViewModel by viewModels()

    private val args: HoroscopoDetailActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHoroscopoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        horoscopoDetailViewModel.getHoroscope(args.type)
    }

    private fun initUI() {
        initListener()
        initUIState()
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                horoscopoDetailViewModel.state.collect() {
                    when (it) {
                        HoroscopoDetailState.Loading -> loadinState()
                        is HoroscopoDetailState.Error -> errorState()
                        is HoroscopoDetailState.success -> successState(it)
                    }
                }
            }
        }
    }

    private fun loadinState() {
        binding.pb.isVisible = true
    }

    private fun errorState() {
        binding.pb.isVisible = false
    }

    private fun successState(state: HoroscopoDetailState.success) {
        binding.pb.isVisible = false
        binding.tvTitle.text = state.sign
        binding.tvBody.text = state.data

        val image = when(state.horoscopoModel){
            Aries -> R.drawable.detail_aries
            Taurus ->R.drawable.detail_taurus
            Gemini ->R.drawable.detail_gemini
            Cancer ->R.drawable.detail_cancer
            Leo ->R.drawable.detail_leo
            Virgo ->R.drawable.detail_virgo
            Libra ->R.drawable.detail_libra
            Scorpio ->R.drawable.detail_scorpio
            Sagittarius ->R.drawable.detail_sagittarius
            Capricorn ->R.drawable.capricornio
            Aquarius ->R.drawable.detail_aquarius
            Pisces ->R.drawable.detail_pisces
        }

        binding.ivDetail.setImageResource(image)
       // Log.d("horoscopeui","${state.data}")
    }


}