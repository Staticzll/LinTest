package com.lin.test.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.lin.test.R

/**
 * created by zll on 2024/5/20 10:43
 */
class RatingBar(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    View(context, attr, defStyleAttr) {
    private var mStarNormalBitmap: Bitmap
    private var mStarFocusBitmap: Bitmap
    private var mGradeNumber = 5
    private var mCurrentGrade = 0
    private var mSpacing = 20

    init {
        val array = context.obtainStyledAttributes(attr, R.styleable.RatingBar)
        val startNormalId = array.getResourceId(R.styleable.RatingBar_startNormal, 0)
        if (startNormalId == 0) {
            throw RuntimeException("请设置属性startNormal")
        }

        mStarNormalBitmap = BitmapFactory.decodeResource(resources, startNormalId)

        val startFocusId = array.getResourceId(R.styleable.RatingBar_startFocus, 0)
        if (startFocusId == 0) {
            throw RuntimeException("请设置属性startFocus")
        }

        mStarFocusBitmap = BitmapFactory.decodeResource(resources, startFocusId)

        mGradeNumber = array.getInteger(R.styleable.RatingBar_gradeNumber, mGradeNumber)
        array.recycle()
    }

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 高度
        val height = mStarFocusBitmap.height
        val number = if (mGradeNumber > 1) mGradeNumber - 1 else 0
        val spacing = number * mSpacing
        val allViewWidth = mStarFocusBitmap.width * mGradeNumber
        val width = paddingStart + paddingEnd + allViewWidth + spacing
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        for (i in 0 until mGradeNumber) {
            // i*星星的宽度
            val x = paddingStart + i * mStarFocusBitmap.width.toFloat() + i * mSpacing

            if (mCurrentGrade > i) {
                canvas.drawBitmap(mStarFocusBitmap, x, 0f, null)
            } else {
                canvas.drawBitmap(mStarNormalBitmap, x, 0f, null)
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                //Log.e("TAG", "onTouchEvent: ${event.action}")
                val moveX = event.x //  event.getX()相对于当前控件的位置 event.getRaw()获取屏幕的x位置
                var currentGrade =
                    ((moveX - paddingStart) / (mStarFocusBitmap.width + mSpacing) + 1).toInt()
                currentGrade = if (currentGrade < 0) 0 else currentGrade
                currentGrade = if (currentGrade > mGradeNumber) mCurrentGrade else currentGrade
                if (mCurrentGrade == currentGrade) return true

                mCurrentGrade = currentGrade
                invalidate()
            }
        }
        return true
    }

}