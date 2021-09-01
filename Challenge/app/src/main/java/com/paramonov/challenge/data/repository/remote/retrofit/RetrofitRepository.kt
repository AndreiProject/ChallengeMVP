package com.paramonov.challenge.data.repository.remote.retrofit

import okhttp3.ResponseBody
import retrofit2.Call

interface RetrofitRepository {
    fun getFile(requestUrl: String): Call<ResponseBody>
}