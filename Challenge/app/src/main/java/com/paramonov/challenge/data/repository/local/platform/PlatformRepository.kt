package com.paramonov.challenge.data.repository.local.platform

import java.io.File
import java.io.InputStream

interface PlatformRepository {
    fun saveDataToPlatformStorage(inStream: InputStream, fileUri: File)
    fun getStoragePath(): File?
}