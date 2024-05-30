package com.lin.test.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

/**
 * created by zll on 2024/5/21 15:33
 */
class LetterSideBar(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    View(context, attr, defStyleAttr) {
    private var mCurrentTouchLetter: String? = null
    private var mPaint: Paint = Paint()
    private val mLetters = arrayOf(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z",
        "#"
    )

    init {
        mPaint.isAntiAlias = true
        mPaint.textSize = sp2px(20)
        mPaint.color = Color.BLUE
    }

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 计算指定宽度 = 左右padding + 字母的宽度（取决于画笔）
        val textWidth = mPaint.measureText("A")
        val width = (paddingStart + paddingEnd + textWidth).toInt()
        // 高度可以直接获取
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun sp2px(sp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics
        )
    }

    override fun onDraw(canvas: Canvas) {
        // 画26个字母

        val itemHeight = (height - paddingTop - paddingBottom) / mLetters.size

        mLetters.forEachIndexed { index, s ->
            val textWidth = mPaint.measureText(s)
            val x = width / 2 - textWidth / 2
            val letterCenterY = index * itemHeight + itemHeight / 2 + paddingTop
            val fontMetrics = mPaint.fontMetrics
            val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseLine = letterCenterY + dy

            if (mLetters[index] == mCurrentTouchLetter) {
                mPaint.color = Color.RED
            } else {
                mPaint.color = Color.BLUE
            }
            canvas.drawText(s, x, baseLine, mPaint)

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val currentMoveY = event.y
                val itemHeight = (height - paddingTop - paddingBottom) / mLetters.size
                var currentPosition = (currentMoveY / itemHeight).toInt()
                if (currentPosition < 0) {
                    currentPosition = 0
                }
                if (currentPosition > mLetters.size - 1) {
                    currentPosition = mLetters.size - 1
                }
                mCurrentTouchLetter = mLetters[currentPosition]
                listener?.touch(mCurrentTouchLetter,true)
                invalidate()
            }
            MotionEvent.ACTION_UP->{
                listener?.touch(mCurrentTouchLetter,false)
            }
        }
        return true
    }

    var listener: LetterTouchListener? = null

    interface LetterTouchListener {
        fun touch(letter: CharSequence?, isTouch: Boolean)
    }

}