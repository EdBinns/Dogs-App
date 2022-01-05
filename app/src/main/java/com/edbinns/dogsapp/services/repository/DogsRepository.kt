package com.edbinns.dogsapp.services.repository

import com.edbinns.dogsapp.models.Dog
import com.edbinns.dogsapp.models.DogsResponse
import com.edbinns.dogsapp.services.retrofit.DogImageApiService
import com.edbinns.dogsapp.utils.Constants.LIMIT_IMAGE_DOGS
import com.edbinns.dogsapp.utils.toDogsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class DogsRepository @Inject constructor(private val dogImageApiService: DogImageApiService) {


    suspend fun getDogsImages(): List<Dog> {
        return withContext(Dispatchers.IO) {
            val response = dogImageApiService.getDogImageList(LIMIT_IMAGE_DOGS)
            transformListResponse(response)
        }
    }

    suspend fun getDogsImagesByBreed(breed: String): List<Dog> {
        return withContext(Dispatchers.IO) {
            val response = dogImageApiService.getDogsImagesByBreed(LIMIT_IMAGE_DOGS,breed)
            println("response $response")
            transformListResponse(response)
        }
    }
    suspend fun getDogsImagesBySubBreed(breed: String, subBreed: String): List<Dog> {
        return withContext(Dispatchers.IO) {
            val response = dogImageApiService.getDogsImagesBySubBreed(LIMIT_IMAGE_DOGS,breed,subBreed)
            transformListResponse(response)
        }
    }


    suspend fun getDogsBreed(): JSONObject {
        return withContext(Dispatchers.IO) {
            val response = dogImageApiService.getDogsBreed()
            val data = JSONObject(response.body()?.string())
            data.getJSONObject("message")
        }
    }
    private fun transformListResponse(response: Response<DogsResponse>):  List<Dog> {
        val responseList = response.body()?.imageList
        if(!responseList.isNullOrEmpty()) {
            return responseList.toDogsList()
        }
        return emptyList()
    }
}