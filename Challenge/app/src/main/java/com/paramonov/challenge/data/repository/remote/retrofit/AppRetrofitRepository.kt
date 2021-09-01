package com.paramonov.challenge.data.repository.remote.retrofit

import com.paramonov.challenge.data.repository.remote.retrofit.service.RetrofitService

class AppRetrofitRepository(private val service: RetrofitService) : RetrofitRepository {
    override fun getFile(requestUrl: String) = service.getFile(requestUrl)
}