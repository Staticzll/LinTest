package com.tianjuan.workmanger.kotlin.activityresult

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.savedstate.SavedStateRegistry

/**
 * created by zll on 2024/2/29 14:14
 */

class MyLifecycleObserver(private val registry: ActivityResultRegistry) : DefaultLifecycleObserver {
    lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        registry.register("key", owner, ActivityResultContracts.GetContent()) { uri ->

        }
    }

    fun selectImage() {
        getContent.launch("image?*")
    }
}

class MyFragment : Fragment() {
    lateinit var observer: MyLifecycleObserver;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = MyLifecycleObserver(requireActivity().activityResultRegistry)

//        click{
//          observer.selectImage()
//        }
    }

}