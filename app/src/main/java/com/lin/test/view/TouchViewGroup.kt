package com.lin.test.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

/**
 * created by zll on 2024/5/29 9:59
 */
class TouchViewGroup(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attr, defStyleAttr) {


    private val TAG: String = "TAG"

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)


    // 事件分发
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.e(TAG, "ViewGroup.dispatchTouchEvent: ${ev.action}")
        return super.dispatchTouchEvent(ev)
    }

    // 事件触摸
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        Log.e(TAG, "ViewGroup.onTouchEvent: ${ev.action}")
        return super.onTouchEvent(ev)
    }

    // 事件拦截
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.e(TAG, "ViewGroup.onInterceptTouchEvent: ${ev.action}")
        return true
    }
}