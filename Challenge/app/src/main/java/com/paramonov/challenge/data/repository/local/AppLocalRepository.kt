package com.paramonov.challenge.data.repository.local

import com.paramonov.challenge.data.repository.local.platform.PlatformRepository
import com.paramonov.challenge.data.repository.local.room.RoomRepository
import com.paramonov.challenge.data.repository.model.Category
import java.io.*

class AppLocalRepository(
    private val rmRepository: RoomRepository,
    private val plRepository: PlatformRepository
) : LocalRepository {

    override fun getCategoriesWithChallenges(userId: String) = rmRepository.getCategoriesWithChallenges(userId)

    override fun insertCategoryWithChallenges(category: Category, userId: String) {
        rmRepository.insertCategoryWithChallenges(category, userId)
    }

    override fun saveDataToPlatformStorage(inStream: InputStream, fileUri: File) {
        plRepository.saveDataToPlatformStorage(inStream, fileUri)
    }

    override fun getStoragePath() = plRepository.getStoragePath()
}