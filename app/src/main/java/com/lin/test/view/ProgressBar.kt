package com.lin.test.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.lin.test.R
import kotlin.math.min

/**
 * created by zll on 2024/5/10 10:35
 */
class ProgressBar(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    View(context, attr, defStyleAttr) {

    private var mInnerBackground = Color.RED
    private var mOuterBackground = Color.RED
    private var mProgressTextSize = 20f
    private var mProgressTextColor = Color.RED
    private var mRoundWidth = 10f

    private var mInnerPaint: Paint
    private var mOuterPaint: Paint
    private var mTextPaint: Paint
    private var mMax = 100
    private var mProgress = 50
    private lateinit var mRectF: RectF

    init {
        val array = context.obtainStyledAttributes(attr, R.styleable.ProgressBar)
        mInnerBackground = array.getColor(R.styleable.ProgressBar_innerBackground, mInnerBackground)
        mOuterBackground = array.getColor(R.styleable.ProgressBar_outerBackground, mOuterBackground)
        mRoundWidth = array.getDimension(R.styleable.ProgressBar_roundWidth, dip2px(10))
        mProgressTextColor =
            array.getColor(R.styleable.ProgressBar_progressTextColor, mProgressTextColor)
        mProgressTextSize =
            array.getDimension(R.styleable.ProgressBar_progressTextSize, sp2px(mProgressTextSize))
        array.recycle()

        mInnerPaint = Paint()
        mInnerPaint.isAntiAlias = true
        mInnerPaint.style = Paint.Style.STROKE
        mInnerPaint.strokeWidth = mRoundWidth
        mInnerPaint.color = mInnerBackground

        mOuterPaint = Paint()
        mOuterPaint.isAntiAlias = true
        mOuterPaint.style = Paint.Style.STROKE
        mOuterPaint.strokeWidth = mRoundWidth
        mOuterPaint.color = mOuterBackground

        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        mTextPaint.color = mProgressTextColor
        mTextPaint.textSize = mProgressTextSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 只保证是正方形
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val realSize = min(width, height)
        setMeasuredDimension(realSize, realSize)
        Log.e(TAG, "onMeasure: width: $width, height: $height")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e(TAG, "onLayout left: $left , top: $top , right: $right bottom: $bottom")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.e(TAG, "onSizeChanged w:$w, h: $h, oldw: $oldw, oldh: $oldh")
        mRectF = RectF(
            mRoundWidth / 2, mRoundWidth / 2, width - mRoundWidth / 2, height - mRoundWidth / 2
        )
    }

    override fun onDraw(canvas: Canvas) {
        Log.e(TAG, "onDraw: ")
        val center = width / 2f
        canvas.drawCircle(
            center, center, center - mRoundWidth / 2, mInnerPaint
        )
        if (mProgress == 0) return


        val percent = mProgress / mMax.toFloat()
        Log.e(TAG, "onDraw: $percent")
        canvas.drawArc(mRectF, 0f, percent * 360, false, mOuterPaint)

        val text = "${(percent * 100).toInt()}%"
        val bounds = Rect()
        mTextPaint.getTextBounds(text, 0, text.length, bounds)
        val dx = width / 2f - bounds.width() / 2f
        val fontMetrics = mTextPaint.getFontMetrics()
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseLine = height / 2 + dy
        canvas.drawText(text, dx, baseLine, mTextPaint)
    }


//    private val reentrantLock = ReentrantLock()
//
//    private fun setMax(max: Int) {
//        reentrantLock.lock()
//        mMax = max
//        reentrantLock.unlock()
//    }

    fun setMax(max: Int) {
        if (max < 0) return
        synchronized(Unit) {
            mMax = max
        }
    }

    fun setProgress(progress: Int) {
        if (progress < 0) return
        synchronized(Unit) {
            mProgress = progress
            invalidate()
        }
    }

    //    private fun setMax(max: Int) {
//        val queue = SynchronousQueue<Unit>()
//        mMax = max
//        queue.put(Unit)
//        queue.take()
//    }
//


//    private fun setMax(max: Int) {
//        val cd = CountDownLatch(2)
//        mMax = max
//        cd.countDown()
//    }

//    private fun setMax(max: Int) {
//        val cb = CyclicBarrier(3)
//        mMax = max
//        cb.await()
//    }

    private fun sp2px(sp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics
        )
    }

    private fun dip2px(dip: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), resources.displayMetrics
        )
    }

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)

    companion object {
        private const val TAG = "ProgressBar"
    }
}