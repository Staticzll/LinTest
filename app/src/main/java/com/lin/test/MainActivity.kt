package com.lin.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lin.test.databinding.ActivityMainBinding
import com.lin.test.letterbar.LetterBarActivity
import com.lin.test.ratingbar.RatingBarActivity
import com.lin.test.touch.TouchActivity

class MainActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ratingBar.setOnClickListener(this)
        binding.letterSideBar.setOnClickListener(this)
        binding.touchView.setOnClickListener {
            start(TouchActivity::class.java)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rating_bar -> {
                start(RatingBarActivity::class.java)
            }

            R.id.letter_side_bar -> {
                start(LetterBarActivity::class.java)
            }
        }
    }

    private fun start(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

}