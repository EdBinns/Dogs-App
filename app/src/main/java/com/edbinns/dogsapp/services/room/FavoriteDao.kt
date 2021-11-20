package com.edbinns.dogsapp.services.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.edbinns.dogsapp.models.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAll(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE id=:id")
    suspend fun getByID(id: Int): Favorite

    @Update
    suspend fun update(favorites: Favorite)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorites: Favorite)

    @Delete
    suspend fun delete(favorites: Favorite)
}