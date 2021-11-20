package com.edbinns.dogsapp.services.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edbinns.dogsapp.models.Favorite


@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class FavoritesDB: RoomDatabase(){
    abstract fun favoritesDao(): FavoriteDao
}