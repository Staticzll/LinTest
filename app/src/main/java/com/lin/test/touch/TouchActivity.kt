package com.lin.test.touch

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lin.test.databinding.ActivityTouchBinding

class TouchActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTouchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.touchView.setOnTouchListener { v, event ->
            Log.e(TAG, "onTouch: ${event.action}")
            false
        }

//        binding.touchView.setOnClickListener {
//            Log.e(TAG, "onClick", )
//        }
    }

    companion object {
        const val TAG = "TAG"
    }
}