package com.lin.test.other

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import com.lin.test.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * created by zll on 2023/12/1 14:25
 */
class MyFragment : Fragment() {
    //private val viewModel: MyViewModel by viewModels()
    //private val viewModel: MyViewModel by viewModels(ownerProducer = { requireParentFragment() })
    //private val viewModel: MyViewModel by activityViewModels()
    //private val viewModel: MyViewModel by navGraphViewModels(R.id.nav_graph)
    private val viewModel: MyViewModel by viewModels({ findNavController().getBackStackEntry(R.id.nav_graph) })

    init {
        lifecycleScope.launch {
            whenStarted {
                withContext(Dispatchers.IO) {

                }
            }
        }

        lifecycleScope.launchWhenStarted {
            try {

            } finally {
                if (lifecycle.currentState >= Lifecycle.State.STARTED) {

                }

            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Default) {

            }
        }

        lifecycle.coroutineScope.launch {

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            flow<Int> { emit(1) }
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {

                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val flow = flow { emit(1) }
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect {

                }

                flow.collect {

                }
            }
        }

    }

}