package com.lin.test.other

import android.os.Bundle
import androidx.core.os.bundleOf
import java.io.File

private fun File.saveTempFile() = bundleOf("path" to absoluteFile)

private fun Bundle.restoreTempFile() = if (containsKey("path")) {
    getString("path")?.let { File(it) }
} else {
    null
}

