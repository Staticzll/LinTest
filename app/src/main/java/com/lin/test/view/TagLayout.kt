package com.lin.test.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.lin.test.adapter.BaseAdapter
import kotlin.math.max

/**
 * created by zll on 2024/5/28 11:09
 */
class TagLayout(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    ViewGroup(context, attr, defStyleAttr) {

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)

    private val mChildViews = mutableListOf<MutableList<View>>()
    private var mAdapter: BaseAdapter? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mChildViews.clear()

        // 获取宽度
        val width = MeasureSpec.getSize(widthMeasureSpec)
        // 高度需要计算
        var height = paddingTop + paddingBottom
        // 一行的宽度
        var lineWidth = paddingStart

        var childViews = mutableListOf<View>()
        mChildViews.add(childViews)

        var maxHeight = 0

        for (i in 0 until childCount) {
            //  2.1.1 for循环测量子View
            val childView = getChildAt(i)
            // 这段话执行之后就可以获取子view的宽高，因为会调用子view的onMeasure
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)

            // margin
            val params = childView.layoutParams as MarginLayoutParams

            // 什么时候需要换行，一行不够的情况下  考虑margin
            if (lineWidth + (childView.measuredWidth + params.marginStart + params.marginEnd) > width) {
                // 换行 ,累加高度 加上一行条目中最大高度
                height += childView.measuredHeight + params.topMargin + params.bottomMargin
                lineWidth = childView.measuredWidth + params.marginStart + params.marginEnd
                childViews = mutableListOf()
                mChildViews.add(childViews)
            } else {
                lineWidth += childView.measuredWidth + params.marginStart + params.marginEnd
                maxHeight = max(
                    childView.measuredHeight + params.topMargin + params.bottomMargin, maxHeight
                )
            }

            childViews.add(childView)
        }

        height += maxHeight
        Log.e("TAG", "width: $width height: $height ${mChildViews.size}")
        // 2.1.2 根据子view计算和指定自己的宽高
        setMeasuredDimension(width, height)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }


    /**
     * 摆放子View
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left: Int
        var right: Int
        var top = paddingTop
        var bottom: Int
        mChildViews.forEach parent@{ childViews ->
            var maxHeight = 0
            left = paddingStart
            if (childViews.isEmpty()) return@parent
            childViews.forEach child@{ childView ->
                if (childView.visibility == View.GONE) return@child
                val params = childView.layoutParams as MarginLayoutParams
                left += params.marginStart
                val childTop = top + params.topMargin
                right = left + childView.measuredWidth
                bottom = childTop + childView.measuredHeight
                // 摆放
                childView.layout(left, childTop, right, bottom)

                // 累加
                left += childView.measuredWidth + params.marginEnd

                // 计算当前行view最大高度
                val childHeight = childView.measuredHeight + params.topMargin + params.bottomMargin
                maxHeight = max(childHeight, maxHeight)
            }

            // 不断叠加top值
            top += maxHeight
        }
    }

    fun setAdapter(adapter: BaseAdapter) {
        this.mAdapter = null
        this.mAdapter = adapter

        // 清空所有子view
        removeAllViews()

        val childCount = adapter.getCount()
        for (i in 0 until childCount) {
            // 通过位置获取view
            val view = adapter.getView(i, this)
            addView(view)
        }
    }
}