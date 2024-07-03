package com.lin.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lin.test.base.BaseActivity
import com.lin.test.bean.ActivityBean
import com.lin.test.databinding.ActivityMainBinding
import com.lin.test.letterbar.LetterBarActivity
import com.lin.test.listview.VerticalDragListViewActivity
import com.lin.test.ratingbar.RatingBarActivity
import com.lin.test.slidingmenu.SlidingMenuActivity
import com.lin.test.touch.TouchActivity
import com.lin.test.view.VerticalDragListView

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = ActivityAdapter()
        adapter.addAll(getData())
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter
    }

    private fun getData(): Collection<ActivityBean> {
        return mutableListOf<ActivityBean>().apply {
            add(ActivityBean("rating_bar", RatingBarActivity::class.java))
            add(ActivityBean("letter_side_bar", LetterBarActivity::class.java))
            add(ActivityBean("touch_view", TouchActivity::class.java))
            add(ActivityBean("sliding_menu", SlidingMenuActivity::class.java))
            add(ActivityBean("verticalDragListView", VerticalDragListViewActivity::class.java))
        }
    }

    override fun inflateView(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

}