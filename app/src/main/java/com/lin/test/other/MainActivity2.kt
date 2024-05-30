package com.lin.test.other

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lin.test.databinding.ActivityMain2Binding
import com.lin.test.view.ColorTrackTextView

class MainActivity2 : AppCompatActivity() {
    private lateinit var textView: ColorTrackTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        textView = binding.textView
        binding.leftToRight.setOnClickListener {
            leftToRight()
        }
        binding.rightToLeft.setOnClickListener { rightToLeft() }
    }

    private fun leftToRight() {
        textView.setDirection(ColorTrackTextView.Direction.LIFT_TO_RIGHT)
        val valueAnimator = ObjectAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 2000
        valueAnimator.addUpdateListener {
            val currentProgress = it.getAnimatedValue() as Float
            Log.e("TAG", "currentProgress: $currentProgress" )
            textView.setCurrentProgress(currentProgress)
        }
        valueAnimator.start()
    }

    private fun rightToLeft() {
        textView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LIFT)
        val valueAnimator = ObjectAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 2000
        valueAnimator.addUpdateListener {
            val currentProgress = it.getAnimatedValue() as Float
            Log.e("TAG", "currentProgress: $currentProgress" )
            textView.setCurrentProgress(currentProgress)
        }
        valueAnimator.start()
    }
}