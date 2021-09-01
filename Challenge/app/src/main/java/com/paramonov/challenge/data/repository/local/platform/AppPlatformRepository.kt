package com.paramonov.challenge.data.repository.local.platform

import android.content.Context
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import java.io.*

class AppPlatformRepository(private val context: Context) : PlatformRepository {

    companion object {
        private val TAG = AppPlatformRepository::class.java.simpleName
    }

    override fun saveDataToPlatformStorage(inStream: InputStream, fileUri: File) {
        try {
            val outStream = FileOutputStream(fileUri)
            inStream.use { inStr ->
                outStream.use { outStr ->
                    inStr.copyTo(outStr)
                }
            }
        } catch (ex: Exception) {
            Log.d(TAG, ex.toString())
        }
    }

    override fun getStoragePath(): File? {
        return context.getExternalFilesDir(DIRECTORY_DOWNLOADS)
    }
}