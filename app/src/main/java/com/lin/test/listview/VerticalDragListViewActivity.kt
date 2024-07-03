package com.lin.test.listview

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lin.test.base.BaseActivity
import com.lin.test.databinding.ActivityVerticalDragListViewBinding

class VerticalDragListViewActivity : BaseActivity<ActivityVerticalDragListViewBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mItems = ArrayList<String>()

        for (i in 0..199) {
            mItems.add("i -> $i")
        }

        val adapter = DragAdapter(mItems)
        binding.listView.layoutManager = LinearLayoutManager(this)
        binding.listView.adapter = adapter
    }

    override fun inflateView(): ActivityVerticalDragListViewBinding {
        return ActivityVerticalDragListViewBinding.inflate(layoutInflater)
    }
}