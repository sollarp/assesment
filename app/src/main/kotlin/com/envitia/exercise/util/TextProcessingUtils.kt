package com.envitia.exercise.util

import android.content.Context
import java.io.IOException

object TextProcessingUtils {
    fun saveToFile(text: String, context: Context) {
        try {
            context.openFileOutput(Constants.TEXT_FILE_NAME, Context.MODE_APPEND).use { outputStream ->
                outputStream.write("$text\n\n".toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}