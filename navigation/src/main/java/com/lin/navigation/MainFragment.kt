package com.lin.navigation

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.lin.navigation.databinding.FragmentMainBinding

/**
 * created by zll on 2024/1/17 17:09
 */
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.findNavController().navigate(R.id.main_to_play)

        binding.button2.setOnClickListener {
            // 全局操作
            //view.findNavController().navigate(R.id.action_global_play_fragment)
        }

        // 导航到隐式深层链接目的地
//        val request = NavDeepLinkRequest.Builder
//            .fromUri("android-app://androidx.navigation.app/profile".toUri())
//            .build()
//        findNavController().navigate(request)
    }

}