package com.edbinns.dogsapp.domain

import com.edbinns.dogsapp.models.Favorite
import com.edbinns.dogsapp.services.provider.BreedsProvider
import com.edbinns.dogsapp.services.repository.DogsRepository
import com.edbinns.dogsapp.services.repository.FavoriteRepository
import com.edbinns.dogsapp.utils.toNonNullable
import org.json.JSONObject
import javax.inject.Inject

class GetAllBreedsUseCase @Inject constructor(
    private val repository: DogsRepository,
    private val breedsProvider: BreedsProvider
) {
    suspend operator fun invoke(): JSONObject {

        if (breedsProvider.allBreedsJSON == null){
            breedsProvider.allBreedsJSON = repository.getDogsBreed()
        }
        return breedsProvider.allBreedsJSON.toNonNullable()

    }

}