package com.tianjuan.workmanger.kotlin.activityresult

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

/**
 * created by zll on 2024/2/29 11:16
 */
class ActivityResult : AppCompatActivity() {


    val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

        }


    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val intent = result.data
            // handle the Intent
        }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        getContent.launch("image/*")

        //startForResult.launch(Intent(this,))
    }

}