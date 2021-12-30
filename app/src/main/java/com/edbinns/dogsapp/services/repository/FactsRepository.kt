package com.edbinns.dogsapp.services.repository

import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.services.retrofit.DogFactsApiService
import com.edbinns.dogsapp.utils.Constants
import com.edbinns.dogsapp.utils.Constants.FACTS_API_BASE_URl
import com.edbinns.dogsapp.utils.Constants.LIMIT_IMAGE_DOGS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FactsRepository @Inject constructor(private val dogsFactsApiService: DogFactsApiService) {

    suspend fun getDogsFacts(): List<String> {
        return withContext(Dispatchers.IO) {
            val response = dogsFactsApiService.getDogsFactsFromService(FACTS_API_BASE_URl,LIMIT_IMAGE_DOGS)


            emptyList()
//            val responseList = response.body()?.imageList
//            transformListResponse(responseList)
        }
    }
}