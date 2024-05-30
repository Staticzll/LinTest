package com.lin.test.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lin.test.databinding.FragmentItemBinding

/**
 * created by zll on 2024/5/9 10:11
 */
class ItemFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentItemBinding.inflate(inflater, container, false)
        val title = arguments?.getString(ARG_TITLE)
        binding.textView.text = title
        return binding.root
    }


    companion object {
        private const val ARG_TITLE = "title"
        fun newInstance(title: String): ItemFragment {
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            val fragment = ItemFragment()
            fragment.arguments = args
            return fragment
        }
    }
}