package com.lin.test.viewpager

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.lin.test.databinding.ActivityViewPagerBinding
import com.lin.test.view.ColorTrackTextView

/**
 * created by zll on 2024/5/9 10:07
 */
class ViewPagerActivity : AppCompatActivity() {
    private val items = arrayOf("直播", "推荐", "视频", "图片", "段子", "精华")
    private val mIndicators by lazy { mutableListOf<ColorTrackTextView>() }
    private lateinit var mIndicatorContainer: LinearLayout
    private lateinit var mViewPage: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityViewPagerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mIndicatorContainer = binding.indicatorContainer
        mViewPage = binding.viewpager
        initViewPager()
        initIndicator()
    }

    private fun initIndicator() {
        items.forEach {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.weight = 1f
            val colorTrackTextView = ColorTrackTextView(this)
            colorTrackTextView.textSize = 28f
            colorTrackTextView.text = it
            colorTrackTextView.setChangeColor(Color.RED)
            colorTrackTextView.layoutParams = layoutParams
            mIndicatorContainer.addView(colorTrackTextView)
            mIndicators.add(colorTrackTextView)
        }

    }

    private fun initViewPager() {
        mViewPage.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount() = items.size

            override fun getItem(position: Int): Fragment {
                return ItemFragment.newInstance(items[position])
            }
        }

        mViewPage.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                Log.e("TAG", "position -> $position positionOffset -> $positionOffset")
                // position 代表当前位置
                // positionOffset 代表滚动的 0 - 1 百分比
                val left = mIndicators[position]
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LIFT)
                left.setCurrentProgress(1 - positionOffset)

                try {
                    val right = mIndicators[position + 1]
                    right.setDirection(ColorTrackTextView.Direction.LIFT_TO_RIGHT)
                    right.setCurrentProgress(positionOffset)
                } catch (ignore: Exception) {
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

}