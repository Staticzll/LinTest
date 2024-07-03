package com.lin.test.slidingmenu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lin.test.R
import com.lin.test.base.BaseActivity
import com.lin.test.databinding.ActivitySlidingMenuBinding

class SlidingMenuActivity : BaseActivity<ActivitySlidingMenuBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun inflateView(): ActivitySlidingMenuBinding {
        return ActivitySlidingMenuBinding.inflate(layoutInflater)
    }
}