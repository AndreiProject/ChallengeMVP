package com.paramonov.challenge.data.repository.remote

import com.paramonov.challenge.data.repository.remote.firebase.FirebaseRepository
import com.paramonov.challenge.data.repository.remote.retrofit.RetrofitRepository

interface RemoteRepository : FirebaseRepository, RetrofitRepository