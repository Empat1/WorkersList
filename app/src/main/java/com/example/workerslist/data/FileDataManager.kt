package com.example.workerslist.data

import android.app.Application
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import javax.inject.Inject

class FileDataManager @Inject constructor(private val application: Application) {

    private fun getFile(): File {
        val path = application.filesDir
        val letDirectory = File(path, "LET")
        letDirectory.mkdirs()
        return File(letDirectory, "Records.txt")
    }

    suspend fun saveFile(text: String) {
        val file = getFile()

        FileOutputStream(file).use {
            it.write(text.toByteArray())
            Log.d(TAG, "save text $text")
        }
    }

    suspend fun readFile(): String {
        val file = getFile()

        val inputAsString = try {
            FileInputStream(file).bufferedReader().use {
                it.readText()
            }
        } catch (e: FileNotFoundException) {
            Log.d(TAG, "file not find")
            ""
        }


        Log.d(TAG, "get file text $inputAsString")
        return inputAsString
    }

    companion object {
        private const val TAG = "FileDataMManager"
    }
}