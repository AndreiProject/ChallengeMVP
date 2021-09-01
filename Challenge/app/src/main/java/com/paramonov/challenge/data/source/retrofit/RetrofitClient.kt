package com.paramonov.challenge.data.source.retrofit

import com.paramonov.challenge.data.repository.remote.retrofit.service.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://base_domain.ru"

    private var retrofit: Retrofit? = null

    fun getService(): RetrofitService {
        if (retrofit == null) {

            val builder = OkHttpClient().newBuilder()

            retrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(RetrofitService::class.java)
    }
}