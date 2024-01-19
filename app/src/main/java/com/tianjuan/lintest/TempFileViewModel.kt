package com.tianjuan.lintest

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.io.File

private fun File.saveTempFile() = bundleOf("path" to absoluteFile)

private fun Bundle.restoreTempFile() = if (containsKey("path")) {
    getString("path")?.let { File(it) }
} else {
    null
}

/**
 * created by zll on 2023/12/4 9:37
 */
class TempFileViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private var tempFile: File? = null

    init {

        val tempFileBundle = savedStateHandle.get<Bundle>("temp_file")
        if (tempFileBundle != null) {
            tempFile = tempFileBundle.restoreTempFile()
        }

        savedStateHandle.setSavedStateProvider("temp_file") {
            tempFile?.saveTempFile() ?: Bundle()
        }
    }

    fun createOrGetTempFile(): File {
        return tempFile ?: File.createTempFile("temp", null).also {
            tempFile = it
        }
    }
}