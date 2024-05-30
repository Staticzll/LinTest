package com.lin.test.other

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

/**
 * created by zll on 2023/12/1 14:49
 */
class SavedStateViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val filterData: LiveData<List<String>> = savedStateHandle.getLiveData<String>("query")
        .switchMap { query ->
            getFilteredData(query)
        }

    val filterData2: Flow<List<String>> = savedStateHandle.getStateFlow<String>("query","abc")
        .flatMapLatest { query ->
            MutableStateFlow(emptyList())
        }


    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }

    private fun getFilteredData(str: String): MutableLiveData<List<String>> {
        return MutableLiveData<List<String>>()
    }

}