package com.paramonov.challenge.data.repository.model

import androidx.annotation.NonNull
import androidx.room.*
import com.google.firebase.database.Exclude

@Entity(tableName = "category")
data class Category(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "img_url") var imgUrl: String = "",
    @ColumnInfo(name = "name") var name: String = ""
) {
    @get:Exclude
    @Ignore
    var items: List<Challenge>? = null
}