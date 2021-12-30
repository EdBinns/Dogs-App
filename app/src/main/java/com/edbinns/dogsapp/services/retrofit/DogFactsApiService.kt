package com.edbinns.dogsapp.services.retrofit

import com.edbinns.dogsapp.models.DogFactsResponse
import com.edbinns.dogsapp.models.DogsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

import okhttp3.ResponseBody




interface DogFactsApiService {


    @GET
    suspend fun getDogsFactsFromService(@Url url: String?, @Query("facts") facts : String):  Response<DogFactsResponse>
}