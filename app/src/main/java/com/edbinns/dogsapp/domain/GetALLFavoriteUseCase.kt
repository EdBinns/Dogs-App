package com.edbinns.dogsapp.domain

import androidx.lifecycle.LiveData
import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.repository.FavoriteRepository
import javax.inject.Inject

class GetALLFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {

    val getAllFavorite: LiveData<List<Favorite>> =  repository.getAll

}