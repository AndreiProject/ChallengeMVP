package com.paramonov.challenge.data.repository.remote.firebase

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ValueEventListener
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableOnSubscribe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.RuntimeException

class AppFirebaseRepository(
    private val auth: FirebaseAuth,
    private val fbDatabase: FirebaseDatabase
) : FirebaseRepository {

    companion object {
        private val TAG = AppFirebaseRepository::class.java.simpleName
        private const val GROUP_CATEGORIES = "categories"
        private const val GROUP_CHALLENGES = "challenges"
        private const val GROUP_USERS = "users"
    }

    override fun checkAuth(): Boolean = auth.currentUser != null

    override fun auth(email: String, password: String): Single<Boolean> =
        Single.create(SingleOnSubscribe<Boolean> { emitter ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { emitter.onSuccess(true) }
                .addOnFailureListener {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { emitter.onSuccess(true) }
                        .addOnFailureListener { emitter.onError(it) }
                }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getEmail() = auth.currentUser!!.email!!

    override fun logOut() {
        auth.signOut()
    }

    override fun updateUser(user: User) {
        val groupUsersRef = fbDatabase.reference.child(
            GROUP_USERS
        )
        val currentUserId = auth.currentUser?.uid.toString()
        val userRef = groupUsersRef.child(currentUserId)
        userRef.setValue(user)
    }

    override fun getUser(): Flowable<User> = Flowable.create(FlowableOnSubscribe<User> { emitter ->
        val groupUsersRef = fbDatabase.reference.child(GROUP_USERS)
        val currentUserId = auth.currentUser?.uid.toString()
        val userRef = groupUsersRef.child(currentUserId)

        val listener = object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                val user = data.getValue(User::class.java)
                user?.let {
                    emitter.onNext(user)
                } ?: emitter.onError(RuntimeException("User is null"))
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, error.toString())
                emitter.onComplete()
            }
        }
        userRef.addValueEventListener(listener)
    }, BackpressureStrategy.LATEST)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun removeChallenges(categoryId: String, challengeId: String) {
        val groupChallengesRef = fbDatabase.reference.child(
            GROUP_CHALLENGES
        )
        val challengesRef = groupChallengesRef.child(categoryId)
        val challengeRef = challengesRef.child(challengeId)
        challengeRef.removeValue()
    }

    override fun getChallenges(categoryId: String): LiveData<List<Challenge>> {
        return MutableLiveData<List<Challenge>>().apply {
            val groupChallengesRef = fbDatabase.reference.child(
                GROUP_CHALLENGES
            )
            val challengesRef = groupChallengesRef.child(categoryId)

            val listener = object : ValueEventListener {
                override fun onDataChange(data: DataSnapshot) {
                    val listChallenge = mutableListOf<Challenge>()
                    for (snapshot in data.children) {
                        val challenge = snapshot.getValue(Challenge::class.java)
                        challenge?.let {
                            listChallenge.add(it)
                        }
                    }
                    value = listChallenge
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, error.toString())
                }
            }
            challengesRef.addValueEventListener(listener)
        }
    }

    override fun getAllCategories(): Flowable<List<Category>> =
        Flowable.create(FlowableOnSubscribe<List<Category>> {
            val categoriesRef = fbDatabase.reference.child(GROUP_CATEGORIES)
            val listener = object : ValueEventListener {
                override fun onDataChange(data: DataSnapshot) {
                    val listCategory = mutableListOf<Category>()
                    for (snapshot in data.children) {
                        val category = snapshot.getValue(Category::class.java)
                        category?.let {
                            listCategory.add(it)
                        }
                    }
                    it.onNext(listCategory)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, error.toString())
                    it.onComplete()
                }
            }
            categoriesRef.addValueEventListener(listener)
        }, BackpressureStrategy.LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}