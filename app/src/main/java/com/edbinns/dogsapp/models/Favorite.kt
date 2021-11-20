package com.edbinns.dogsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imageURL: String,
    val breed: String
)