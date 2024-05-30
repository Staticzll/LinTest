package com.lin.test.adapter

import android.view.View
import android.view.ViewGroup

/**
 * created by zll on 2024/5/28 16:51
 */
abstract class BaseAdapter {

    // 1,有多少条目
    abstract fun getCount(): Int

    // 2，getView通过position
    abstract fun getView(position: Int, parent: ViewGroup): View
}