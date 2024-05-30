package com.lin.test.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.lin.test.R

/**
 * created by zll on 2024/5/9 10:49
 */
class QQStepView(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    View(context, attr, defStyleAttr) {
    private var mOuterColor = Color.RED
    private var mInnerColor = Color.BLUE
    private var mBorderWidth = 2f
    private var mStepTextSize = 26f
    private var mStepTextColor = Color.BLACK

    private var mOutPaint: Paint
    private var mInnerPaint: Paint
    private var mTextPaint: Paint
    private var mCurrentStep = 0f
    private var mStepMax = 0f

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)

    init {
        // 1,分析效果
        // 2,确定自定义属性，编写attrs.xml
        // 3,在布局中使用
        // 4,在自定义view中获取自定义属性

        val array = context.obtainStyledAttributes(attr, R.styleable.QQStepView)
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor)
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor)
        mBorderWidth = array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth)
        mStepTextSize = array.getDimension(R.styleable.QQStepView_stepTextSize, mStepTextSize)
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor)
        array.recycle()

        mOutPaint = Paint()
        mOutPaint.color = mOuterColor
        mOutPaint.isAntiAlias = true
        mOutPaint.strokeWidth = mBorderWidth
        mOutPaint.style = Paint.Style.STROKE

        mInnerPaint = Paint()
        mInnerPaint.color = mInnerColor
        mInnerPaint.isAntiAlias = true
        mInnerPaint.strokeWidth = mBorderWidth
        mInnerPaint.style = Paint.Style.STROKE

        mTextPaint = Paint()
        mTextPaint.color = mStepTextColor
        mTextPaint.textSize = mStepTextSize
        mTextPaint.isAntiAlias = true
    }

    // 5,onMeasure()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 调用者在布局文件中可能 wrap_content
        // 获取模式 AT_MOST 40DP
        // 宽度高度不一致 取最小值，确保是个正方形
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val realSize = if (width > height) height else width
        setMeasuredDimension(realSize, realSize)
    }


    // 6,画外圆弧，内圆弧，文字
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 6.1 画外弧
        val rectF = RectF(
            mBorderWidth / 2, mBorderWidth / 2, width - mBorderWidth / 2, height - mBorderWidth / 2
        )
        canvas.drawArc(rectF, 135f, 270f, false, mOutPaint)

        // 6.2 画内弧
        val sweepAngle = mCurrentStep / mStepMax.toFloat()
        canvas.drawArc(rectF, 135f, sweepAngle * 270, false, mInnerPaint)

        // 6.3 画文字
        val stepText = mCurrentStep.toString()
        val bounds = Rect()
        mTextPaint.getTextBounds(stepText, 0, stepText.length, bounds)
        val dx = (width / 2 - bounds.width() / 2).toFloat()
        val fontMetrics = mTextPaint.getFontMetrics()
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseLine = height / 2 + dy
        canvas.drawText(stepText, dx, baseLine, mTextPaint)
    }

    // 7，其他
    fun setStepMax(stepMax: Float) {
        mStepMax = stepMax
    }

    fun setCurrentStep(currentStep: Float) {
        mCurrentStep = currentStep
        invalidate()
    }
}