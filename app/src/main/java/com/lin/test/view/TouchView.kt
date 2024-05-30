package com.lin.test.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * created by zll on 2024/5/29 9:59
 */
class TouchView(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    View(context, attr, defStyleAttr) {


    private val TAG: String ="TAG"

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)


    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e(TAG, "View.onTouchEvent: ${event.action}")
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.e(TAG, "View.dispatchTouchEvent: ${event.action}")
        return super.dispatchTouchEvent(event)
    }
}