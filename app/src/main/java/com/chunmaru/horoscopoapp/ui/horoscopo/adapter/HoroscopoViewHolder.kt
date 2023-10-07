package com.chunmaru.horoscopoapp.ui.horoscopo.adapter

import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.chunmaru.horoscopoapp.databinding.ItemHoroscopeBinding
import com.chunmaru.horoscopoapp.domain.model.HoroscopeInfo

class HoroscopoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val bindig = ItemHoroscopeBinding.bind(view)
    fun render(horoscopeInfo: HoroscopeInfo, onItemSelected: (HoroscopeInfo) -> Unit) {
        val context = bindig.tvTitle.context
        bindig.ivHoroscopo.setImageResource(horoscopeInfo.img)
        bindig.tvTitle.text = context.getString(horoscopeInfo.name)
        bindig.parent.setOnClickListener {
            startRotatioAnimation(bindig.ivHoroscopo, newLambda = {onItemSelected(horoscopeInfo)})
        //onItemSelected(horoscopeInfo)
        }
    }

    private fun startRotatioAnimation(view: View,newLambda:()->Unit){
        view.animate().apply {
            duration =500
            interpolator = LinearInterpolator()
            rotationBy(360f)
            withEndAction { newLambda() }
            start()
        }
    }

}