package com.paramonov.challenge.data.repository.remote.firebase

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ValueEventListener
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import com.paramonov.challenge.ui.feature.login.Result

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

    override fun auth(email: String, password: String): LiveData<Result> =
        MutableLiveData<Result>().apply {
            value = Result.Authorization(false)
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    value = Result.Authorization(true)
                }
                .addOnFailureListener {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            value = Result.Authorization(true)
                        }
                        .addOnFailureListener { value = Result.Error(it) }
                }
        }

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

    override fun getUser(): LiveData<User> {
        return MutableLiveData<User>().apply {
            val groupUsersRef = fbDatabase.reference.child(
                GROUP_USERS
            )
            val currentUserId = auth.currentUser?.uid.toString()
            val userRef = groupUsersRef.child(currentUserId)

            val listener = object : ValueEventListener {
                override fun onDataChange(data: DataSnapshot) {
                    val user = data.getValue(User::class.java)
                    user?.let {
                        value = user
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, error.toString())
                }
            }
            userRef.addValueEventListener(listener)
        }
    }

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

    override fun getAllCategories(): LiveData<List<Category>> {
        return MutableLiveData<List<Category>>().apply {
            val categoriesRef = fbDatabase.reference.child(
                GROUP_CATEGORIES
            )

            val listener = object : ValueEventListener {
                override fun onDataChange(data: DataSnapshot) {
                    val listCategory = mutableListOf<Category>()
                    for (snapshot in data.children) {
                        val category = snapshot.getValue(Category::class.java)
                        category?.let {
                            listCategory.add(it)
                        }
                    }
                    value = listCategory
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, error.toString())
                }
            }
            categoriesRef.addValueEventListener(listener)
        }
    }

// android stream api (file storage)

//    fun getAllCategories(): LiveData<List<Category>> {
//        return MutableLiveData<List<Category>>().apply {
//
//            val c1 = Category(
//                "1sd5adw5as5d",
//                "https://www3.paratoptan.org/img/p/1/40448-home_default.jpg",
//                "Спорт"
//            )
//
//            val c2 = Category(
//                "sd8a7wa4dda7",
//                "https://podarochnieknigi.ru/images/shop_categories/13_350x350.jpg",
//                "Книги"
//            )
//
//            val c3 = Category(
//                "si9d9a4dda7",
//                "https://d2hg8ctx8thzji.cloudfront.net/resultsbee.com/wp-content/uploads/2020/11/6_stellar_gaming_deals_for_Black_Friday_and_Cyber_Monday_1606214162-350x350.jpg",
//                "Компьютерные игры"
//            )
//
//            val c4 = Category(
//                "s7a655a4s5a7",
//                "https://www.turk5.com/ilanlar/wp-content/uploads/2020/12/dizi_cevirisi_film_cevirisi-350x350.jpg",
//                "Фильмы"
//            )
//
//
//            val listUser = mutableListOf(c1, c2, c3, c4)
//            value = listUser
//
//            val ref = db.reference.child(GROUP_CATEGORIES)
//            ref.child(c1.id).setValue(c1)
//            ref.child(c2.id).setValue(c2)
//            ref.child(c3.id).setValue(c3)
//            ref.child(c4.id).setValue(c4)
//
//
//            val list1 = listOf(
//                Challenge(
//                    "we5sda8we8q",
//                    "https://u-stena.ru/upload/iblock/43a/43a908d21f68cef6cedb33d2c34e1f71.jpg",
//                    "Сноуборд",
//                    "8.4"
//                ),
//                Challenge(
//                    "we5sd5s2a78",
//                    "https://weonlydothisonce.com/wp-content/uploads/2016/01/depositphotos_22312629_xs-150x150.jpg",
//                    "Бег",
//                    "7.4"
//                ),
//                Challenge(
//                    "a9dw8f8m898",
//                    "http://www.laf1encontinu.com/wp-content/uploads/2014/11/Q8T1385-150x150.jpg",
//                    "F1",
//                    "9.3"
//                ),
//                Challenge(
//                    "7ass76w6da6",
//                    "http://sportregime.ru/wp-content/uploads/2019/09/9ec00e5614cddc00f01a06d4f66d1b3b-150x150.jpg",
//                    "Хоккей",
//                    "8.1"
//                ),
//                Challenge(
//                    "09da9w898s8",
//                    "https://joefavorito.com/wp-content/uploads/2015/01/17iht-soccer17a-superJumbo-150x150.jpg",
//                    "Футбол",
//                    "8.7"
//                ),
//                Challenge(
//                    "ds89as88d9x",
//                    "http://www.vitonaccitrainer.it/wp-content/uploads/2020/01/nuoto-in-piscina-150x150.jpg",
//                    "Плавание",
//                    "9.0"
//                ),
//                Challenge(
//                    "s879a8s7d8a",
//                    "https://www.propingpong.ru/photos/8/icons/ppp000127_tn.jpg",
//                    "Настольный теннис",
//                    "7.7"
//                )
//            )
//
//            val list2 = listOf(
//                Challenge(
//                    "sd2s8987da7",
//                    "https://lingvya.ru/wp-content/uploads/film-vlastelin-kolec-150x150.jpg",
//                    "Властелин колец",
//                    "9.4"
//                ),
//                Challenge(
//                    "9d6gb67fg6d",
//                    "https://mbs-deluxe.ru/wp-content/uploads/69975079_1296401613_2010_8_30_39597656viento-150x150.jpg",
//                    "Унесенные ветром",
//                    "8.4"
//                ),
//                Challenge(
//                    "s8asc9x999b",
//                    "https://im0-tub-ru.yandex.net/i?id=41f04ccd4c44651d46f32229f2e29bda-sr&n=13",
//                    "Над пропастью во ржи",
//                    "7.3"
//                ),
//                Challenge(
//                    "c0zc0cz09d0",
//                    "https://i.pinimg.com/150x150/0a/d3/79/0ad37916feab0db471d9788dde0a4582.jpg",
//                    "365 градусов по фаренгейту",
//                    "7.1"
//                )
//            )
//
//
//            val list3 = listOf(
//                Challenge(
//                    "s7sd77e6a7",
//                    "https://i.pinimg.com/736x/76/87/7b/76877b4872f623c55fb56f6974169975.jpg",
//                    "Red dead redemption 2",
//                    "8.7"
//                ),
//                Challenge(
//                    "da7sda7w88",
//                    "https://sproshu.com/uploads/ava/3/ava8192.jpg",
//                    "GTA 5",
//                    "8.9"
//                )
//            )
//
//
//            val list4 = listOf(
//                Challenge(
//                    "s7x6zs66a77",
//                    "https://wallscloud.net/uploads/cache/1266658504/interstellar-2014-wK9X-150x150-MM-90.jpg",
//                    "Интерстеллар",
//                    "8.7"
//                ),
//                Challenge(
//                    "ds8df5ds4s",
//                    "http://blog.hitline.org.ua/wp-content/uploads/2011/01/14-150x150.jpg",
//                    "Начало",
//                    "8.9"
//                )
//            )
//
//            val ref2 = db.reference.child(GROUP_CHALLENGES)
//            for (x in list1) {
//                ref2.child(c1.id).child(x.id).setValue(x)
//            }
//            for (x in list2) {
//                ref2.child(c2.id).child(x.id).setValue(x)
//            }
//            for (x in list3) {
//                ref2.child(c3.id).child(x.id).setValue(x)
//            }
//            for (x in list4) {
//                ref2.child(c4.id).child(x.id).setValue(x)
//            }
//        }
//    }
}