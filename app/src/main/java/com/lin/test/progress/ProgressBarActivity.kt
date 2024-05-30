package com.lin.test.progress

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lin.test.databinding.ActivityProgressBarBinding
import com.lin.test.view.ShapeView
import kotlin.concurrent.thread

class ProgressBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityProgressBarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.progressBar.setMax(4000)

        val valueAnimator = ObjectAnimator.ofFloat(0f, 4000f)
        valueAnimator.duration = 2000
        valueAnimator.addUpdateListener {
            val progress = it.getAnimatedValue() as Float
            binding.progressBar.setProgress(progress.toInt())
        }
        valueAnimator.start()


        binding.change.setOnClickListener {
            change(binding.shapeView)
        }
    }

    private fun change(view: ShapeView) {

        thread {
            while (true) {
                runOnUiThread {
                    view.exchange()
                }
                Thread.sleep(1000)
            }
        }.start()
    }
}