package com.paramonov.challenge.data.repository.remote.retrofit.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET
    fun getFile(@Url requestUrl: String): Call<ResponseBody>
}