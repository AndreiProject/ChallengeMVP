package com.paramonov.challenge.data.repository.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "challenge")
data class Challenge(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "img_url") var imgUrl: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "rating") var rating: String = ""
)