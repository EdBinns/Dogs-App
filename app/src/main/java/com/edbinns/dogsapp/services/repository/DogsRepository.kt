package com.edbinns.dogsapp.services.repository

import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.services.retrofit.DogImageApiService
import com.edbinns.dogsapp.utils.Constants.LIMIT_IMAGE_DOGS
import com.edbinns.dogsapp.utils.toDogsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogsRepository @Inject constructor(private val dogImageApiService: DogImageApiService) {


    suspend fun getDogsImages(): List<Dog> {
        return withContext(Dispatchers.IO) {
            val response = dogImageApiService.getDogImageList(LIMIT_IMAGE_DOGS)
            val responseList = response.body()?.imageList
            transformListResponse(responseList)
        }
    }

    suspend fun getDogsImagesByBreed(breed: String): List<Dog> {
        return withContext(Dispatchers.IO) {
            val response = dogImageApiService.getDogsImagesByBreed(LIMIT_IMAGE_DOGS,breed)

            val responseList = response.body()?.imageList
            transformListResponse(responseList)
        }
    }


    private fun transformListResponse(imageList: List<String>?):  List<Dog> {
        if(!imageList.isNullOrEmpty()) {
            return imageList.toDogsList()
        }
        return emptyList()
    }
}