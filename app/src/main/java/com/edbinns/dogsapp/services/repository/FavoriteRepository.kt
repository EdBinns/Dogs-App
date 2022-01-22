package com.edbinns.dogsapp.services.repository

import androidx.lifecycle.LiveData
import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.room.FavoriteDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val favoriteDao: FavoriteDao){

    val getAll: LiveData<List<Favorite>> = favoriteDao.getAll()

    suspend fun addFavorite(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    suspend  fun getByUrl(imageURL: String):Favorite{
        return  favoriteDao.getByUrl(imageURL)
    }

    suspend  fun update(favorites: Favorite){
        favoriteDao.update(favorites)
    }

    suspend fun delete(favorites: Favorite){
        favoriteDao.delete(favorites)
    }
}