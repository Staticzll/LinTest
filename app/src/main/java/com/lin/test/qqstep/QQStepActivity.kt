package com.lin.test.qqstep

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lin.test.databinding.ActivityQqStepBinding
import com.lin.test.view.QQStepView

/**
 * created by zll on 2024/5/9 10:58
 */
class QQStepActivity : AppCompatActivity() {
    private lateinit var qqStepView: QQStepView
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityQqStepBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        qqStepView = binding.qqStepView
        qqStepView.setStepMax(4000f)
        binding.running.setOnClickListener {
            running()
        }
    }

    private fun running() {
        val valueAnimator = ObjectAnimator.ofFloat(0f, 3000f)
        valueAnimator.duration = 2000
        valueAnimator.addUpdateListener {
            val animatedValue = it.getAnimatedValue() as Float
            qqStepView.setCurrentStep(animatedValue)

        }
        valueAnimator.start()
    }
}