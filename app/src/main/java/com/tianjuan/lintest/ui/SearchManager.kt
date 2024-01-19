package com.tianjuan.lintest.ui

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner

/**
 * created by zll on 2023/11/30 17:51
 */
class SearchManager(registryOwner: SavedStateRegistryOwner) :
    SavedStateRegistry.SavedStateProvider {
    private var query: String? = null

    init {
        // 你好
        registryOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                val registry = registryOwner.savedStateRegistry
                registry.registerSavedStateProvider(PROVIDER, this)
                val state = registry.consumeRestoredStateForKey(PROVIDER)
                query = state?.getString(QUERY)
                Log.i("TAG", "query:  $query")
            }
        })
    }

    override fun saveState(): Bundle {
        Log.i("TAG", "saveState: " + query)
        return bundleOf(QUERY to query)
    }

    companion object {
        const val PROVIDER = "provider"
        const val QUERY = "query"
    }
}