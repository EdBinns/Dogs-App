package com.edbinns.dogsapp.domain

import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.repository.FavoriteRepository
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(private val repository : FavoriteRepository) {
    suspend operator fun invoke(data : Favorite) = repository.update(data)
}