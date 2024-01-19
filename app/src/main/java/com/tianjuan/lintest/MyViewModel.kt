package com.tianjuan.lintest

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.launch

/**
 * created by zll on 2023/12/1 11:39
 */
class MyViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

//    @Suppress("UNCHECKED_CAST")
//    companion object {
//        val factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//                val application =
//                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
//                val savedStateHandle = extras.createSavedStateHandle()
//
//                return MyViewModel(savedStateHandle) as T
//            }
//        }
//    }


//    companion object {
//        val factory = viewModelFactory {
//            initializer {
//                    val savedStateHandle = createSavedStateHandle()
//                    MyViewModel(savedStateHandle)
//            }
//        }
//    }

//    companion object {
////        fun provideFactory(app:Application) = object :ViewModelProvider.AndroidViewModelFactory(app){
////            override fun <T : ViewModel> create(modelClass: Class<T>): T {
////                return super.create(modelClass)
////            }
////        }
//
//        fun provideFactory() = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return super.create(modelClass)
//            }
//        }
//    }
//}

    init {
        viewModelScope.launch {

        }
    }


    companion object {
        fun provideFactory(
            owner: SavedStateRegistryOwner,
            defaultArg: Bundle? = null
        ) = object : AbstractSavedStateViewModelFactory(owner, defaultArg) {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return MyViewModel(handle) as T
            }

        }
    }
}