package com.lin.test.other

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.savedstate.SavedStateRegistry
import com.lin.test.databinding.ActivityMainBinding

class MainActivity3 : AppCompatActivity(), SavedStateRegistry.SavedStateProvider {
    //private var searchManager = SearchManager(this)
//    private val viewModel: MyViewModel by viewModels {
//        MyViewModel.factory
//    }

//    private val viewModel: MyViewModel by viewModels {
//        MyViewModel.provideFactory(this)
//    }

    private val viewModel: MyViewModel by viewModels()

    private var query: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedStateRegistry.registerSavedStateProvider(SearchManager.PROVIDER, this)
        val state = savedStateRegistry.consumeRestoredStateForKey(SearchManager.PROVIDER)
        query = state?.getString(SearchManager.QUERY)

        //binding.textView.text = query

        Handler().postDelayed({
            query = "abc"
        }, 2000)
    }

    override fun saveState(): Bundle {
        return bundleOf(SearchManager.QUERY to query)
    }
}