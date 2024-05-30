package com.lin.test.ratingbar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.lin.test.adapter.BaseAdapter
import com.lin.test.databinding.ActivityRatingBarBinding
import com.lin.test.databinding.ItemTagBinding

/**
 * created by zll on 2024/5/20 10:46
 */
class RatingBarActivity : AppCompatActivity() {
    private val mTags = mutableListOf<String>().apply {
        add("Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!")
        add("Hello world!Hello world!Hello world!Hello world!Hello world!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRatingBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tagLayout.setAdapter(object : BaseAdapter() {
            override fun getCount() = mTags.size

            override fun getView(position: Int, parent: ViewGroup): View {
                val itemBinding = ItemTagBinding.inflate(
                    LayoutInflater.from(this@RatingBarActivity), parent, false
                )
                itemBinding.tag.text = mTags[position]
                itemBinding.tag.setOnClickListener {
                    Log.e("TAG", "getView: ${mTags[position]}")
                }
                return itemBinding.root
            }
        })

    }
}