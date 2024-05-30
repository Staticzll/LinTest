package com.lin.test.fragmentfactory

import androidx.fragment.app.FragmentFactory

/**
 * created by zll on 2024/1/31 18:08
 */
class MyFragmentFactory(val testRepository: TestRepository) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (loadFragmentClass(classLoader, className)) {
            TestFragment::class.java -> TestFragment(testRepository)
            else -> super.instantiate(classLoader, className)
        }
}