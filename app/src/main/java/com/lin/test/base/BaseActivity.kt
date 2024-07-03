package com.lin.test.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * created by zll on 2024/5/30 16:05
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null

    val binding get() = _binding ?: throw RuntimeException("no inflate view")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateView()
        setContentView(_binding!!.root)
    }

    abstract fun inflateView(): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}