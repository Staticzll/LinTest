package com.lin.test.letterbar

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lin.test.R
import com.lin.test.view.LetterSideBar

class LetterBarActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var letterSideBar: LetterSideBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_bar)
        textView = findViewById<TextView>(R.id.textView)
        letterSideBar = findViewById(R.id.letter_side_bar)
        letterSideBar.listener = object : LetterSideBar.LetterTouchListener {
            override fun touch(letter: CharSequence?, isTouch: Boolean) {
                if (isTouch) {
                    textView.visibility = View.VISIBLE
                    textView.text = letter
                } else {
                    textView.visibility = View.GONE
                }
            }
        }
    }
}