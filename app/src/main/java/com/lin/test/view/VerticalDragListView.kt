package com.lin.test.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView
import androidx.core.widget.ListViewCompat
import androidx.customview.widget.ViewDragHelper

/**
 * created by zll on 2024/7/2 10:49
 */
class VerticalDragListView(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attr, defStyleAttr) {
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context) : this(context, null) {}


    private var mDragListView: View? = null
    private var mViewDragHelper: ViewDragHelper

    // 1.拖动我们的子view
    private val mDragHelperCallback: ViewDragHelper.Callback

    // 后面菜单的高度
    private var mMenuHeight = 0

    // 菜单是否打开
    private var mMenuIsOpen = false

    init {
        mDragHelperCallback = getDragHelperCallback()
        mViewDragHelper = ViewDragHelper.create(this, mDragHelperCallback)
    }

    private fun getDragHelperCallback() = object : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {

            // 指定我的子view是否可以拖动，就是child
            // 只能是列表拖动
            // 2.1 后面不能拖动
            return mDragListView == child
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            // 垂直拖动移动的位置
            // 2.3 垂直拖动的范围只能是后面的菜单 view的高度
            var newTop = top
            if (newTop <= 0) {
                newTop = 0
            }
            if (newTop > mMenuHeight) {
                newTop = mMenuHeight
            }
            return newTop
        }

        // 2.4 手指松开的时候两者选其一，要么打开要么关闭
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            Log.e("TAG", "onViewReleased:  mMenuHeight: $mMenuHeight  yvel: $yvel")
            if (releasedChild != mDragListView) return
            mDragListView?.let {
                Log.e("TAG", "top:  ${it.top}")
                if (it.top > mMenuHeight / 2) {
                    // 滚动到菜单的高度（打开）
                    mViewDragHelper.settleCapturedViewAt(0, mMenuHeight)
                    mMenuIsOpen = true
                } else {
                    // 滚动到0的位置（关闭）
                    mViewDragHelper.settleCapturedViewAt(0, 0)
                    mMenuIsOpen = false
                }

                invalidate()
            }
        }

        // 2.2 列表只能垂直拖动
        //        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
        //            // 水平拖动移动的位置
        //            return left
        //        }
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val menuView = getChildAt(0)
//        mMenuHeight = menuView.measuredHeight
//    }

//    override fun addView(child: View?) {
//        super.addView(child)
//    }
//
//    override fun setVisibility(visibility: Int) {
//        super.setVisibility(visibility)
//    }

    /**
     * 响应滚动
     */
    override fun computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            val menuView = getChildAt(0)
            mMenuHeight = menuView.measuredHeight
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper.processTouchEvent(event)
        return true
    }

    private var mDownY = 0f

    // because ACTION_DOWN was not received for this pointer before ACTION_MOVE
    // VDLV.onInterceptTouchEvent().Down ->LV.onTouch()->
    // VDLV.onInterceptTouchEvent().Move -> VDLV.onTouchEvent.Move
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // 菜单打开要拦截
        if (mMenuIsOpen) return true
        //SwipeRefreshLayout
        // 向下滑动不要给ListView做处理
        // 谁拦截谁 父view 拦截子view ,但是子view可以调这个方法
        // requestDisallowInterceptTouchEvent 请求父view不要拦截，改变的其实就是mGroupFlags的值
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownY = ev.y
                // 让dragHelper 拿到一个完整的事件
                mViewDragHelper.processTouchEvent(ev)
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaY = ev.y - mDownY
                if (deltaY > 0 && !canChildScrollUp()) {
                    // 向下滑动&&滚动到顶部,不让listView做处理
                    return true
                }
            }
        }

        return super.onInterceptTouchEvent(ev)
    }

    fun canChildScrollUp(): Boolean {
        return mDragListView?.let {
            if (it is ListView) {
                ListViewCompat.canScrollList(it, -1)
            } else it.canScrollVertically(-1)
        } ?: false
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 2) {
            throw RuntimeException("VerticalDragListView 只能包含两个子布局")
        }

        mDragListView = getChildAt(1)

    }


}