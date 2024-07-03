package com.lin.test.view

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.HorizontalScrollView
import androidx.core.view.ViewCompat
import com.lin.test.R

/**
 * created by zll on 2024/5/30 15:54
 */
class SlidingMenu(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    HorizontalScrollView(context, attr, defStyleAttr) {
    private val mMenuWidth: Int
    private var mContentView: View? = null
    private var mMenuView: View? = null
    private var mGestureDetector: GestureDetector
    private var mMenuIsOpen = false
    private var isIntercept = false


    private val mGestureListener = object : SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float
        ): Boolean {
            Log.e("TAG", "velocityX: $velocityX velocityY: $velocityY")
            // 打开的时候往右边快速滑动切换（关闭），关闭的时候往左边快速滑动切换（打开）
            // 快速往左边滑动的时候是一个负数，往右边滑动的时候是一个正数
            if (mMenuIsOpen) {
                //打开的时候往左边快速滑动切换（关闭）
                if (velocityX < 0) {
                    closeMenu()
                    return true
                }
            } else {
                //关闭的时候往右边快速滑动切换（打开）
                if (velocityX > 0) {
                    openMenu()
                    return true
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    init {
        val array = context.obtainStyledAttributes(attr, R.styleable.SlidingMenu)

        val rightMargin = array.getDimensionPixelSize(
            R.styleable.SlidingMenu_menuRightMargin, dip2px(50)
        )
        mMenuWidth = (getScreenWidth(context) - rightMargin).toInt()
        array.recycle()

        mGestureDetector = GestureDetector(context, mGestureListener)
    }

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null) {}


    override fun onFinishInflate() {
        // 这个方式是布局解析完毕也就是XML布局文件解析完毕
        super.onFinishInflate()
        val container = getChildAt(0) as ViewGroup

        val childCount = container.childCount
        if (childCount != 2) {
            throw RuntimeException("只能放置2个子View!")
        }

        mMenuView = container.getChildAt(0).apply {
            val menuParams = layoutParams
            menuParams.width = mMenuWidth
            layoutParams = menuParams
        }

        mContentView = container.getChildAt(1).apply {
            val contentParams = layoutParams
            contentParams.width = getScreenWidth(context)
            layoutParams = contentParams
        }
    }


    // 4，处理右边的缩放，左边的缩放和透明度，需要不断的获取当前滚动的位置
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        Log.e("TAG", "onScrollChanged: l: $l  mMenuWidth： $mMenuWidth") // 变化是 mMenuRight ->0
        // 算一个梯度值  手指从右到左滑动数值会递增，直到数值等于menu的宽度
        val scale = 1f * l / mMenuWidth // scale 是从1 ->0
        Log.e("TAG", "scale: $scale")
        // 右边的缩放:最小 0.7f 最大是1f ,内容完全显示时scale等于1
        val rightScale = 0.7f + 0.3f * scale
        Log.e("TAG", "rightScale: $rightScale")
        // 设置右边缩放,默认是以中心点缩放
        // 设置缩放的中心点
        mContentView?.let {
            ViewCompat.setPivotX(it, 0f)
            ViewCompat.setPivotY(it, it.measuredHeight / 2f)
            ViewCompat.setScaleX(it, rightScale)
            ViewCompat.setScaleY(it, rightScale)
        }

        // 菜单的缩放和透明度
        // 透明是 半透明到完全透明 0.7f - 1.0f
        // 缩放 0.7f - 1.0f
        // 内容完全显示时，scale等于1，菜单完全显示时，scale等于0
        val leftAlpha = 0.5f + (1 - scale) * 0.5f
        val leftScale = 0.7f + (1 - scale) * 0.3f
        mMenuView?.let {
            ViewCompat.setAlpha(it, leftAlpha)
            ViewCompat.setScaleX(it, leftScale)
            ViewCompat.setScaleY(it, leftScale)
            // 抽屉，平移
            ViewCompat.setTranslationX(it, 0.2f * l)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        // 2, 初始化进来是关闭的
        scrollTo(mMenuWidth, 0)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        isIntercept = false
        // 2，处理事件拦截+ViewGroup事件分发的源码实践
        // 当菜单打开的时候，手指触摸右边内容部分需要关闭菜单，还需要拦截事件（打开情况下点击内容页不会响应点击事件）
        if (mMenuIsOpen) {
            val currentX = ev.x
            if (currentX > mMenuWidth) {
                // 1. 关闭菜单
                closeMenu()
                // 2, 子view b不需要响应任何事件（点击和触摸），拦截子view的事件
                isIntercept = true
                return true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


    // 3，手指抬起是二选一，要么关闭要么打开
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (isIntercept) return true
        // 快速滑动触发了下面的就不要执行了
        if (mGestureDetector.onTouchEvent(ev)) return true
        // 1，获取手指滑动的速率，当期大于指定值就认为是快速滑动，GestureDetector(系统提供好的类)
        if (ev.action == MotionEvent.ACTION_UP) {
            // 只需要管手指抬起，根据我们当前滚动的距离来判断
            val currentScrollX = scrollX
            if (currentScrollX > mMenuWidth / 2) {
                // 关闭
                closeMenu()
            } else {
                // 打开
                openMenu()
            }

            // 确保super.onTouchEvent()不会执行
            return true

        }
        return super.onTouchEvent(ev)
    }

    private fun openMenu() {
        smoothScrollTo(0, 0)
        mMenuIsOpen = true
    }

    private fun closeMenu() {
        smoothScrollTo(mMenuWidth, 0)
        mMenuIsOpen = false
    }


    private fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    private fun dip2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics
        ).toInt()
    }
}