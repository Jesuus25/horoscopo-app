package com.chunmaru.horoscopoapp.ui.luck

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import java.util.Random
import com.chunmaru.horoscopoapp.R
import com.chunmaru.horoscopoapp.databinding.FragmentHoroscopoBinding
import com.chunmaru.horoscopoapp.databinding.FragmentLuckBinding
import com.chunmaru.horoscopoapp.ui.core.listener.OnSwipeTouchListener
import com.chunmaru.horoscopoapp.ui.providers.RandomCardProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LuckFragment : Fragment() {
    private var _binding: FragmentLuckBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var randomCardProvider: RandomCardProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        preparePrediction()
        initListener()
    }

    private fun preparePrediction() {
        val lucky = randomCardProvider.getLucky()
        lucky?.let {luck->
            val currentPrediction = getString(luck.text)
            binding.ivLuckyCard.setImageResource(luck.image)
            binding.tvLucky.text = getString(luck.text)
            binding.tvShare.setOnClickListener { shareResult(currentPrediction) }
        }
    }

    private fun shareResult(prediction:String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,prediction)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent,null)
        startActivity(shareIntent)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {
        binding.ivRuleta.setOnTouchListener(object : OnSwipeTouchListener(requireContext()){
            @SuppressLint("ClickableViewAccessibility")
            override fun onSwipeRight() {
                girarRuleta()
            }

            override fun onSwipeLeft() {
                girarRuleta()
            }
        })
    }

    private fun girarRuleta() {
        val random = Random()
        val degrees = random.nextInt(1440) + 360

        val animator =
            ObjectAnimator.ofFloat(binding.ivRuleta, View.ROTATION, 0f, degrees.toFloat())
        animator.duration = 2000
        animator.interpolator = DecelerateInterpolator()
        animator.doOnEnd { slideCard() }
        animator.start()

    }

    private fun slideCard() {
        val slideUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)

        slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.ivReverse.isVisible = true
            }

            override fun onAnimationEnd(p0: Animation?) {
                growCard()
            }

            override fun onAnimationRepeat(p0: Animation?) {}

        })
        binding.ivReverse.startAnimation(slideUpAnimation)

    }

    private fun growCard() {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)
        growAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                binding.ivReverse.isVisible = false
                showPremonitionView()

            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
        binding.ivReverse.startAnimation(growAnimation)
    }

    private fun showPremonitionView() {
        val disolverAnimacion = AlphaAnimation(1.0f, 0.0f)
        val aparecerAnimacion = AlphaAnimation(0.0f, 1.0f)
        disolverAnimacion.duration = 300
        aparecerAnimacion.duration = 900
        disolverAnimacion.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {
                binding.preview.isVisible = false
                binding.prediction.isVisible = true
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
        binding.preview.startAnimation(disolverAnimacion)
        binding.prediction.startAnimation(aparecerAnimacion)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLuckBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}