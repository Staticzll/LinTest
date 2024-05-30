package com.lin.test.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.sqrt

/**
 * created by zll on 2024/5/11 11:41
 */
class ShapeView(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    View(context, attr, defStyleAttr) {
    private var mCurrentShape: Shape = Shape.CIRCLE
    private var mPaint: Paint

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true

    }

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mCurrentShape) {
            Shape.CIRCLE -> {
                val center = width / 2f
                mPaint.color = Color.YELLOW
                canvas.drawCircle(center, center, center, mPaint)

            }

            Shape.SQUARE -> {
                mPaint.color = Color.BLUE
                canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
            }

            Shape.TRIANGLE -> {
                mPaint.color = Color.RED
                path ?: Path().apply {
                    moveTo(width / 2f, 0f)
                    lineTo(0f, (width/2* sqrt(3.00)).toFloat())
                    lineTo(width.toFloat(), (width/2* sqrt(3.00)).toFloat())
                    close()
                }.also { path = it }
                canvas.drawPath(path!!, mPaint)
            }
        }
    }

    private var path: Path? = null

    fun exchange() {
        when (mCurrentShape) {
            Shape.CIRCLE -> mCurrentShape = Shape.SQUARE

            Shape.SQUARE -> mCurrentShape = Shape.TRIANGLE

            Shape.TRIANGLE -> mCurrentShape = Shape.CIRCLE
        }
        invalidate()
    }

    enum class Shape {
        CIRCLE, SQUARE, TRIANGLE
    }
}