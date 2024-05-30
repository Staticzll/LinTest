package com.lin.test.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.lin.test.R
import java.util.jar.Attributes

/**
 * created by zll on 2024/5/8 11:13
 */
class ColorTrackTextView(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attr, defStyleAttr) {
    private var mOriginalPaint: Paint? = null
    private var mChangePaint: Paint? = null
    private val bounds = Rect()
    private var mCurrentProgress = 0f
    private var mDirection: Direction = Direction.LIFT_TO_RIGHT

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)

    enum class Direction {
        LIFT_TO_RIGHT, RIGHT_TO_LIFT
    }

    fun setDirection(direction: Direction) {
        mDirection = direction
    }

    fun setCurrentProgress(currentProgress: Float) {
        mCurrentProgress = currentProgress
        invalidate()
    }

    init {
        initPaint(context, attr)
    }

    private fun initPaint(context: Context, attr: AttributeSet?) {
        val array = context.obtainStyledAttributes(attr, R.styleable.ColorTrackTextView)
        val originalColor =
            array.getColor(R.styleable.ColorTrackTextView_originalColor, textColors.defaultColor)
        val changeColor =
            array.getColor(R.styleable.ColorTrackTextView_changeColor, textColors.defaultColor)

        mOriginalPaint = getPaintByColor(originalColor)

        mChangePaint = getPaintByColor(changeColor)

        array.recycle()
    }

    private fun getPaintByColor(color: Int): Paint {
        val paint = Paint()
        paint.color = color
        paint.isAntiAlias = true
        paint.isDither = true
        paint.textSize = textSize
        return paint
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)

        // 根据进度把中间值算出来
        val middle = (mCurrentProgress * width).toInt()

        if (mDirection == Direction.LIFT_TO_RIGHT) {
            // 绘制变色
            drawText(canvas, mChangePaint!!, 0, middle)
            // 绘制不变色
            drawText(canvas, mOriginalPaint!!, middle, width)
        } else {
            // 绘制不变色
            drawText(canvas, mChangePaint!!, width - middle, width)
            // 绘制变色
            drawText(canvas, mOriginalPaint!!, 0, width - middle)
        }
    }

    private fun drawText(canvas: Canvas, paint: Paint, start: Int, end: Int) {
        canvas.save()
        val rect = Rect(start, 0, end, height)
        canvas.clipRect(rect)

        val text = text.toString()
        paint.getTextBounds(text, 0, text.length, bounds)
        // 获取字体宽度
        val x = (width / 2 - bounds.width() / 2).toFloat()
        // 基线baseLine
        val fontMetrics = paint.getFontMetrics()
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseLine = height / 2 + dy
        canvas.drawText(text, x, baseLine, paint)
        canvas.restore()
    }

    fun setChangeColor(color: Int) {
        mChangePaint?.color = color
    }

    fun setOriginalColor(color: Int) {
        mOriginalPaint?.color = color
    }

}