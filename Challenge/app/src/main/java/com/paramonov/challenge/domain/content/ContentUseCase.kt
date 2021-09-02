package com.paramonov.challenge.domain.content

import android.util.Log
import androidx.lifecycle.LiveData
import com.paramonov.challenge.data.repository.local.LocalRepository
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.data.repository.remote.RemoteRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.*
import java.io.*

class ContentUseCase(
    private val rmRepository: RemoteRepository,
    private val lcRepository: LocalRepository
) :
    ContentUseCaseContract {

    companion object {
        private val TAG = ContentUseCase::class.java.simpleName
    }

    override fun removeChallenges(categoryId: String, challengeId: String) {
        rmRepository.removeChallenges(categoryId, challengeId)
    }

    override fun getChallenges(categoryId: String, debounceMs: Long): Flowable<List<Challenge>> {
        return rmRepository.getChallenges(categoryId, debounceMs)
    }

    override fun getAllCategories(): Flowable<List<Category>> {
        return rmRepository.getAllCategories()
    }

    override fun getCategoriesWithChallenges(): Single<List<Category>> {
        return Single.fromCallable {
            val userId = rmRepository.getEmail()
            lcRepository.getCategoriesWithChallenges(userId)
        }.subscribeOn(Schedulers.io())
    }

    override fun insertCategoryWithChallenges(category: Category) {
        Single.just(category)
            .subscribeOn(Schedulers.io())
            .subscribe({
                val userId = rmRepository.getEmail()
                lcRepository.insertCategoryWithChallenges(category, userId)
            }, {
                Log.e(TAG, it.toString())
            })
    }

    override fun saveImg(imgURL: String) {
        val response = rmRepository.getFile(imgURL)
        response.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                response?.body()?.let {
                    val inStream = it.byteStream()
                    val uri = getImgUri(imgURL)
                    lcRepository.saveDataToPlatformStorage(inStream, uri)
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }
        })
    }

    private fun getImgUri(imgURL: String): File {
        val storagePath = lcRepository.getStoragePath()
        val imgName = imgURL.split("/").last()
        return File(storagePath, imgName)
    }
}